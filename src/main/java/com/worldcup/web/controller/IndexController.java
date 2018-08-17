package com.worldcup.web.controller;

import com.worldcup.web.entity.Article;
import com.worldcup.web.entity.ArticleType;
import com.worldcup.web.service.ArticleService;
import com.worldcup.web.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;

    @GetMapping({"/",""})
    public String index(ModelMap modelMap) {
        //查询所有文章类型
        List<ArticleType> types = articleTypeService.listIdAndNameByStatusLimitNum();
        //查询所有文章类型对应的最新10篇文章
        Map<String, Object> articleMap = new LinkedHashMap<>();
        List<Article> articles;
        //将每种类型名称及对应文章放入map中
        for (ArticleType type : types) {
            articles = articleService.listTopTenArticleByType(type.getId());
            articleMap.put(type.getName(), articles);
        }
        modelMap.put("articleMap",articleMap);
        return "index";
    }
}
