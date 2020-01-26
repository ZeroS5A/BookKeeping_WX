package com.BookKeeping.dao;

import com.BookKeeping.entity.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface userDao {
    //查询
    @Select("select * from t_user")
    List<user> selAll();

    //添加
    @Insert("INSERT INTO t_user (openId,nickName,gender,avatarUrl)VALUES(#{openId},#{nickName},#{gender},#{avatarUrl})")
    int insertUser(user us);
}
