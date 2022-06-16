package com.yaxin.tuanbs.service;

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

    @Value("${static.folders.source}")
    private String sourcePath;

    @Value("${static.folders.res}")
    private String resPath;

    @Value("${static.maxImageSize}")
    private Long maxImageSize;

    public String saveSourceIMG(MultipartFile file){
        return saveIMG(file, sourcePath);
    }

    private String saveIMG(MultipartFile file, String folder){
        if(!isValidIMG(file)){
            return null;
        }
        return saveFile(file, folder);
    }

    private boolean isValidIMG(MultipartFile file){
        if(file.getSize() > maxImageSize){
            return false;
        }
        try {
            return FileUtil.isImg(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param file 文件
     * @param folder 存入的文件夹名称
     * @return 存入成功返回可访问的url， 否则返回null
     */
    private String saveFile(MultipartFile file, String folder){
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
        try{
            file.transferTo(saveFile);
            //存入成功
            String url = rootURL + "/" + folder + "/" + fileName;
            System.out.println(url);
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
