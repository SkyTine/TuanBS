package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.IMGString;
import com.yaxin.tuanbs.utils.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    public IMG handleRIMG(String sImgPath, String tag, String type, String recordId){
        //defense 1,2,3都是返回分类结果String，其他的均返回图片路径
        String resString = "";
        switch (tag){
            case "attack":
                resString = doAttack(sImgPath, type);
                break;
            case "defense":
                resString = doDefense(sImgPath, type);
                break;
            default:
                log.info("error tag!!!!!!!!");
                break;
        }
        System.out.println(resString);
        if(tag.equals("defense") && (type.equals("adv_inception_v3") || type.equals("ens3_adv_inception_v3") || type.equals("ens4_adv_inception_v3"))){
            //这里直接放原图
            String rImgUrl = fileService.getImgURL(sImgPath);
            //通过分类信息创建
            return new IMG(new IMGString(recordId, rImgUrl, resString));
        }
        String rImgUrl = fileService.getImgURL(resString);
        return new IMG(recordId, rImgUrl, doClassify(resString));
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
            map.put(tmp[0].split(", ")[0], Double.parseDouble(tmp[1]));
        }
        return map;
    }

    /**
     * 攻击(攻击图片是由模型生成保存的)
     * @param sImgPath 原图路径
     * @param type 攻击类型
     * @return 返回对抗图路径
     */
    public String doAttack(String sImgPath, String type){
        List<String> cmd = new ArrayList<>();
        String targetAttack = "attack.py";
        //type等模型确定后再补充
        switch(type){
            case "CW":
            case "FGSM":
                targetAttack = "attack2.py";
                break;
            default:
                break;
        }
        cmd.add("python"); cmd.add(pyScriptRoot + "/" + targetAttack);
        cmd.add(sImgPath);
        if(targetAttack.equals("attack2.py")){
            cmd.add(type);
        }
        List<String> list = CommandUtil.executeCommand(cmd.toArray(new String[0]), RES_LINES);
        if(list == null || !new File(list.get(0)).exists()){
            return null;
        }
        return list.get(0);
    }

    /**
     * 防御（防御图片是由模型保存的）
     * @param attackedImgPath 一张被攻击的图片
     * @param type 防御类型
     * @return 返回防御图片路径或者图片分类信息(imgInfo)
     */
    public String doDefense(String attackedImgPath, String type){
        String targetDefense;
        List<String> cmd = new ArrayList<>();
        List<String> list;
        cmd.add("python");
        switch (type){
            case "adv_inception_v3":
                targetDefense = "defense.py";
                break;
            case "ens3_adv_inception_v3":
                targetDefense = "defense2.py";
                break;
            case "ens4_adv_inception_v3":
                targetDefense = "defense3.py";
                break;
            case "BF_adv":
            default:
                type = "BF_adv";
                targetDefense = "defense4.py";
                break;
        }
        cmd.add(pyScriptRoot + "/" + targetDefense);
        cmd.add(attackedImgPath);
        switch (type){
            case "adv_inception_v3":
            case "ens3_adv_inception_v3":
            case "ens4_adv_inception_v3":
                list = CommandUtil.executeCommand(cmd.toArray(new String[0]), CATEGORY);
                break;
            case "BF_adv":
            default:
                list = CommandUtil.executeCommand(cmd.toArray(new String[0]), RES_LINES);
                break;
        }
        if(list != null && list.size() == 5){
            StringBuilder sb = new StringBuilder();
            for (String s: list) {
                String[] split = s.split("/");
                String[] names = split[0].split(", ");
                sb.append(names[0]); sb.append("/"); sb.append(split[1]); sb.append("#");
            }
            return sb.toString();
        }
        if(list == null || !new File(list.get(0)).exists()){
            return null;
        }
        return list.get(0);
    }
}
