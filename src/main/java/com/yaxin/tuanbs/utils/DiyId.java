package com.yaxin.tuanbs.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class DiyId {
    /**
     * @return 一个由时间标识的随机字符串id
     */
    public static String getTimeDiyId(){
        String timeString = new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA).format(new Date());
        String s = UUID.randomUUID().toString().substring(24);
        return timeString + s;
    }
}
