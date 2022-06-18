package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.yaxin.tuanbs.utils.DiyId;

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

    public Record domain(String tag, String type, MultipartFile file){
        Record record = new Record(DiyId.getTimeDiyId(), tag, type, new Date());
        IMG sIMG = fileService.saveIMG(file);
        sIMG.setId(record.getId());
        record.setSIMG(sIMG);
        //记得放入分类IMGInfo
        //有rIMG则要放入rIMG
        //然后存入数据库(Record、sIMG、rIMG)
        //返回Record
        return record;
    }
}
