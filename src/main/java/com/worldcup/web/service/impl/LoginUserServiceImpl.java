package com.worldcup.web.service.impl;

import com.worldcup.web.entity.LoginUser;
import com.worldcup.web.mapper.LoginUserMapper;
import com.worldcup.web.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUserServiceImpl implements LoginUserService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public void insertLoginUser(LoginUser loginUser) {
        loginUserMapper.insertLoginUser(loginUser);
    }

    @Override
    public void deleteLoginUserById(int id) {
        loginUserMapper.deleteLoginUserById(id);
    }

    @Override
    public void updateLoginUserById(LoginUser loginUser) {
        loginUserMapper.updateLoginUserById(loginUser);
    }

    @Override
    public void updateLoginUserPasswordById(LoginUser loginUser) {
        loginUserMapper.updateLoginUserPasswordById(loginUser);
    }

    @Override
    public LoginUser getUserByUsername(String username) {
        return loginUserMapper.getUserByUsername(username);
    }

    @Override
    public List<LoginUser> listUserByCondition(LoginUser loginUser) {
        return loginUserMapper.listUserByCondition(loginUser);
    }
}
