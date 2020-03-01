package com.BookKeeping.dao;

import com.BookKeeping.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDao_db2 {
    //查询
    @Select("select * from crawexpenddata_user")
    List<User> selAll();
}
