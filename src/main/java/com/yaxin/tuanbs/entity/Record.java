package com.yaxin.tuanbs.entity;

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
    private String sourceIMG;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private ResIMG[] resIMGs;
}
