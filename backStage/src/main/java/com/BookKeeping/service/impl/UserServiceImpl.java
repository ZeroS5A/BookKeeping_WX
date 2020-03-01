package com.BookKeeping.service.impl;

import com.BookKeeping.common.DBContextHolder;
import com.BookKeeping.dao.LoginDao;
import com.BookKeeping.dao.UserDao;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    //查询用户
    public List<User> selAll() {
        System.out.println("Spring创建");
        return userDao.selAll();
    }

    //添加用户
    public Integer insertUser(User user){
        System.out.println("Spring中添加用户");
        System.out.println(user);
        return userDao.insertUser(user);
    }

    @Override
    public Integer insertFeedback(String openId, Map<String,String> feedbackData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        feedbackData.put("openId",openId);
        feedbackData.put("date",sdf.format(new Date()));
        return userDao.insertFeedback(feedbackData);
    }


}
