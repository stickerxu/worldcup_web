package com.worldcup.web.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 5619835167434421102L;

    private int id;
    private String username;
    private String password;
    private String realName; //注册时不填
    private Integer gender; //性别：0、女 1、男 2、保密
    private String userEmail;
    private String userPhone;
    private String investCode; //注册时生成
    private Date registryTime;

}
