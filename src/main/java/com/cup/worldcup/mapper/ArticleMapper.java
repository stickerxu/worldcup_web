package com.cup.worldcup.mapper;

import com.cup.worldcup.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    void saveArticle(Article article);

    Integer getMaxArticleIdByType(Integer type);

    List<Article> listTopTenArticleByType(Integer type);

    Article getArticleById(Integer id);
}
