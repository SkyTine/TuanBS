package com.yaxin.tuanbs.utils;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yaxin
 * @date 2022/6/8 21:41
 */
@Service
public class FileUtil {
    static Tika tika = new Tika();
    static final String IMG = "image";

    public static String getMimeType(File file) {
        try {
            return tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unidentifiable";
    }

    public static String getMimeType(InputStream inputStream) {
        try {
            return tika.detect(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unidentifiable";
    }

    public static boolean isImg(File file) {
        return getMimeType(file).split("/")[0].equals(IMG);
    }

    public static boolean isImg(InputStream inputStream) {
        return getMimeType(inputStream).split("/")[0].equals(IMG);
    }
}
