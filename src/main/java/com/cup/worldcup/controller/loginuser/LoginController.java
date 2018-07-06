package com.cup.worldcup.controller.loginuser;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.service.LoginUserService;
import com.cup.worldcup.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    //找回密码
    @GetMapping("/login/findPass")
    public String findPass() {
        return "loginuser/find_password";
    }

    //下一步
    @PostMapping("/login/findPassNext")
    public String findPassNext(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            return "redirect:/login";
        }
        LoginUser user = loginUserService.getUserByUsername(username);
        if (user == null) {
            request.getSession().setAttribute("resultMsg", "用户名不存在");
            return "redirect:/login/findPass";
        }
        request.getSession().setAttribute("findPassUser", user);
        request.getSession().removeAttribute("resultMsg");
        log.info("用户 {} 找回密码用户名验证成功", username);
        return "loginuser/find_pass_next";
    }
    //继续下一步
    @PostMapping("/login/findPassNextTwo")
    public String findPassNextTwo(HttpServletRequest request) {
        String user_email = request.getParameter("user_email");
        String user_phone = request.getParameter("user_phone");
        LoginUser user = (LoginUser) request.getSession().getAttribute("findPassUser");
        if (user == null) {
            request.getSession().setAttribute("resultMsg", "操作超时，请重新操作！");
            return "redirect:/login/findPass";
        }
        if (StringUtils.isBlank(user_email) || StringUtils.isBlank(user_phone) ||
                !user.getUser_email().equals(user_email) || !user.getUser_phone().equals(user_phone)) {
            request.getSession().setAttribute("resultMsg", "邮箱或手机号输入有误！");
            return "loginuser/find_pass_next";
        }
        request.getSession().removeAttribute("resultMsg");
        log.info("用户 {} 邮箱和手机号验证成功", user.getUsername());
        return "loginuser/find_pass_next_two";
    }
    //提交
    @PostMapping("/login/findPassSub")
    public String findPassSub(HttpServletRequest request) {
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        LoginUser user = (LoginUser) request.getSession().getAttribute("findPassUser");
        if (user == null) {
            request.getSession().setAttribute("resultMsg", "操作超时，请重新操作！");
            return "redirect:/login/findPass";
        }
        if (StringUtils.isBlank(password) || StringUtils.isBlank(repassword) || !password.equals(repassword)) {
            return "redirect:/login";
        }
        try {
            user.setPassword(EncrptionUtil.md5(user.getUsername() + password).get(EncrptionUtil.MD5_32_BIT));
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        loginUserService.updateLoginUserPasswordById(user);
        request.getSession().removeAttribute("resultMsg");
        request.getSession().removeAttribute("findPassUser");
        log.info("用户 {} 密码修改成功", user.getUsername());
        return "redirect:/login";
    }
}
