package com.worldcup.web.service;

import com.worldcup.web.entity.ArticleType;

import java.util.List;

public interface ArticleTypeService {

    List<ArticleType> listIdAndNameByStatusLimitNum();

    String getNameById(Integer id);
}
