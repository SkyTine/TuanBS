package com.yaxin.tuanbs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Yaxin
 * @date 2022/6/12 12:20
 */
public class TestJavaCommand {

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String[] command = {"python", "C:\\Users\\Yaxin\\Desktop\\testfile\\test.py"};
        String s = executeCommand(command);
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - l);
    }
    /**
     * 执行命令
     */
    public static String executeCommand(String[] cmdarray) {
        Runtime runtime = Runtime.getRuntime();
        String inStr = "", errStr = "";
        try {
            Process process = runtime.exec(cmdarray);
            // 标准输入流（必须写在 waitFor 之前）
            inStr = consumeInputStream(process.getInputStream());
            // 标准错误流（必须写在 waitFor 之前）
            errStr = consumeInputStream(process.getErrorStream()); //若有错误信息则输出
            int proc = process.waitFor();
            if (proc == 0) {
                return inStr;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "执行失败\n" + errStr;
    }

    /**
     * @param is inputStream
     * @return String
     */
    private static String consumeInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = br.readLine()) != null) {
//            System.out.println(s);
            sb.append(s);
        }
        return sb.toString();
    }
}
