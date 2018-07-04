package com.cup.worldcup.service.impl;

import com.cup.worldcup.entity.LoginUser;
import com.cup.worldcup.mapper.LoginUserMapper;
import com.cup.worldcup.service.LoginUserService;
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
    public LoginUser getUserByUsername(String username) {
        return loginUserMapper.getUserByUsername(username);
    }

    @Override
    public List<LoginUser> listUserByCondition(LoginUser loginUser) {
        return loginUserMapper.listUserByCondition(loginUser);
    }
}
