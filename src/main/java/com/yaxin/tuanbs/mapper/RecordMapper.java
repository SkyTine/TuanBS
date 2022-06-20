package com.yaxin.tuanbs.mapper;

import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.entity.ResRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yaxin
 * @date 2022/6/19 21:27
 */
@Repository
public interface RecordMapper {

    void addRecord(Record record);

    Record getRecordById(String id);

    List<Record> getRecordPaged(@Param("pageNum") Integer pageNum,
                                @Param("pageSize") Integer pageSize);

    List<ResRecord> getResRecordPaged(@Param("pageNum") Integer pageNum,
                                      @Param("pageSize") Integer pageSize);
}
