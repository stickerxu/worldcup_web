package com.worldcup.web.util;

import com.worldcup.web.Constants;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncrptionUtil {
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String SHA256 = "SHA-256";
    //MD5 返回4种加密结果
    public static String md5(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (ParameterUtil.isBlank(message)) {
            return null;
        }
        String result = encrypt(message, MD5);
        return result;
    }

    //SHA1 数字签名标准
    public static String sha1(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (ParameterUtil.isBlank(message)) {
            return null;
        }
        return encrypt(message, SHA1);
    }

    //SHA-256
    public static String sha256(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (ParameterUtil.isBlank(message)) {
            return null;
        }
        return encrypt(message, SHA256);
    }

    private static String encrypt(String message, String method) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        MessageDigest digest = MessageDigest.getInstance(method);
        byte[] bytes = digest.digest(message.getBytes(Constants.CHARSET_UTF_8));
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0XFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result.append(temp);
        }
        return result.toString();
    }

    public static byte[] aesEncrypt(String content) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey secretKey = generator.generateKey();
        SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content.getBytes(Constants.CHARSET_UTF_8));
    }


    public static String generateSalt(int number) {
        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom();
        for (int i = 0; i < number; i++) {
            sb.append(sr.nextInt(10));
        }
        return sb.toString();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
