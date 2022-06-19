package com.yaxin.tuanbs.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaxin
 * @date 2022/6/12 12:20
 */
@Service
public class CommandUtil {
    /**
     * 执行命令
     */
    public static List<String> executeCommand(String[] cmdArray, int lastLineNum) {
        Runtime runtime = Runtime.getRuntime();
        List<String> list;
        try {
            Process process = runtime.exec(cmdArray);
            // 标准输入流（必须写在 waitFor 之前）
            list = consumeInputStream(process.getInputStream(), lastLineNum);
            int proc = process.waitFor();
            if (proc == 0) {
                return list;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param is inputStream
     * @return String
     */
    private static List<String> consumeInputStream(InputStream is, int lastLineNum) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String s;
        List<String> list = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            list.add(s);
        }
        return list.subList(list.size() - lastLineNum, list.size());
    }
}
