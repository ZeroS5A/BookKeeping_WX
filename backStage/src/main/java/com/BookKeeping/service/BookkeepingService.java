package com.BookKeeping.service;

import com.BookKeeping.entity.Bookkeeping;

import java.util.List;
import java.util.Map;

public interface BookkeepingService {

    /**带参数查找收入表*/
    public List<Bookkeeping> listIncome(Map<String, Object> map);

    /**带参数查找支出表*/
    public List<Bookkeeping> listExpend(Map<String, Object> map);

}
