package com.cup.worldcup.controller.loginuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginUserController {

    @GetMapping("/user/info")
    public String info() {
        return "loginuser/user_info";
    }

    @GetMapping("/user/updatePass")
    public String updatePass() {

        return "loginuser/user_update_pass";
    }
}
