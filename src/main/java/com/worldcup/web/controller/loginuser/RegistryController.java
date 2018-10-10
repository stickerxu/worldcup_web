package com.worldcup.web.controller.loginuser;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.service.LoginUserService;
import com.worldcup.web.util.EncrptionUtil;
import com.worldcup.web.util.ParameterUtil;
import com.worldcup.web.util.ResponsePageUtil;
import com.worldcup.web.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class RegistryController {
    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("/registry")
    public String registry() {
        return "loginuser/registry";
    }

    @PostMapping("/registry")
    public @ResponseBody Map<String, Object> registrySub(@RequestParam("verifyCode") String verifyCode, LoginUser loginUser,
                           HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        //验证码校验
        String registryVerifyCode = (String) session.getAttribute("registryVerifyCode");
        if (ParameterUtil.isBlank(registryVerifyCode) || !verifyCode.equalsIgnoreCase(registryVerifyCode)) {
            resultMap.put("status", 0);
            return resultMap;
        }
        String username = loginUser.getUsername();
        //查重
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user != null) {
            resultMap.put("status", 1);
            return resultMap;
        }
        String password = loginUser.getPassword();
        try {
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                loginUser.setPassword(EncrptionUtil.md5(username + password));
            }
            loginUser.setInvestCode(EncrptionUtil.md5(String.valueOf(EncrptionUtil.generateSalt(6))).substring(8,24).toUpperCase());
            loginUserService.insertLoginUser(loginUser);
            //删除session中的验证码信息
            session.removeAttribute("registryVerifyCode");
            resultMap.put("status", 2);
            resultMap.put("message", loginUser.getUsername());
            return resultMap;
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
        BufferedImage image = (BufferedImage) objs[1];
        try {
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/registry/success")
    public String registrySuc(@RequestParam("username") String username, ModelMap modelMap) {
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user != null) {
            modelMap.put("user", user);
            return "loginuser/registry_success";
        }
        return ResponsePageUtil.errorPage(modelMap);
    }
}
