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

    public Record domain(String tag, String type, MultipartFile file){
        //新建Record
        Record record = new Record(DiyId.getTimeDiyId(), tag, type, new Date());
        //存原图、分类并填充到record
        record.setSIMG(pyScriptService.handleSIMG(file, record.getId()));
        //做处理（攻击或防御）并填充到record
        String sImgURL = fileService.getImgPath(record.getSIMG().getUrl());
        record.setRIMG(pyScriptService.handleRIMG(sImgURL, type, record.getId()));
        //然后存入数据库(Record、sIMG、rIMG)
        //返回Record
        return record;
    }
}
