package com.yaxin.tuanbs.service;

import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Yaxin
 * @date 2022/6/12 15:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class FileService {
    @Value("${static.profile}")
    private String rootPath;

    //访问根路径
    @Value("${static.rootURL}")
    private String rootURL;

    @Value("${static.folders.pics}")
    private String picPath;

    @Value("${static.maxImageSize}")
    private Long maxImageSize;

    /**
     * @param file 文件
     * @return 存入成功返回可访问url， 否则返回null
     */
    public String saveIMG(MultipartFile file) {
        return saveFile(file, picPath);
    }

    public boolean isValidIMG(MultipartFile file) {
        if (file.getSize() > maxImageSize) {
            return false;
        }
        try {
            return FileUtil.isImg(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getImgURL(String imgPath) {
        return getURl(imgPath, picPath);
    }

    public String getImgPath(String url) {
        return getLocalPath(url, picPath);
    }

    private String getURl(String localPath, String folder) {
        return getRootURL() + "/" + folder + "/" + localPath.substring(localPath.lastIndexOf("/") + 1);
    }

    private String getLocalPath(String url, String folder) {
        return getRootPath() + "/" + folder + "/" + url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * @param file   文件
     * @param folder 存入的文件夹名称
     * @return 存入成功返回可访问url， 否则返回null
     */
    private String saveFile(MultipartFile file, String folder) {
        //存储路径
        String savePath = rootPath + "/" + folder;
        //文件全名
        String fileName = file.getOriginalFilename();
        //后缀
        String suffixName;
        assert fileName != null;
        suffixName = fileName.substring(fileName.lastIndexOf("."));

        //生成新文件名
        fileName = UUID.randomUUID().toString().replace("-", "") + suffixName;
        File targetFile = new File(savePath);
        if (!targetFile.exists()) {
            // 判断文件夹是否为空，空则创建
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, fileName);
        try {
            file.transferTo(saveFile);
            //存入成功
            String url = rootURL + "/" + folder + "/" + fileName;
            System.out.println(savePath + "/" + fileName);
            System.out.println(url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
