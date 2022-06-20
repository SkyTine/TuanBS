package com.yaxin.tuanbs.entity;

import com.yaxin.tuanbs.utils.DiyId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
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

    public IMG(IMGString imgString){
        this.id = imgString.getId();
        this.url = imgString.getUrl();
        Map<String, Double> map = new HashMap<>();
        String[] results = imgString.getImgInfo().split("#");
        for (String result : results){
            String[] arr = result.split("/");
            map.put(arr[0], Double.parseDouble(arr[1]));
        }
        this.imgInfo = map;
    }

    @Override
    public String toString(){
        return "{"
                + "id="+ id + " " +
                "url=" + url + " " +
                "imgInfo=" + (imgInfo == null ? "" : imgInfo.toString())
                + "}";
    }

    public String imgInfoToString(){
        StringBuilder sb = new StringBuilder();
        for (String key : imgInfo.keySet()) {
            String tmp = key + "/" + imgInfo.get(key) + "#";
            sb.append(tmp);
        }
        return sb.toString();
    }
}
