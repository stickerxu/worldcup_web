package com.cup.worldcup;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.service.LoginUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorldcupApplicationTests {

    @Autowired
    private LoginUserService loginUserService;

    @Test
    public void contextLoads() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("stickertest");
        loginUser.setPassword("1591655");
        loginUser.setUser_email("");
        loginUser.setInvest_code("");
        loginUser.setUser_phone("");
        loginUserService.insertLoginUser(loginUser);
    }

}
