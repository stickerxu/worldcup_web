package com.worldcup.web.mapper;

import com.worldcup.web.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    /*前台*/
    List<Article> listTopTenArticleByType(Integer type);

    List<Article> listArticleByType(Integer type);

    Integer countArticleByType(Article article);

    Article getArticleById(Integer id);
}
