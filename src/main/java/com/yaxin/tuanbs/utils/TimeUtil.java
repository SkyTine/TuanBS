package com.yaxin.tuanbs.utils;

import org.springframework.stereotype.Service;

/**
 * @author Yaxin
 * @date 2022/6/19 17:12
 */
@Service
public class TimeUtil {

    public String calculateSec(long start, long end){
        long ms = end - start;
        return "" + ms/1000 + "s";
    }
}
