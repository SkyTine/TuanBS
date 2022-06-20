package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.entity.ResRecord;
import com.yaxin.tuanbs.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * @return 返回完整记录的records
     */
    public List<Record> getRecentRecord(){
        List<Record> records = recordMapper.getRecordPaged(0, pageSize);
        for(Record record : records){
            record.setSIMG(imgService.getSImgById(record.getId()));
            record.setRIMG(imgService.getRImgById(record.getId()));
        }
        return records;
    }

    public List<ResRecord> getRecentResRecord(){
        List<ResRecord> resRecords = recordMapper.getResRecordPaged(0, pageSize);
        for(ResRecord record : resRecords){
            IMG rImg = imgService.getRImgById(record.getId());
            String name = "";
            double maxValue = Double.MIN_VALUE;
            Map<String, Double> imgInfo = rImg.getImgInfo();
            for (String key:imgInfo.keySet()) {
                double value = imgInfo.get(key);
                if(value > maxValue){
                    name = key; maxValue = value;
                }
            }
            imgInfo = new HashMap<>();imgInfo.put(name, maxValue);
            rImg.setImgInfo(imgInfo);
            record.setRIMG(rImg);
        }
        return resRecords;
    }
}
