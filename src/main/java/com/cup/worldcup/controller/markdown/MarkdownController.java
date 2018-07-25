package com.cup.worldcup.controller.markdown;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@Slf4j
@RequestMapping("/markdown")
public class MarkdownController {

    @GetMapping("")
    public String markdown() {
        return "markdown/editor";
    }

    @PostMapping("/expHtml")
    public void expHtml(HttpServletRequest request, HttpServletResponse response) {
        LoginUser user = (LoginUser) request.getSession().getAttribute("user");
        if (user != null) {
            log.info("用户 {}导出了html", user.getUsername());
        }
        response.setContentType(DownloadUtil.HTML);// 下载东西的格式
        response.setHeader("Content-disposition", "attachment;filename=" + "WannaCorn.html");
        try (PrintWriter writer = new PrintWriter(response.getOutputStream())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<!DOCTYPE html>\n<html lang='zh-CN'>\n<head>\n")
                    .append("    <meta charset='UTF-8'>\n    <title>Title</title>\n</head>\n<body>\n")
                    .append(request.getParameter("htmlText"))
                    .append("</body>\n</html>");
            writer.write(buffer.toString().toCharArray());
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
