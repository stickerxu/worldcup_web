package com.worldcup.web.service.impl;

import com.worldcup.web.entity.ArticleType;
import com.worldcup.web.mapper.ArticleTypeMapper;
import com.worldcup.web.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {
    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    @Override
    public List<ArticleType> listIdAndNameByStatusLimitNum() {
        return articleTypeMapper.listIdAndNameByStatusLimitNum();
    }

    @Override
    public String getNameById(Integer id) {
        return articleTypeMapper.getNameById(id);
    }
}
