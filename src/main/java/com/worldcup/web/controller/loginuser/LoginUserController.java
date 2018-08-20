package com.worldcup.web.controller.loginuser;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.service.LoginUserService;
import com.worldcup.web.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@Slf4j
public class LoginUserController {

    @Autowired
    private LoginUserService loginUserService;

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
        String old_password = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        String renew_password = request.getParameter("renew_password");
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            if (user == null) {
                writer.write("<script>alert('登录超时！');</script>");
                response.sendRedirect("/login");
                return;
            }
            LoginUser loginUser = loginUserService.getUserByUsername(user.getUsername());
            if (verifyPassword(old_password, loginUser) == false) {
                writer.write("<script>alert('原密码错误！');history.back();</script>");
                return;
            }
            if (!new_password.equals(renew_password)) {
                log.warn("非法访问！");
                response.sendRedirect("/login");
                return;
            }
            String newPassword = EncrptionUtil.md5(loginUser.getUsername() + new_password);
            loginUser.setPassword(newPassword);
            loginUserService.updateLoginUserPasswordById(loginUser);
            writer.write("<script>alert('修改成功！');history.back();</script>");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }

    }
    private boolean verifyPassword(String password, LoginUser loginUser) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String inputPass = loginUser.getUsername() + password;
        String daoPass = loginUser.getPassword();
        if (EncrptionUtil.md5(inputPass).equals(daoPass)) {
            return true;
        }
        return false;
    }
}
