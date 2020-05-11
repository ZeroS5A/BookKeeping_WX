package com.BookKeeping.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CardDataDao {
    @Select("SELECT * FROM crawExpendData_expend WHERE userId_id = #{arg0} AND expendTime>#{arg1} ORDER BY expendTime DESC")
    List<Map<String,Object>> listCardData(String openid, Date expend);

    @Select("SELECT bkDate FROM `t_bookkeeping_expend` WHERE userId =#{openid} AND isCard= 1 ORDER BY bkDate DESC LIMIT 1")
    Date selTopTimeInExpend(String openid);

    @Select("SELECT expendTime FROM `crawExpendData_expend` WHERE userId_id =#{openid} ORDER BY expendTime DESC LIMIT 1")
    Date selTopTimeInCard(String openid);

    @Delete("DELETE\n" +
            "FROM\n" +
            "\tt_bookkeeping_expend\n" +
            "WHERE\n" +
            "\tisCard=1\n" +
            "\tAND\n" +
            "\tuserId = #{openid}")
    Integer delateCardData(String openid);
}
