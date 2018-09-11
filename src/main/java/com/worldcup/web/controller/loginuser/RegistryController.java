package com.worldcup.web.controller.loginuser;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.service.LoginUserService;
import com.worldcup.web.util.EncrptionUtil;
import com.worldcup.web.util.ParameterUtil;
import com.worldcup.web.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
    public String registrySub(@RequestParam("verifyCode") String verifyCode, LoginUser loginUser,
                              HttpSession session, HttpServletResponse response, Model model) {
        //验证码校验
        String registryVerifyCode = (String) session.getAttribute("registryVerifyCode");
        if (ParameterUtil.isBlank(registryVerifyCode) || !verifyCode.equalsIgnoreCase(registryVerifyCode)) {
            response.setContentType("text/html;charset=UTF-8");
            try {
                response.getWriter().write("<script>alert('出错了');</script>");
            } catch (IOException e) {
                log.info(e.getMessage(), e);
            }
            return null;
        }
        String username = loginUser.getUsername();
        //查重
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user != null) {
            model.addAttribute("message", "用户名重复啦~ 亲，请换个更有个性的用户名！");
            return "error/error";
        }
        String password = loginUser.getPassword();
        try {
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                loginUser.setPassword(EncrptionUtil.md5(username + password));
            }
            loginUser.setInvestCode(EncrptionUtil.md5(String.valueOf(EncrptionUtil.generateSalt(6))).substring(8,24).toUpperCase());
            loginUserService.insertLoginUser(loginUser);
            model.addAttribute("user", loginUser);
            return "loginuser/registry_success";
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @PostMapping("/registry/verifyUsername")
    @ResponseBody
    public int verify(@RequestParam("username") String username) {
        int result = 0;
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user == null) {
            result = 1;
        }
        return result;
    }

    @GetMapping("/registry/getVerifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) {
        Object[] objs = VerifyCodeUtil.createImage();
        session.setAttribute("registryVerifyCode", String.valueOf(objs[0]));
        log.info("图片验证码为：{}", session.getAttribute("registryVerifyCode"));
        BufferedImage image = (BufferedImage) objs[1];
        try {
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
