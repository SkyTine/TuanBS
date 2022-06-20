package com.yaxin.tuanbs.mapper;

import com.yaxin.tuanbs.entity.IMGString;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yaxin
 * @date 2022/6/19 21:34
 */
@Repository
public interface IMGMapper {

    void addSImg(@Param("id") String id, @Param("url") String url, @Param("imgInfo") String imgInfo);

    void addRImg(@Param("id") String id, @Param("url") String url, @Param("imgInfo") String imgInfo);

    IMGString getSImgById(String id);

    IMGString getRImgById(String id);

    IMGString getSImgBbyURL(String url);

    IMGString getRImgBbyURL(String url);
}
