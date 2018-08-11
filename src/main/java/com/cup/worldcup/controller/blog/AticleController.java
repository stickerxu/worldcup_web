package com.cup.worldcup.controller.blog;

import com.cup.worldcup.Constants;
import com.cup.worldcup.entity.Article;
import com.cup.worldcup.service.ArticleService;
import com.cup.worldcup.util.ResponsePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/article")
public class AticleController {
    @Autowired
    private ArticleService articleService;
    @Value("${web.article.path.dev}")
    private String webArticlePath;

    @GetMapping("/{id:\\d+}")
    public String article(@PathVariable("id") Integer id, ModelMap modelMap) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return ResponsePageUtil.errorPage("参数有误！", modelMap);
        }
        try {
            Document artDoc = Jsoup.parse(new File(webArticlePath + article.getFile_name()), "UTF-8", Constants.WEB_URL);
            String script = artDoc.head().select("script").outerHtml();
            String style = artDoc.head().select("style").outerHtml();
            String body = artDoc.body().html();
            StringBuilder builder = new StringBuilder();
            builder.append(style).append(script).append(body);
            modelMap.put("article",article);
            modelMap.put("includeHtml",builder.toString());
            return "article/info";
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
