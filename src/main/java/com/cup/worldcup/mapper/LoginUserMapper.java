package com.cup.worldcup.mapper;

import com.cup.worldcup.entity.LoginUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface LoginUserMapper {
    public void insertLoginUser(LoginUser loginUser);

    public void deleteLoginUserById(int id);

    public void updateLoginUserById(LoginUser loginUser);

    public LoginUser getUserByUsername(String username);

    public List<LoginUser> listUserByCondition(LoginUser loginUser);
}