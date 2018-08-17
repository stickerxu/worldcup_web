package com.worldcup.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ArticleType extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5280319170213848382L;

    private Integer id;
    private String name;
    //1 不显示；2 显示
    private Integer status = 1;
    private Integer weight = 0;
}
