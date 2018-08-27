package com.worldcup.web.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    public static final int CASE_LOW = 0;
    public static final int CASE_UP = 1;
    public static final int CASE_FIRST_UP = 1;
    public static final int TONE_NUMBER = 0;
    public static final int TONE_NONE = 1;
    public static final int TONE_MARK = 2;
    public static final int CHAR_U_COLON = 0;
    public static final int CHAR_V = 1;
    public static final int CHAR_U = 2;
    public static final String SEPARATE_NONE = "";
    public static final String SEPARATE_BLANK = " ";

    //汉字转拼音
    public static String hanZiZhuanPinYin(String content) throws BadHanyuPinyinOutputFormatCombination {
        return hanZiZhuanPinYin(content, CASE_LOW, TONE_NONE, CHAR_U, SEPARATE_BLANK);
    }

    public static String hanZiZhuanPinYin(String content, Integer pyCase, Integer pyTone, Integer pyWithU, String pySeparate) throws BadHanyuPinyinOutputFormatCombination {
        if (ParameterUtil.isBlank(content)) {
            return null;
        }
        return PinyinHelper.toHanYuPinyinString(content, pinYinFormat(pyCase, pyTone, pyWithU), pySeparate, true);
    }



    //大小写、有无声调、ü|v
    private static HanyuPinyinOutputFormat pinYinFormat(Integer pyCase, Integer pyTone, Integer pyWithU) {
        HanyuPinyinCaseType caseType;
        HanyuPinyinToneType toneType;
        HanyuPinyinVCharType charType;
        switch (pyCase) {
            case CASE_LOW : caseType = HanyuPinyinCaseType.LOWERCASE;break;
            case CASE_UP : caseType = HanyuPinyinCaseType.UPPERCASE;break;
            default: throw new  RuntimeException("未找到caseType类型");
        }
        switch (pyTone) {
            case TONE_NUMBER : toneType = HanyuPinyinToneType.WITH_TONE_NUMBER;break;
            case TONE_NONE : toneType = HanyuPinyinToneType.WITHOUT_TONE;break;
            case TONE_MARK : toneType = HanyuPinyinToneType.WITH_TONE_MARK;break;
            default: throw new  RuntimeException("未找到toneType类型");
        }
        switch (pyWithU) {
            case CHAR_U_COLON : charType = HanyuPinyinVCharType.WITH_U_AND_COLON;break;
            case CHAR_V : charType = HanyuPinyinVCharType.WITH_V;break;
            case CHAR_U : charType = HanyuPinyinVCharType.WITH_U_UNICODE;break;
            default: throw new  RuntimeException("未找到charType类型");
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(caseType);
        format.setToneType(toneType);
        format.setVCharType(charType);
        return format;
    }
}
