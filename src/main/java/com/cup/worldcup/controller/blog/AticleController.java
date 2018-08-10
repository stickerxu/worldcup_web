package com.cup.worldcup.controller.blog;

import com.cup.worldcup.entity.Article;
import com.cup.worldcup.service.ArticleService;
import com.cup.worldcup.util.ResponsePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/article")
public class AticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id:\\d+}")
    public String article(@PathVariable("id") Integer id, ModelMap modelMap) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return ResponsePageUtil.errorPage("参数有误！", modelMap);
        }
        modelMap.put("article",article);
        return "article/info";
    }
}
