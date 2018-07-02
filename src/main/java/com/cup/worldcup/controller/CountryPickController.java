package com.cup.worldcup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CountryPickController {

    @GetMapping({"/",""})
    public String index() {
        return "index";
    }

    @GetMapping("/pick")
    public String pickPage() {
        return "pick";
    }

    @GetMapping("/prediction")
    public String prediction() {
        return "prediction";
    }
}
