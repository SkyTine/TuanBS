package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.IMGString;
import com.yaxin.tuanbs.mapper.IMGMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yaxin
 * @date 2022/6/19 21:52
 */
@Service
public class IMGService {
    @Autowired
    private IMGMapper imgMapper;

    public void addSImg(IMG sImg){
        imgMapper.addSImg(sImg.getId(), sImg.getUrl(), sImg.imgInfoToString());
    }

    public IMG getSImgById(String id){
        return new IMG(imgMapper.getSImgById(id));
    }

    public void addRImg(IMG rImg){
        imgMapper.addRImg(rImg.getId(), rImg.getUrl(), rImg.imgInfoToString());
    }

    public IMG getRImgById(String id){
        return new IMG(imgMapper.getRImgById(id));
    }
}