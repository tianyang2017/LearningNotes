package com.ciic.test.util;

import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : heibai
 * @description : 生成文章页导航目录
 * @date :create in 2018/11/27
 */

public class GenNavigation {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("请传递路径");
            return;
        }
        String filePath = args[0];
        //  获取文件内容
        String content = getContent(filePath);
        // 获取全部标题
        List<Pair<String, String>> allTitle = getAllTitle(content);
        // 生成导航
        String nav = genNav(allTitle);
        //输出导航
        System.out.println(nav);
        // 可以选择写出并覆盖原文件
        // write(filePath, content, nav);
    }

    private static void write(String filePath, String content, String nav) {
        try {
            String newContent = "";
            if (content.contains("## 目录") && content.contains("## 正文<br/>")) {
                // 如果原来有目录则替换
                newContent = content.replaceAll("(?m)(## 目录[\\s\\S]*## 正文<br/>)", nav);
            } else {
                StringBuilder stringBuilder = new StringBuilder(content);
                // 如果原来没有目录，则title和正文一个标题间写入
                int index = content.indexOf("## ");
                stringBuilder.insert(index - 1, nav);
                newContent = stringBuilder.toString();
            }
            // 写出覆盖文件
            FileWriter fileWriter = new FileWriter(new File(filePath));
            fileWriter.write(newContent);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String genNav(List<Pair<String, String>> flagAndTitles) {
        StringBuilder builder = new StringBuilder();
        // 目录头
        builder.append("## 目录<br/>\n");
        // Github有效目录格式: <a href="#21-预备">页面锚点</a>
        for (Pair<String, String> ft : flagAndTitles) {
            String flag = ft.getKey();
            String title = ft.getValue();
            builder.append(genBlank(flag.length() - 2, 4));
            builder.append(String.format("<a href=\"%s\">%s</a><br/>\n", "#" + title.replaceAll("\\.", "").replace(" ", "-"), title));
        }
        // 目录尾
        builder.append("## 正文<br/>\n");
        return builder.toString();
    }

    private static String genBlank(int i, int scale) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < scale; k++) {
                builder.append("&nbsp;");
            }
        }
        return builder.toString();
    }

    private static List<Pair<String, String>> getAllTitle(String content) {
        List<Pair<String, String>> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?m)^(#{2,10})\\s?(\\S+\\s?\\S+)");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group2 = matcher.group(2);
            if (!group2.contains("目录") && !group2.contains("正文")) {
                list.add(new Pair<>(matcher.group(1), group2));
            }
        }
        return list;
    }

    private static String getContent(String filePath) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] bytes = new byte[10240];

            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, read));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}


