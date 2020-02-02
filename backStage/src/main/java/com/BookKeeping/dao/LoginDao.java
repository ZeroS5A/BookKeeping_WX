package com.BookKeeping.dao;

import com.BookKeeping.entity.User;
import org.apache.ibatis.annotations.Select;

public interface LoginDao {

    //根据openid查询
    @Select("select * from t_user where openid=#{openid}")
    User getByOpenId(String openId);


}
