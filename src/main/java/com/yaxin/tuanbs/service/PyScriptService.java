package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.utils.CommandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yaxin
 * @date 2022/6/19 10:25
 */
@Service
public class PyScriptService {

    @Value("${static.pyscript}")
    private String pyScriptRoot;
    private final int CATEGORY = 5;
    private final int RES_LINES = 1;

    @Autowired
    CommandUtil commandUtil;

    @Autowired
    FileService fileService;

    /**
     * 存入图片并生成分类信息
     * @param  img 图片
     * @param recordId 本次记录recordId
     * @return 存入图片并生成分类信息
     */
    public IMG handleSIMG(MultipartFile img, String recordId){
        String url = fileService.saveIMG(img);
        String imgPath = fileService.getImgPath(url);
        return new IMG(recordId, url, doClassify(imgPath));
    }

    public IMG handleRIMG(String sImgPath, String type, String recordId){
        String rImgLocalPath = doAttack(sImgPath, type);
        String rImgUrl = fileService.getImgURL(rImgLocalPath);
        return new IMG(recordId, rImgUrl, doClassify(rImgLocalPath));
    }

    /**
     * 获得图片分类信息
     * @param imgPath 图片路径
     * @return map
     */
    public Map<String, Double> doClassify(String imgPath){
        List<String> cmd = new ArrayList<>();
        cmd.add("python"); cmd.add(pyScriptRoot + "/" + "classify.py");
        cmd.add(imgPath);
        List<String> list = CommandUtil.executeCommand(cmd.toArray(new String[0]), CATEGORY);
        Map<String, Double> map = new HashMap<>();
        if(list == null){
            return map;
        }
        for (String item : list) {
            String[] tmp = item.split("/");
            map.put(tmp[0], Double.parseDouble(tmp[1]));
        }
        return map;
    }

    /**
     * 攻击(攻击图片是由模型生成保存的)
     * @param sImgPath 原图路径
     * @param type 攻击类型
     * @return 成功，返回对抗图路径； 否则返回null
     */
    public String doAttack(String sImgPath, String type){
        String targetAttack = "attack.py";
        //type等模型确定后再补充
        List<String> cmd = new ArrayList<>();
        cmd.add("python"); cmd.add(pyScriptRoot + "/" + targetAttack);
        cmd.add(sImgPath);
        List<String> list = CommandUtil.executeCommand(cmd.toArray(new String[0]), RES_LINES);
        if(list == null){
            return null;
        }
        return list.get(0);
    }
}
