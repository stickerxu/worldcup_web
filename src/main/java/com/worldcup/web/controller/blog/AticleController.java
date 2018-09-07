package com.worldcup.web.controller.blog;

import com.worldcup.web.Constants;
import com.worldcup.web.entity.Article;
import com.worldcup.web.service.ArticleService;
import com.worldcup.web.service.ArticleTypeService;
import com.worldcup.web.util.ParameterUtil;
import com.worldcup.web.util.ResponsePageUtil;
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
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/article")
public class AticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;

    @Value("${web.article.path}")
    private String webArticlePath;

    @GetMapping("/{id:\\d+}")
    public String article(@PathVariable("id") Integer id, ModelMap modelMap) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            modelMap.put("message", "参数有误！");
            return ResponsePageUtil.errorPage(modelMap);
        }
        try {
            StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(webArticlePath).append(File.separator).append(article.getType())
                    .append(File.separator).append(article.getFileName());
            Document artDoc = Jsoup.parse(new File(pathBuilder.toString()), Constants.CHARSET_UTF_8, Constants.WEB_URL);
            String script = artDoc.head().select("script").outerHtml();
            String style = artDoc.head().select("style").outerHtml();
            String body = artDoc.body().html();
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append(style).append(script).append(body);
            modelMap.put("article",article);
            modelMap.put("includeHtml", htmlBuilder.toString());
            return "article/info";
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping("/more/{type:\\d+}")
    public String artMore(@PathVariable("type") Integer type, ModelMap modelMap) {
        String name = articleTypeService.getNameById(type);
        if (ParameterUtil.isNotBlank(name)) {
            modelMap.put("articleTypeName", name);
        } else {
            modelMap.put("message", "操作有误！");
            return ResponsePageUtil.errorPage(modelMap);
        }
        List<Article> articles = articleService.listArticleByType(type);
        modelMap.put(Constants.MODEL_MAP_PAGE, articles);
        return "article/list";
    }
}
