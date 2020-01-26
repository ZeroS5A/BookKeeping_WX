package com.BookKeeping.service;

import com.BookKeeping.entity.user;

import java.util.List;

public interface userService {
    //查询
    List<user> selAll();

   //添加
    int insertUser(user us);
}
