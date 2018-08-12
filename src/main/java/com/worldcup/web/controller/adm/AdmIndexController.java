package com.worldcup.web.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/adm")
public class AdmIndexController {
    @GetMapping({"","/"})
    public String index() {
        return "adm/index";
    }
}
