package com.BookKeeping.mapper;

import com.BookKeeping.entity.Bookkeeping;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.runtime.Desc;
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
                if (bookkeeping.getIsCard() != null) VALUES("isCard", "#{isCard}");
            }
        }.toString();
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
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
        //System.out.println(queryStr);
        return queryStr;
    }

    public String deleteIncome() {
        String queryStr = new SQL() {
            {
                DELETE_FROM("t_bookkeeping_income");
                WHERE ("id = #{id}");
                WHERE ("userId = #{userId}");
            }
        }.toString();
        //System.out.println(queryStr);
        return queryStr;
    }

    public String deleteExpend() {
        String queryStr = new SQL() {
            {
                DELETE_FROM("t_bookkeeping_expend");
                WHERE ("id = #{id}");
                WHERE ("userId = #{userId}");
            }
        }.toString();
        //System.out.println(queryStr);
        return queryStr;
    }

    public String listMonthsIncomeExpend () {

        String queryStr =(
                "SELECT\n" +
                        "	YEAR(a.Date) AS 'Year',MONTH(a.Date) AS 'Month',\n" +
                        "	sum(a.totExpend) AS 'totExpend',\n" +
                        "	sum(a.totIncome) AS 'totIncome'\n" +
                        "FROM(\n" +
                        "	SELECT\n" +
                        "		bkDate AS 'Date',\n" +
                        "		bkMoney AS 'totExpend',\n" +
                        "		0 AS 'totIncome'\n" +
                        "	FROM\n" +
                        "		t_bookkeeping_expend\n" +
                        "	WHERE\n" +
                        "		userId=#{openId}	\n" +
                        "UNION ALL\n" +
                        "	SELECT\n" +
                        "		bkDate AS 'Date',\n" +
                        "		0 AS 'totExpend',\n" +
                        "		bkMoney AS 'totIncome'\n" +
                        "	FROM\n" +
                        "		t_bookkeeping_income\n" +
                        "	WHERE\n" +
                        "		userId=#{openId}\n" +
                        ") a\n" +
                        "GROUP BY\n" +
                        "	YEAR(a.Date),MONTH(a.Date)\n" +
                        "ORDER BY a.Date DESC"
                );
        //System.out.println(queryStr);
        return queryStr;
    }

    public String listExpendByType(){
        String queryStr=(
                "SELECT\n" +
                        "	bkType AS 'name',\n" +
                        "	sum(bkMoney) AS 'data'\n" +
                        "FROM\n" +
                        "	t_bookkeeping_expend\n" +
                        "WHERE	userId=#{openId} \n" +
                        "	AND\n" +
                        "	bkDate like #{dateStr}\n" +
                        "GROUP BY\n" +
                        "	bkType\n" +
                        "ORDER BY	data\n" +
                        "DESC"
                );
        //System.out.println(queryStr);
        return queryStr;
    }

    public String listExpendTypeList(Map<String,String> map){
        String queryStr=(
                "SELECT\n" +
                        "	id,userId,bkType,bkDate,bkMoney,remarkText,'expend' AS incomeOrExpend\n" +
                        "FROM \n" +
                        "	`t_bookkeeping_expend`\n" +
                        "WHERE\n" +
                        "	userId=#{openId}\n" +
                        "	AND\n" +
                        "	bkDate LIKE #{dateStr}\n" +
                        "	AND\n" +
                        "	bkType LIKE #{bkType}\n" +
                        "ORDER BY "+map.get("type")+" "+map.get("orderType")
        );
        //System.out.println(queryStr);
        return queryStr;
    }
}
