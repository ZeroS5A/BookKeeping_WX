package com.BookKeeping.service;

import com.BookKeeping.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    //查询所有用户
    List<User> selAll();

    //添加
    Integer insertUser(User user);

    //意见反馈
    Integer insertFeedback(String openId, Map<String,String> feedbackData);

}
