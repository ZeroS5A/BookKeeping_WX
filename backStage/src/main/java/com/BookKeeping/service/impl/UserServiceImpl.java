package com.BookKeeping.service.impl;

import com.BookKeeping.dao.LoginDao;
import com.BookKeeping.dao.UserDao;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String processUserdata(String openId) {
        User us=userDao.selFromOpenId(openId);

        if(us.getOpenId()!=null){
            System.out.println("用户存在");
        }else {
            System.out.println("用户不存在");
        }

        return null;
    }

}
