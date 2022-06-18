package com.yaxin.tuanbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Yaxin
 * @date 2022/6/9 11:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private String id;
    private String tag;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //原图
    private IMG sIMG;
    //有结果图放结果图
    private IMG rIMG;

    public Record(String id, String tag, String type, Date createTime) {
        this.id = id;
        this.tag = tag;
        this.type = type;
        this.createTime = createTime;
    }
}
