package com.yaxin.tuanbs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Yaxin
 * @date 2022/6/18 9:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMG {
    private String id;
    private String url;
    private Map<String, Double> imgInfo;

    @Override
    public String toString(){
        return "id: "+ id + "\n" +
                "url: " + url + "\n" +
                "imgInfo: " + imgInfo.toString();
    }
}
