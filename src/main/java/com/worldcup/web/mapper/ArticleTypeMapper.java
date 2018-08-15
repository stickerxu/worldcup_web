package com.worldcup.web.mapper;

import com.worldcup.web.entity.ArticleType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTypeMapper {

    List<ArticleType> listAll();
}
