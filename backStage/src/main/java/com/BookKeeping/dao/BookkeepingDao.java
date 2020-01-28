package com.BookKeeping.dao;

import com.BookKeeping.entity.Bookkeeping;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface BookkeepingDao {

    /**带参数查找收入表*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class,method="listIncome")
    public List<Bookkeeping> listIncome(Map<String, Object> map);

    /**带参数查找支出表*/
    @SelectProvider(type=com.BookKeeping.mapper.BookkeepingSqlProvider.class,method="listExpend")
    public List<Bookkeeping> listExpend(Map<String, Object> map);

}
