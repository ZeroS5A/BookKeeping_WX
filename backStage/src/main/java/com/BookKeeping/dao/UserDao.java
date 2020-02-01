package com.BookKeeping.dao;

import com.BookKeeping.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    //查询
    @Select("select * from t_user")
    List<User> selAll();

    //添加
    @Insert("INSERT INTO t_user (openId,nickName,gender,avatarUrl)VALUES(#{openId},#{nickName},#{gender},#{avatarUrl})")
    Integer insertUser(User user);

    //根据openid查询
    @Select("select * from t_user where openid=#{openid}")
    User selFromOpenId (String openId);
}
