package com.cup.worldcup.controller.loginuser;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.service.LoginUserService;
import com.cup.worldcup.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private LoginUserService loginUserService;
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("token",token);
        return "loginuser/login";
    }

    @PostMapping("/loginSub")
    public String loginSub(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try {
            if (verifyToken(session,token)) {
                response.getWriter().write("<script>alert('请不要重复提交表单！');</script>");
                return login(request, model);
            }
            session.removeAttribute("token");
            LoginUser user = loginUserService.getUserByUsername(username);
            //密码校验
            if (user == null || verifyPassword(password, user) == false) {
                model.addAttribute("message","用户名或密码错误！");
                return login(request, model);
            }
            log.info("用户：{} 登陆成功！", user.getUsername());
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
    private boolean verifyToken(HttpSession session, String token) {
        if (token == null) {
            return true;
        }
        if (session.getAttribute("token") == null) {
            return true;
        }
        if (!session.getAttribute("token").equals(token)) {
            return true;
        }
        return false;
    }

    private boolean verifyPassword(String password, LoginUser loginUser) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String inputPass = loginUser.getUsername() + password;
        String daoPass = loginUser.getPassword();
        if (EncrptionUtil.md5(inputPass).get(EncrptionUtil.MD5_32_BIT).equals(daoPass)) {
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (user == null) {
            log.info("用户未登录！");
            return "redirect:/login";
        }
        log.info("用户：{} 已退出！", user.getUsername());
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        return "redirect:/";
    }
}
