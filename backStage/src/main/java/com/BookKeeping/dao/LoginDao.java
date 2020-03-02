package com.BookKeeping.dao;

import com.BookKeeping.entity.User;
import org.apache.ibatis.annotations.Select;

public interface LoginDao {

    //根据openid查询
    @Select("select * from t_user where openid=#{openid}")
    User getByOpenId(String openId);

    //查询是否登记了学号
    @Select("SELECT\n" +
            "\tcount(*)\n" +
            "FROM\n" +
            "\tcrawexpenddata_user\n" +
            "WHERE\n" +
            "\tuserId=#{openid}")
    Integer getAccount(String openid);

}
