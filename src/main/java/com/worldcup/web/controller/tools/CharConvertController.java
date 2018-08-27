package com.worldcup.web.controller.tools;

import com.worldcup.web.util.ParameterUtil;
import com.worldcup.web.util.PinYinUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/tools/charConvert")
public class CharConvertController {
    @GetMapping({"","/"})
    public String charConver() {
        return "tools/charconvert/index";
    }

    @PostMapping("/pyConvert")
    public @ResponseBody String pyConvert(HttpServletRequest request) {
        String inputText = request.getParameter("inputText");
        Integer pyCase = ParameterUtil.parseInteger(request.getParameter("pyCase"));
        Integer pyTone = ParameterUtil.parseInteger(request.getParameter("pyTone"));
        Integer pyWithV = ParameterUtil.parseInteger(request.getParameter("pyWithV"));
        String pySeparate = request.getParameter("pySeparate");
        switch (pyCase) {
            case 0 : pyCase = PinYinUtil.CASE_LOW;break;
            case 1 : pyCase = PinYinUtil.CASE_UP;break;
            case 2 : pyCase = PinYinUtil.CASE_FIRST_UP;break;
            default : pyCase = null;break;
        }
        pyTone = pyTone == null ? PinYinUtil.TONE_NONE : PinYinUtil.TONE_MARK;
        pyWithV = pyWithV == null ? PinYinUtil.CHAR_U : PinYinUtil.CHAR_V;
        pySeparate = pySeparate == null ? PinYinUtil.SEPARATE_NONE : PinYinUtil.SEPARATE_BLANK;
        try {
            String resultText = PinYinUtil.hanZiZhuanPinYin(inputText, pyCase, pyTone, pyWithV, pySeparate);
            return resultText;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
