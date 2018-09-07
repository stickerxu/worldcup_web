package com.worldcup.web.controller.breakwall;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.util.DateTimeUtil;
import com.worldcup.web.util.ResponsePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class BreakWallController {

    @GetMapping("/breakwall")
    public String breakwall() {
        return "breakwall/breakwall";
    }

    @GetMapping("/breakwall/verifyCode")
    public @ResponseBody String verifyCode(HttpServletRequest request, ModelMap modelMap) {
        String investCode = request.getParameter("investCode");
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        String msg = "";
        if (user == null) {
            modelMap.put("message", "请登录！");
            return ResponsePageUtil.errorPage(modelMap);
        }
        //校验当前用户邀请是否有效
        if (StringUtils.isBlank(investCode) || !investCode.equals(user.getInvestCode())) {
            msg = "无效的邀请码";
            return msg;
        }
        return msg;
    }
    //验证成功跳转(保护getInfo页面)
    @PostMapping("/breakwall/getInfo")
    public String getInfo() {
        return "breakwall/verify_success";
    }

    @GetMapping("/breakwall/myCode")
    public @ResponseBody String myCode(HttpServletRequest request) {
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (user == null) {
            log.error("{} 登陆超时, 访问地址：{};", user.getUsername(), request.getRequestURI());
            return "登陆超时！";
        }
        return user.getInvestCode();
    }
}
