package com.BookKeeping.service.impl;

import com.BookKeeping.dao.userDao;
import com.BookKeeping.entity.user;
import com.BookKeeping.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("user")
public class userImpl implements userService{

    @Autowired
    userDao iud;

    //查询用户
    public List<user> selAll() {
        System.out.println("Spring创建");
        return iud.selAll();
    }

    //添加用户
    public int insertUser(user us){
        System.out.println("Spring中添加用户");
        System.out.println(us);
        return iud.insertUser(us);
    }
}
