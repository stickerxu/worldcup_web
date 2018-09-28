package com.worldcup.web.config;

import com.worldcup.web.Constants;
import com.worldcup.web.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Component
@Slf4j
public class MethonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        //访问路径、ip、参数打印
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestUri = request.getRequestURI();
        log.info("RequestURI：{}", requestUri);
        log.info("IP：{}", request.getRemoteAddr());
        if (parameterMap.size() > 0) {
            StringBuilder params = new StringBuilder();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                params.append("Key：").append(entry.getKey()).append(", Value：").append(Arrays.toString(entry.getValue())).append("; ");
            }
            log.info("RequestParams：{}", params.toString());
        }
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (null == user) {
            request.getSession().setAttribute(Constants.LOGIN_REDIRECT_URI, requestUri);
            response.sendRedirect("/login");
            flag = false;
        }
        return flag;
    }
}
