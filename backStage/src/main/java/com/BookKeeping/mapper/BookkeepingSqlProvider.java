package com.BookKeeping.mapper;

import com.BookKeeping.entity.Bookkeeping;
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
                SELECT("*, 'income' as incomeOrExpend");
                FROM("t_bookkeeping_income");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
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
                SELECT("*, 'expend' as incomeOrExpend");
                FROM("t_bookkeeping_expend");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString() + endStr;
        System.out.println(queryStr);
        return queryStr;
    }

    public String listAll(Map<String, Object> map) {
        String endStr = " order by bkDate desc";
        if (map.get("start") != null && map.get("size") != null) {
            endStr += " limit "+ map.get("start") + "," + map.get("size");
        }
        String queryIncomeStr = new SQL() {
            {
                SELECT("*, 'income' as incomeOrExpend");
                FROM("t_bookkeeping_income");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        String queryExpendStr = new SQL() {
            {
                SELECT("*, 'expend' as incomeOrExpend");
                FROM("t_bookkeeping_expend");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        String queryStr = queryIncomeStr + " union all " + queryExpendStr + endStr;
        System.out.println(queryStr);
        return queryStr;
    }

    public String totalIncome(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("count(*)");
                FROM("t_bookkeeping_income");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String totalExpend(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("count(*)");
                FROM("t_bookkeeping_expend");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String sumIncomeMoney(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("sum(bkMoney)");
                FROM("t_bookkeeping_income");
//                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
//                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
//                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
//                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String sumExpendMoney(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("sum(bkMoney)");
                FROM("t_bookkeeping_expend");
//                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
//                if (map.get("bkType") != null) WHERE("bkType = #{bkType}");
                if (map.get("bkDateStr") != null) WHERE("bkDate like #{bkDateStr}");
//                if (map.get("bkMoney") != null) WHERE("bkMoney = #{bkMoney}");
//                if (map.get("remarkText") != null) WHERE("remarkText like #{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String insertIncome(Bookkeeping bookkeeping) {
        String queryStr = new SQL() {
            {
                INSERT_INTO("t_bookkeeping_income");
//                if (bookkeeping.getId() != null) VALUES("id", "#{id}");
                if (bookkeeping.getUserId() != null) VALUES("userId", "#{userId}");
                if (bookkeeping.getBkType() != null) VALUES("bkType", "#{bkType}");
                if (bookkeeping.getBkDate() != null) VALUES("bkDate", "#{bkDate}");
                if (bookkeeping.getBkMoney() != null) VALUES("bkMoney", "#{bkMoney}");
                if (bookkeeping.getRemarkText() != null) VALUES("remarkText", "#{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String insertExpend(Bookkeeping bookkeeping) {
        String queryStr = new SQL() {
            {
                INSERT_INTO("t_bookkeeping_expend");
//                if (bookkeeping.getId() != null) VALUES("id", "#{id}");
                if (bookkeeping.getUserId() != null) VALUES("userId", "#{userId}");
                if (bookkeeping.getBkType() != null) VALUES("bkType", "#{bkType}");
                if (bookkeeping.getBkDate() != null) VALUES("bkDate", "#{bkDate}");
                if (bookkeeping.getBkMoney() != null) VALUES("bkMoney", "#{bkMoney}");
                if (bookkeeping.getRemarkText() != null) VALUES("remarkText", "#{remarkText}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String updateIncome(Bookkeeping bookkeeping) {
        String queryStr = new SQL() {
            {
                UPDATE("t_bookkeeping_income");
                if (bookkeeping.getBkType() != null) SET("bkType = #{bkType}");
                if (bookkeeping.getBkDate() != null) SET("bkDate = #{bkDate}");
                if (bookkeeping.getBkMoney() != null) SET("bkMoney = #{bkMoney}");
                if (bookkeeping.getRemarkText() != null) SET("remarkText = #{remarkText}");
                WHERE ("id = #{id}");
                WHERE ("userId = #{userId}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

    public String updateExpend(Bookkeeping bookkeeping) {
        String queryStr = new SQL() {
            {
                UPDATE("t_bookkeeping_expend");
                if (bookkeeping.getBkType() != null) SET("bkType = #{bkType}");
                if (bookkeeping.getBkDate() != null) SET("bkDate = #{bkDate}");
                if (bookkeeping.getBkMoney() != null) SET("bkMoney = #{bkMoney}");
                if (bookkeeping.getRemarkText() != null) SET("remarkText = #{remarkText}");
                WHERE ("id = #{id}");
                WHERE ("userId = #{userId}");
            }
        }.toString();
        System.out.println(queryStr);
        return queryStr;
    }

}
