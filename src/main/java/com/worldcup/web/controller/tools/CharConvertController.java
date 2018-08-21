package com.worldcup.web.controller.tools;

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
        try {
            String resultText = PinYinUtil.hanZiZhuanPinYin(inputText);
            return resultText;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
