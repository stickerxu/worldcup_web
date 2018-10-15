package com.worldcup.web.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnglishReaderUtil {

    // /Users/xujingyao/Desktop/sb_repository/article.txt
    //读取文件
    public static Map<String, Integer> readFile(InputStream is) throws IOException {
//        String filePath = "/Users/xujingyao/Desktop/sb_repository/article.txt";
        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while (null !=  (line = br.readLine())) {
            line = line.toLowerCase().replaceAll("[^A-Za-z]"," ").trim();
            String[] words = line.split("\\s+");
            countWords(words, map);
        }
        br.close();
        return map;
    }
    //输入内容
    public static Map<String, Integer> readContent(String content) {
        Map<String, Integer> map = new HashMap<>();
        content = content.toLowerCase().replaceAll("[^A-Za-z]"," ").trim();
        String[] words = content.split("\\s+");
        return countWords(words, map);
    }

    private static Map<String, Integer> countWords(String[] words, Map<String, Integer> map) {
        for (String word : words) {
            if (ParameterUtil.isNotBlank(word)) {
                if (map.containsKey(word)) {
                    map.put(word,map.get(word)+1);
                } else {
                    map.put(word,1);
                }
            }
        }
        return map;
    }
}
