package com.cup.worldcup.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class EncrptionUtil {
    //MD5 返回4种加密结果
    public static Map<String, String> md5(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (isblank(message)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String result = encrypt(message, "MD5");
        map.put("tt", result);
        map.put("ttU", result.toUpperCase());
        map.put("s", result.substring(8,24));
        map.put("sU", result.substring(8,24).toUpperCase());
        return map;
    }

    //SHA1 数字签名标准
    public static String sha1(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (isblank(message)) {
            return "";
        }
        return encrypt(message, "SHA1");
    }

    //SHA-256
    public static String sha256(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (isblank(message)) {
            return "";
        }
        return encrypt(message, "SHA-256");
    }

    private static String encrypt(String message, String method) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        MessageDigest digest = MessageDigest.getInstance(method);
        byte[] bytes = digest.digest(message.getBytes("UTF-8"));
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0XFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result.append(temp);
        }
        return result.toString();
    }

    public static String generateSalt(int number) {
        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom();
        for (int i = 0; i < number; i++) {
            sb.append(sr.nextInt(10));
        }
        return sb.toString();
    }

    private static boolean isblank(String message) {
       return "".equals(message);
    }
}
