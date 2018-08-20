package com.worldcup.web.util;

public class ScaleConvertUtil {
    //任意进制转任意进制
    public static String anyToAny(String content, int sourceRadix, int targetRadix) {
        return tenToAny(anyToTen(content, sourceRadix), targetRadix);
    }
    //任意进制转换为10进制
    public static Integer anyToTen(String content, int radix) {
        return Integer.parseInt(content, radix);
    }
    //10进制转任意进制
    public static String tenToAny(Integer content, int radix) {
        if (content == null) {
            return null;
        }
        ;
        return Integer.toString(content, radix)+"";
    }
}
