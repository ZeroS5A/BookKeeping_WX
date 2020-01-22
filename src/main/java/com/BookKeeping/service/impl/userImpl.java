package com.BookKeeping.service.impl;

import com.BookKeeping.entity.user;
import com.BookKeeping.service.userService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("user")
public class userImpl implements userService{

    public List<user> selAll() {
        System.out.println("success");
        return null;
    }
}
