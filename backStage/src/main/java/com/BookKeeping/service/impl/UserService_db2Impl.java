package com.BookKeeping.service.impl;

import com.BookKeeping.common.DBContextHolder;
import com.BookKeeping.dao.UserDao_db2;
import com.BookKeeping.service.UserService_db2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service("userService_db2")
public class UserService_db2Impl implements UserService_db2 {
    @Autowired
    UserDao_db2 userDao_db2;

    @Override
    public Map<String, String> selAll() {
        System.out.println("star");
        DBContextHolder.setDbType("dataSource2");
        System.out.println(DBContextHolder.getDbType());
        userDao_db2.selAll();
        return null;
    }
}
