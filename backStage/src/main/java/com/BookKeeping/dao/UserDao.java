package com.BookKeeping.dao;

import com.BookKeeping.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    //查询
    @Select("select * from t_user")
    List<User> selAll();

    //添加
    @Insert("INSERT INTO t_user (openId,nickName,gender,avatarUrl)VALUES(#{openId},#{nickName},#{gender},#{avatarUrl})")
    Integer insertUser(User user);

    //更新
    @Update("UPDATE t_user SET nickName=#{nickName},gender=#{gender},avatarUrl=#{avatarUrl} WHERE openId=#{openId}")
    Integer updateUser(User user);

    @Insert("INSERT INTO t_feedback (openId,date,model,feedbackData)VALUES(#{openId},#{date},#{model},#{feedbackData})")
    Integer insertFeedback(Map<String,String> map);
}
