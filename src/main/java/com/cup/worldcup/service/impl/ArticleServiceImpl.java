package com.cup.worldcup.service.impl;

import com.cup.worldcup.entity.Article;
import com.cup.worldcup.mapper.ArticleMapper;
import com.cup.worldcup.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void saveArticle(Article article) {
        articleMapper.saveArticle(article);
    }

    @Override
    public Integer getMaxArticleIdByType(Integer type) {
        return articleMapper.getMaxArticleIdByType(type);
    }

    @Override
    public List<Article> listArticleByCriteria(Article article) {
        return articleMapper.listArticleByCriteria(article);
    }

    @Override
    public Integer countArticleByCriteria(Article article) {
        return articleMapper.countArticleByCriteria(article);
    }

    @Override
    public List<Article> listTopTenArticleByType(Integer type) {
        return articleMapper.listTopTenArticleByType(type);
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }
}
