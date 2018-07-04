package com.cup.worldcup.entity;

import lombok.Getter;
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
    private String real_name; //注册时不填
    private String user_email;
    private String user_phone;
    private String invest_code; //注册时生成
    private Date registry_time;

}
