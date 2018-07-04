package com.cup.worldcup.controller.loginuser;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.service.LoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class RegistryController {
    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("/registry")
    public String registry() {
        return "loginuser/registry";
    }

    @PostMapping("/registrySub")
    public String registrySub(LoginUser loginUser) {
        LoginUser user = new LoginUser();
        user.setUsername("sticker-test");
        user.setPassword("159364");
        loginUserService.insertLoginUser(user);
        return null;
    }
}
