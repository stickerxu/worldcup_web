package com.cup.worldcup.controller;

import com.cup.worldcup.entity.Article;
import com.cup.worldcup.entity.ArticleTypeEnum;
import com.cup.worldcup.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class IndexController {
    @Autowired
    private ArticleService articleService;

    @GetMapping({"/",""})
    public String index(ModelMap modelMap) {
        List<Article> bcArts = articleService.listTopTenArticleByType(ArticleTypeEnum.BLOCKCHAIN.getType());
        modelMap.put("bcArts",bcArts);
        return "index";
    }
}
