package com.worldcup.web.service;

import com.worldcup.web.entity.Article;

import java.util.List;

public interface ArticleService {

    /*前台*/
    List<Article> listTopTenArticleByType(Integer type);

    /*后台*/
    void saveArticle(Article article);

    Integer getMaxArticleIdByType(Integer type);

    List<Article> listArticleByCriteria(Article article);

    Integer countArticleByCriteria(Article article);

    /*公共*/
    Article getArticleById(Integer id);


}
