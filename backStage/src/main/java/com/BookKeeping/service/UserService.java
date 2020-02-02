package com.BookKeeping.service;

import com.BookKeeping.entity.User;

import java.util.List;

public interface UserService {

    //查询所有用户
    List<User> selAll();

    //添加
    Integer insertUser(User user);


}
