package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.IMGString;
import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.utils.DiyId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yaxin
 * @date 2022/6/20 0:30
 */
@SpringBootTest
class RecordServiceTest {
    @Autowired
    RecordService recordService;

    @Test
    void addRecord() {
//        Record record = new Record(DiyId.getTimeDiyId(), "attack", "type", new Date());
//        IMG sImg = new IMG(new IMGString(record.getId(), "urlurl", "pug, pug-dog/84.49#Brabancon griffon/3.34#bull mastiff/1.27#Pekinese, Pekingese, Peke/0.43#French bulldog/0.39#"));
//        IMG rImg = new IMG(new IMGString(record.getId(), "urlurl", "pug, pug-dog/84.49#Brabancon griffon/3.34#bull mastiff/1.27#Pekinese, Pekingese, Peke/0.43#French bulldog/39#"));
//        record.setRIMG(sImg);
//        record.setSIMG(rImg);
//        recordService.addRecord(record);
    }

    @Test
    void getRecordById() {
//        System.out.println(recordService.getRecordById("220620004146e20dbfb25308"));
    }

    @Test
    void getRecentRecord(){
//        System.out.println(recordService.getRecentRecord().size());
    }
}