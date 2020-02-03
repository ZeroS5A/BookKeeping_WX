package com.BookKeeping.service.impl;

import com.BookKeeping.dao.BookkeepingDao;
import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.service.BookkeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("bookkeepingService")
public class BookkeepingServiceImpl implements BookkeepingService {

    @Autowired
    private BookkeepingDao bookkeepingDao;

    @Override
    public List<Bookkeeping> listIncome(Map<String, Object> map) {
        return bookkeepingDao.listIncome(map);
    }

    @Override
    public List<Bookkeeping> listExpend(Map<String, Object> map) {
        return bookkeepingDao.listExpend(map);
    }

    @Override
    public List<Bookkeeping> listAll(Map<String, Object> map) {
        return bookkeepingDao.listAll(map);
    }

    @Override
    public Integer totalIncome(Map<String, Object> map) {
        return bookkeepingDao.totalIncome(map);
    }

    @Override
    public Integer totalExpend(Map<String, Object> map) {
        return bookkeepingDao.totalExpend(map);
    }

    @Override
    public Float sumIncomeMoney(Map<String, Object> map) {
        return bookkeepingDao.sumIncomeMoney(map);
    }

    @Override
    public Float sumExpendMoney(Map<String, Object> map) {
        return bookkeepingDao.sumExpendMoney(map);
    }

    @Override
    public Integer insertBookkeeping(Bookkeeping bookkeeping) {
        if (bookkeeping.getIncomeOrExpend().equals("income")) {//插入收入数据
            return bookkeepingDao.insertIncome(bookkeeping);
        } else if (bookkeeping.getIncomeOrExpend().equals("expend")) {//插入支出数据
            return bookkeepingDao.insertExpend(bookkeeping);
        } else {//异常
            return 0;
        }
    }

    @Override
    public Integer updateBookkeeping(Bookkeeping bookkeeping) {
        if (bookkeeping.getIncomeOrExpend().equals("income")) {//更新收入数据
            return bookkeepingDao.updateIncome(bookkeeping);
        } else if (bookkeeping.getIncomeOrExpend().equals("expend")) {//更新支出数据
            return bookkeepingDao.updateExpend(bookkeeping);
        } else {//异常
            return 0;
        }
    }

    @Override
    public Integer deleteBookkeeping(Bookkeeping bookkeeping) {
        if (bookkeeping.getIncomeOrExpend().equals("income")) {//删除收入数据
            return bookkeepingDao.deleteIncome(bookkeeping);
        } else if (bookkeeping.getIncomeOrExpend().equals("expend")) {//删除支出数据
            return bookkeepingDao.deleteExpend(bookkeeping);
        } else {//异常
            return 0;
        }
    }

}
