package com.worldcup.web;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.service.LoginUserService;
import org.junit.Assert;
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
        loginUser.setUserEmail("");
        loginUser.setInvestCode("");
        loginUser.setUserPhone("");
        loginUserService.insertLoginUser(loginUser);
    }

    @Test
    public void test0705() {
        LoginUser adm = loginUserService.getUserByUsername("adm");
        Assert.assertNull("这不是空的对象",adm);
    }
}
