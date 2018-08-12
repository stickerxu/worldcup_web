package com.worldcup.web.controller.worldcup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CountryPickController {

    @GetMapping("/pick")
    public String pickPage() {
        return "worldcup/pick";
    }

    @GetMapping("/prediction")
    public String prediction() {
        return "worldcup/prediction";
    }
}
