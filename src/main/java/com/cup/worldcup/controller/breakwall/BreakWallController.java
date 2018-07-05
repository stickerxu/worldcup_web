package com.cup.worldcup.controller.breakwall;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class BreakWallController {

    @GetMapping("/breakwall")
    public String breakwall() {
        return "breakwall/breakwall";
    }

    @PostMapping("/breakwall/verifyCode")
    public String verifyCode(HttpServletRequest request, Model model) {
        String invest_code = request.getParameter("invest_code");
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "请登录！");
            return "error";
        }
        //校验当前用户邀请是否有效
        if (StringUtils.isBlank(invest_code) || !invest_code.equals(user.getInvest_code())) {
            request.getSession().setAttribute("verifyCodeMsg", "无效的邀请码");
            return "redirect:/breakwall";
        }
        // 注册时间超过一周，邀请码失效
        int compare = DateTimeUtil.compareTo(DateTimeUtil.plusWeeks(user.getRegistry_time(), 1), DateTimeUtil.now());
        if (compare == -1) {
            request.getSession().setAttribute("verifyCodeMsg", "邀请码已失效");
            return "redirect:/breakwall";
        }
        //去邀请码表中查询有效邀请码
        request.getSession().removeAttribute("verifyCodeMsg");
        return "/breakwall/verify_success";
    }

    @GetMapping("/breakwall/myCode")
    public String myCode(HttpServletRequest request, Model model) {
        String result;
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (user == null) {
            result = "请登录！";
        } else {
            result = user.getInvest_code();
        }
        model.addAttribute("myCodeMsg",result);
        return breakwall();
    }
}
