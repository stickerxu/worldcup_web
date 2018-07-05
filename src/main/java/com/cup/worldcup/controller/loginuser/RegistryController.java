package com.cup.worldcup.controller.loginuser;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.service.LoginUserService;
import com.cup.worldcup.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
    public String registrySub(LoginUser loginUser, Model model) {
        String username = loginUser.getUsername();
        //查重
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user != null) {
            model.addAttribute("message", "用户名重复啦~ 亲，请换个更有个性的用户名！");
            return "error";
        }
        String password = loginUser.getPassword();
        try {
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                loginUser.setPassword(EncrptionUtil.md5(username + password).get(EncrptionUtil.MD5_32_BIT));
            }
            loginUser.setInvest_code(EncrptionUtil.md5(String.valueOf(EncrptionUtil.generateSalt(6))).get(EncrptionUtil.MD5_16_BIT_UPCASE));
            loginUserService.insertLoginUser(loginUser);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        }
        return "redirect:/login";
    }

    @PostMapping("/registry/verify")
    @ResponseBody
    public int verify(@RequestParam("username") String username) {
        int result = 0;
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user == null) {
            result = 1;
        }
        return result;
    }
}
