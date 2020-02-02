package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.entity.PageBean;
import com.BookKeeping.service.BookkeepingService;
import com.BookKeeping.util.RedisUtil;
import com.BookKeeping.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookkeeping")
public class BookkeepingController extends ExceptionController {

    @Autowired
    private BookkeepingService bookkeepingService;
    @Autowired
    private RedisUtil redisUtil;

    @RequiresRoles("user")
    @RequestMapping(value = "/listIncome", method = RequestMethod.POST)
    @ResponseBody
    public Result listIncome(@RequestBody Map<String, Object> map, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            map.put("userId", redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            return null;//未获取userId（用户未登录）
        }
        //设置查询范围（分页查询）
        if(map.get("page") != null && map.get("rows") != null) {
            PageBean pageBean = new PageBean(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
            map.put("start", pageBean.getStart());
            map.put("size", pageBean.getPageSize());
        }
        //模糊查询（字符串两端加%）
        if(map.get("bkDateStr") != null) {
            map.put("bkDateStr", StringUtil.formatLike(map.get("bkDateStr").toString()));
        }
        if(map.get("remarkText") != null) {
            map.put("remarkText", StringUtil.formatLike(map.get("remarkText").toString()));
        }
        //数据库查询
        List<Bookkeeping> bookkeepingList = bookkeepingService.listIncome(map);
        Integer totalIncome = bookkeepingService.totalIncome(map);//收入账单的总条数
        Float sumIncomeMoney = bookkeepingService.sumIncomeMoney(map);//收入账单的总收入
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("bookkeepingList", bookkeepingList);
        dataMap.put("totalIncome", totalIncome);
        dataMap.put("sumIncomeMoney", sumIncomeMoney);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/listExpend", method = RequestMethod.POST)
    @ResponseBody
    public Result listExpend(@RequestBody Map<String, Object> map, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            map.put("userId", redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            return null;//未获取userId（用户未登录）
        }
        //设置查询范围（分页查询）
        if(map.get("page") != null && map.get("rows") != null) {
            PageBean pageBean = new PageBean(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
            map.put("start", pageBean.getStart());
            map.put("size", pageBean.getPageSize());
        }
        //模糊查询（字符串两端加%）
        if(map.get("bkDateStr") != null) {
            map.put("bkDateStr", StringUtil.formatLike(map.get("bkDateStr").toString()));
        }
        if(map.get("remarkText") != null) {
            map.put("remarkText", StringUtil.formatLike(map.get("remarkText").toString()));
        }
        //数据库查询
        List<Bookkeeping> bookkeepingList = bookkeepingService.listExpend(map);
        Integer totalExpend = bookkeepingService.totalExpend(map);//支出账单的总条数
        Float sumExpendMoney = bookkeepingService.sumExpendMoney(map);//支出账单的总支出
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("bookkeepingList", bookkeepingList);
        dataMap.put("totalExpend", totalExpend);
        dataMap.put("sumExpendMoney", sumExpendMoney);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    @ResponseBody
    public Result listAll(@RequestBody Map<String, Object> map, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            map.put("userId", redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            return null;//未获取userId（用户未登录）
        }
        //设置查询范围（分页查询）
        if(map.get("page") != null && map.get("rows") != null) {
            PageBean pageBean = new PageBean(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
            map.put("start", pageBean.getStart());
            map.put("size", pageBean.getPageSize());
        }
        //模糊查询（字符串两端加%）
        if(map.get("bkDateStr") != null) {
            map.put("bkDateStr", StringUtil.formatLike(map.get("bkDateStr").toString()));
        }
        if(map.get("remarkText") != null) {
            map.put("remarkText", StringUtil.formatLike(map.get("remarkText").toString()));
        }
        //数据库查询
        List<Bookkeeping> bookkeepingList = bookkeepingService.listAll(map);//账单信息
        Integer totalIncome = bookkeepingService.totalIncome(map);//收入账单的总条数
        Integer totalExpend = bookkeepingService.totalExpend(map);//支出账单的总条数
        Float sumIncomeMoney = bookkeepingService.sumIncomeMoney(map);//收入账单的总收入
        Float sumExpendMoney = bookkeepingService.sumExpendMoney(map);//支出账单的总支出
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("bookkeepingList", bookkeepingList);
        dataMap.put("totalIncome", totalIncome);
        dataMap.put("totalExpend", totalExpend);
        dataMap.put("sumIncomeMoney", sumIncomeMoney);
        dataMap.put("sumExpendMoney", sumExpendMoney);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }

    /**
     *
     * @param map 必须输入bkDateStr(格式：yyyy-MM-dd)
     * @param token
     * @return
     */
    @RequiresRoles("user")
    @RequestMapping(value = "/allIncomeExpendMoney", method = RequestMethod.POST)
    @ResponseBody
    public Result allIncomeExpendMoney(@RequestBody Map<String, Object> map, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            map.put("userId", redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            return null;//未获取userId（用户未登录）
        }

        String bkDateStr;
        if(map.get("bkDateStr") != null) {
            bkDateStr = map.get("bkDateStr").toString();
        } else {
            return null;//没有输入日期
        }

        //数据库查询本日
        map.put("bkDateStr", StringUtil.formatLike(bkDateStr));
        Float todayIncomeMoney = bookkeepingService.sumIncomeMoney(map);//收入账单的本日总收入
        Float todayExpendMoney = bookkeepingService.sumExpendMoney(map);//支出账单的本日总支出

        //数据库查询本月
        map.put("bkDateStr", StringUtil.formatLike(bkDateStr.substring(0, 7)));
        Float monthIncomeMoney = bookkeepingService.sumIncomeMoney(map);//收入账单的本月总收入
        Float monthExpendMoney = bookkeepingService.sumExpendMoney(map);//支出账单的本月总支出

        //数据库查询全部
        map.remove("bkDateStr");
        Float allIncomeMoney = bookkeepingService.sumIncomeMoney(map);//收入账单的全部总收入
        Float allExpendMoney = bookkeepingService.sumExpendMoney(map);//支出账单的全部总支出

        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("todayIncomeMoney", todayIncomeMoney);
        dataMap.put("todayExpendMoney", todayExpendMoney);
        dataMap.put("monthIncomeMoney", monthIncomeMoney);
        dataMap.put("monthExpendMoney", monthExpendMoney);
        dataMap.put("allIncomeMoney", allIncomeMoney);
        dataMap.put("allExpendMoney", allExpendMoney);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }

}
