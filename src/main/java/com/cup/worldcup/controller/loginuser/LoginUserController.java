package com.cup.worldcup.controller.loginuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/user/updatePassSub")
    public void updatePassSub(HttpServletRequest request, HttpServletResponse response) {

    }
}
