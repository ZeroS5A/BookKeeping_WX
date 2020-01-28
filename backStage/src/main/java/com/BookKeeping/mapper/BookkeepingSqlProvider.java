package com.BookKeeping.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BookkeepingSqlProvider {

    public String listIncome(Map<String, Object> map) {
        String endStr = " order by bkDate desc";
        if (map.get("start") != null && map.get("size") != null) {
            endStr += " limit "+ map.get("start") + "," + map.get("size");
        }
        String queryStr = new SQL() {
            {
                SELECT("*");
                FROM("t_bookkeeping_income");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDate") != null) WHERE("bkDate like #{bkDate}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString() + endStr;
        System.out.println(queryStr);
        return queryStr;
    }

    public String listExpend(Map<String, Object> map) {
        String endStr = " order by bkDate desc";
        if (map.get("start") != null && map.get("size") != null) {
            endStr += " limit "+ map.get("start") + "," + map.get("size");
        }
        String queryStr = new SQL() {
            {
                SELECT("*");
                FROM("t_bookkeeping_expend");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDate") != null) WHERE("bkDate like #{bkDate}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString() + endStr;
        System.out.println(queryStr);
        return queryStr;
    }

}
