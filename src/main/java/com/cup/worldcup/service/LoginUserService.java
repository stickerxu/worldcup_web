package com.cup.worldcup.service;

import com.cup.worldcup.entity.LoginUser;

import java.util.List;

public interface LoginUserService {
    public void insertLoginUser(LoginUser loginUser);

    public void deleteLoginUserById(int id);

    public void updateLoginUserById(LoginUser loginUser);

    public LoginUser getUserByUsername(String username);

    public List<LoginUser> listUserByCondition(LoginUser loginUser);
}
