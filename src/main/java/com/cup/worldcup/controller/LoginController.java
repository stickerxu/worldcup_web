package com.cup.worldcup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("token",token);
        return "login";
    }

    @PostMapping("/loginSub")
    public String loginSub(@RequestParam("username") String username, @RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try {
            if (verifyToken(session,token)) {
                response.getWriter().write("<script>alert('请不要重复提交表单！');</script>");
                return login(request);
            }
            session.removeAttribute("token");
            Thread.sleep(1500);
            response.getWriter().write("<script>alert('登录成功！')</script>");
            return "index";
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
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
}
