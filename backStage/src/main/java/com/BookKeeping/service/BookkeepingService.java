package com.BookKeeping.service;

import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.entity.MonthsExpendTypeStatisticData;
import com.BookKeeping.entity.MonthsStatisticData;

import java.util.List;
import java.util.Map;

public interface BookkeepingService {

    /**带参数查找收入表*/
    public List<Bookkeeping> listIncome(Map<String, Object> map);

    /**带参数查找支出表*/
    public List<Bookkeeping> listExpend(Map<String, Object> map);

    /**带参数查找收入支出表*/
    public List<Bookkeeping> listAll(Map<String, Object> map);

    /**带参数查找收入表总条数*/
    public Integer totalIncome(Map<String, Object> map);

    /**带参数查找支出表总条数*/
    public Integer totalExpend(Map<String, Object> map);

    /**带参数查找收入表总收入*/
    public Float sumIncomeMoney(Map<String, Object> map);

    /**带参数查找支出表总支出*/
    public Float sumExpendMoney(Map<String, Object> map);

    /**插入账单数据*/
    public Integer insertBookkeeping(Bookkeeping bookkeeping);

    /**更新账单数据*/
    public Integer updateBookkeeping(Bookkeeping bookkeeping);

    /**删除账单数据*/
    public Integer deleteBookkeeping(Bookkeeping bookkeeping);

    /**每个月的支出和收入统计*/
    public List<MonthsStatisticData> listMonthsIncomeExpend(String openId);

    /**每月按类型统计支出*/
    public List<MonthsExpendTypeStatisticData> listExpendByType(String openId, String dateStr);
}
