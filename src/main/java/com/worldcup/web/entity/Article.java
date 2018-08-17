package com.worldcup.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Article extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8234412686185630203L;

    private Integer id;
    private Integer type;
    private Integer status = 1;
    private String author = "";
    private String title;
    private String file_name;
    private Integer weight = 0;
    private Date publish_time;
    private Date create_time;
}
