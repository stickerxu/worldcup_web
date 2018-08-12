package com.worldcup.web.controller.charconvert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CharConvertController {
    @GetMapping("/charConvert")
    public String charConver() {
        return "charConvert/char_convert_index";
    }
}
