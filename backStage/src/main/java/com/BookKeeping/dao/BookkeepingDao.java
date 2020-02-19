package com.BookKeeping.dao;

import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.entity.MonthsExpendTypeStatisticData;
import com.BookKeeping.entity.MonthsStatisticData;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

public interface BookkeepingDao {

    /**带参数查找收入表*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="listIncome")
    public List<Bookkeeping> listIncome(Map<String, Object> map);

    /**带参数查找支出表*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="listExpend")
    public List<Bookkeeping> listExpend(Map<String, Object> map);

    /**带参数查找收入支出表*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="listAll")
    public List<Bookkeeping> listAll(Map<String, Object> map);

    /**带参数查找收入表总条数*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="totalIncome")
    public Integer totalIncome(Map<String, Object> map);

    /**带参数查找支出表总条数*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="totalExpend")
    public Integer totalExpend(Map<String, Object> map);

    /**带参数查找收入表总收入*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="sumIncomeMoney")
    public Float sumIncomeMoney(Map<String, Object> map);

    /**带参数查找支出表总支出*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="sumExpendMoney")
    public Float sumExpendMoney(Map<String, Object> map);

    /**插入收入数据*/
    @InsertProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="insertIncome")
    public Integer insertIncome(Bookkeeping bookkeeping);

    /**插入支出数据*/
    @InsertProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="insertExpend")
    public Integer insertExpend(Bookkeeping bookkeeping);

    /**更新收入数据*/
    @UpdateProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="updateIncome")
    public Integer updateIncome(Bookkeeping bookkeeping);

    /**更新支出数据*/
    @UpdateProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="updateExpend")
    public Integer updateExpend(Bookkeeping bookkeeping);

    /**删除收入数据*/
    @DeleteProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="deleteIncome")
    public Integer deleteIncome(Bookkeeping bookkeeping);

    /**删除支出数据*/
    @DeleteProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="deleteExpend")
    public Integer deleteExpend(Bookkeeping bookkeeping);

    /**查找所有的月度统计*/
    @SelectProvider(type = com.BookKeeping.mapper.BookkeepingSqlProvider.class,method = "listMonthsIncomeExpend")
    public List<MonthsStatisticData> listMonthsIncomeExpend(String openId);

    /**按支出类型查找月度统计*/
    @SelectProvider(type = com.BookKeeping.mapper.BookkeepingSqlProvider.class,method = "listExpendByType")
    public List<MonthsExpendTypeStatisticData> listExpendByType(Map<String,String> map);

    /**按支出类型排序*/
    @SelectProvider(type = com.BookKeeping.mapper.BookkeepingSqlProvider.class,method = "listExpendTypeList")
    public List<Bookkeeping> listExpendTypeList(Map<String,String> map);

}
