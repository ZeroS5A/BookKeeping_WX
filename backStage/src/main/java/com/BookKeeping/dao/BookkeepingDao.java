package com.BookKeeping.dao;

import com.BookKeeping.entity.Bookkeeping;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

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
    @InsertProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="updateIncome")
    public Integer updateIncome(Bookkeeping bookkeeping);

    /**更新收入数据*/
    @InsertProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class, method="updateExpend")
    public Integer updateExpend(Bookkeeping bookkeeping);

}
