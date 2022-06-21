package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.utils.DiyId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Yaxin
 * @date 2022/6/18 10:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class DomainService {

    @Autowired
    private FileService fileService;

    @Autowired
    private PyScriptService pyScriptService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private IMGService imgService;

    /**
     * 上传图片做处理
     * @param tag 攻击或防御
     * @param type 类型
     * @param file 待处理图片
     * @return 存入数据库的record
     */
    public Record domain(String tag, String type, MultipartFile file){
        //新建Record
        Record record = new Record(DiyId.getTimeDiyId(), tag, type, new Date());
        //存原图、分类并填充到record
        record.setSIMG(pyScriptService.handleSIMG(file, record.getId()));
        //做处理（攻击或防御）并填充到record
        String sImgPath = fileService.getImgPath(record.getSIMG().getUrl());
        record.setRIMG(pyScriptService.handleRIMG(sImgPath, tag, type, record.getId()));
        //然后存入数据库(Record、sIMG、rIMG)
        recordService.addRecord(record);
        //返回Record
        return record;
    }

    /**
     * 使用服务器图片做处理
     * @param tag 攻击或防御
     * @param type 类型
     * @param id 待处理图片的id
     * @return 存入数据库的record
     */
    public Record domain(String tag, String type, String id){
        //新建Record
        Record record = new Record(DiyId.getTimeDiyId(), tag, type, new Date());
        //添加传入结果图做原图记录
        IMG sImg = imgService.getRImgById(id);
        sImg.setId(record.getId());
        record.setSIMG(sImg);
        //做处理（攻击或防御）并填充到record
        String sImgPath = fileService.getImgPath(sImg.getUrl());
        record.setRIMG(pyScriptService.handleRIMG(sImgPath, tag, type, record.getId()));
        //然后存入数据库(Record、sIMG、rIMG)
        recordService.addRecord(record);
        return record;
    }
}
