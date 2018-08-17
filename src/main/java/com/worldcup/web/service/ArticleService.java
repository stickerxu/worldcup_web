package com.worldcup.web.service;

import com.worldcup.web.entity.Article;

import java.util.List;

public interface ArticleService {

    /*前台*/
    List<Article> listTopTenArticleByType(Integer type);

    List<Article> listArticleByType(Integer type);

    Integer countArticleByType(Article article);

    Article getArticleById(Integer id);


}
