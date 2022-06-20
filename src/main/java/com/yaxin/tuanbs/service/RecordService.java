package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yaxin
 * @date 2022/6/19 22:03
 */
@Service
public class RecordService {

    @Value("${static.Record.pageSize}")
    private Integer pageSize;
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private IMGService imgService;

    /**
     * 将record中所有属性存入数据库
     * @param record 记录
     */
    public void addRecord(Record record){
        recordMapper.addRecord(record);
        if(record.getSIMG() != null){
            imgService.addSImg(record.getSIMG());
        }
        if(record.getRIMG() != null){
            imgService.addRImg(record.getRIMG());
        }
    }

    public Record getRecordById(String id){
        Record record = recordMapper.getRecordById(id);
        record.setSIMG(imgService.getSImgById(id));
        record.setRIMG(imgService.getRImgById(id));
        return record;
    }

    public List<Record> getRecentRecord(){
        List<Record> records = recordMapper.getRecordPaged(0, pageSize);
        for(Record record : records){
            record.setSIMG(imgService.getSImgById(record.getId()));
            record.setRIMG(imgService.getRImgById(record.getId()));
        }
        return records;
    }
}
