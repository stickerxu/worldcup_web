package com.cup.worldcup.service;

import com.cup.worldcup.entity.Article;

import java.util.List;

public interface ArticleService {

    void saveArticle(Article article);

    Integer getMaxArticleIdByType(Integer type);

    List<Article> listTopTenArticleByType(Integer type);

    Article getArticleById(Integer id);
}
