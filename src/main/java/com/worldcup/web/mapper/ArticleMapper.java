package com.worldcup.web.mapper;

import com.worldcup.web.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
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
