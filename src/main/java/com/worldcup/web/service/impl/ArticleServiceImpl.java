package com.worldcup.web.service.impl;

import com.worldcup.web.entity.Article;
import com.worldcup.web.mapper.ArticleMapper;
import com.worldcup.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> listArticleByType(Integer type) {
        return articleMapper.listArticleByType(type);
    }

    @Override
    public Integer countArticleByType(Article article) {
        return articleMapper.countArticleByType(article);
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
