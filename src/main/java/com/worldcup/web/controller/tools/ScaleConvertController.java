package com.worldcup.web.controller.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/tools/scaleConvert")
public class ScaleConvertController {
    @GetMapping({"","/"})
    public String scaleConvert() {
        return "tools/scaleconvert/index";
    }
}
