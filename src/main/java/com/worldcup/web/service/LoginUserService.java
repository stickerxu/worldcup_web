package com.worldcup.web.service;

import com.worldcup.web.entity.LoginUser;

import java.util.List;

public interface LoginUserService {
    public void insertLoginUser(LoginUser loginUser);

    public void deleteLoginUserById(int id);

    public void updateLoginUserById(LoginUser loginUser);

    public void updateLoginUserPasswordById(LoginUser loginUser);

    public LoginUser getUserByUsername(String username);

    public List<LoginUser> listUserByCondition(LoginUser loginUser);
}
