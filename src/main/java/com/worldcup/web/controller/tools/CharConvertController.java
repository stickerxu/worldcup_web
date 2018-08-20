package com.worldcup.web.controller.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/tools/charConvert")
public class CharConvertController {
    @GetMapping({"","/"})
    public String charConver() {
        return "tools/charConvert/index";
    }
}
