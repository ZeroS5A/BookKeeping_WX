package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.common.ShiroRealm;
import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.entity.PageBean;
import com.BookKeeping.service.BookkeepingService;
import com.BookKeeping.util.RedisUtil;
import com.BookKeeping.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bookkeeping")
public class BookkeepingController extends ExceptionController {

    @Autowired
    private BookkeepingService bookkeepingService;
    @Autowired
    private RedisUtil redisUtil;

    Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

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
        //分组，bookkeepingList必须按日期逆序存储
        Bookkeeping bkTemp = null;//循环用临时存储
        Bookkeeping bkTemp0 = null;//嵌套循环用临时存储
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日 E");//返回日期头的格式
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        List<Object> bookkeepingAllList = new ArrayList<>();//用于存储分组后的所有数据
        Iterator<Bookkeeping> bookkeepingIterator = bookkeepingList.iterator();//迭代器，防止循环出错
        while (bookkeepingIterator.hasNext() || bkTemp0 != null) {
            Map<String, Object> dayData = new HashMap<String, Object>();//用于存储此组的所有数据
            List<Bookkeeping> bookkeepingDayList = new ArrayList<>();//用于存储某一天的账单数据
            Float dayIncomeMoney = 0.00F;//用于存储某一天的总收入
            Float dayExpendMoney = 0.00F;//用于存储某一天的总支出

            if(bkTemp0 == null) {
                bkTemp = bookkeepingIterator.next();//如果bkTemp0没有数据，则从查询的数据中获取一个数据
            } else {
                bkTemp = bkTemp0;//如果bkTemp0存在数据，则取其值
                bkTemp0 = null;//重要！否则数据将出现问题
            }

            if(bkTemp.getIncomeOrExpend().equals("income")) dayIncomeMoney += bkTemp.getBkMoney();
            else if(bkTemp.getIncomeOrExpend().equals("expend")) dayExpendMoney += bkTemp.getBkMoney();
            else return null;//数据异常

            bkTemp.setShowTime(sdf2.format(bkTemp.getBkDate())); //重写日期格式为时分
            bookkeepingDayList.add(bkTemp);//添加
            bookkeepingIterator.remove();//删除
//------------------------------------------------------------------------------------------------------------
            while (bookkeepingIterator.hasNext()) {
                bkTemp0 = bookkeepingIterator.next();//从查询的数据中获取一个数据

                if(!sdf.format(bkTemp.getBkDate()).equals(sdf.format(bkTemp0.getBkDate()))) break;//如果日期不相等则结束嵌套循环

                bkTemp = bkTemp0;//取其值
                bkTemp0 = null;//重要！否则数据将出现问题

                if(bkTemp.getIncomeOrExpend().equals("income")) dayIncomeMoney += bkTemp.getBkMoney();
                else if(bkTemp.getIncomeOrExpend().equals("expend")) dayExpendMoney += bkTemp.getBkMoney();
                else return null;//数据异常

                bkTemp.setShowTime(sdf2.format(bkTemp.getBkDate())); //重写日期格式为时分
                bookkeepingDayList.add(bkTemp);//添加
                bookkeepingIterator.remove();//删除
            }
//------------------------------------------------------------------------------------------------------------
            dayData.put("bookkeepingDayList", bookkeepingDayList);
            dayData.put("dayDate", sdf1.format(bkTemp.getBkDate()));
            dayData.put("dayIncomeMoney", dayIncomeMoney);
            dayData.put("dayExpendMoney", dayExpendMoney);
            bookkeepingAllList.add(dayData);
        }
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("bookkeepingAllList", bookkeepingAllList);
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

    /**
     *
     * @param bookkeeping id：为空时插入数据，非空时更新数据 incomeOrExpend：必须输入，用于判断收入或支出
     * @param token
     * @return
     */
    @RequiresRoles("user")
    @RequestMapping(value = "/editBookkeeping", method = RequestMethod.POST)
    @ResponseBody
    public Result editBookkeeping(@RequestBody Bookkeeping bookkeeping, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            bookkeeping.setUserId(redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            logger.info("设置openid错误");
            return null;//未获取userId（用户未登录）
        }
        Integer editNumber;
        if(bookkeeping.getId() == null) {//id为空，添加数据
            editNumber = bookkeepingService.insertBookkeeping(bookkeeping);
        } else {//id非空，更新数据
            editNumber = bookkeepingService.updateBookkeeping(bookkeeping);
        }
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("editNumber", editNumber);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }

    /**
     *
     * @param bookkeeping id：必须输入 incomeOrExpend：必须输入，用于判断收入或支出
     * @param token
     * @return
     */
    @RequiresRoles("user")
    @RequestMapping(value = "/deleteBookkeeping", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteBookkeeping(@RequestBody Bookkeeping bookkeeping, @RequestHeader("Authorization") String token) {
        //获取userId
        try {
            bookkeeping.setUserId(redisUtil.hget(token, "id").toString());
        } catch (Exception e) {
            return null;//未获取userId（用户未登录）
        }
        //删除数据
        Integer deleteNumber = bookkeepingService.deleteBookkeeping(bookkeeping);
        //写入返回数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deleteNumber", deleteNumber);
        Result rs = new Result();
        rs.setData(dataMap);
        rs.setMessage("success");
        return rs;
    }


    @RequiresRoles("user")
    @RequestMapping(value = "/listMonthsIncomeExpend", method = RequestMethod.POST)
    @ResponseBody
    public Result listMonthsIncomeExpend(@RequestHeader("Authorization") String token){
        Result rs=new Result();
        try {
            rs.setData(bookkeepingService.listMonthsIncomeExpend(redisUtil.hget(token, "id").toString()));
        } catch (Exception e) {
            rs.setResult(ResultStatus.SERVERERR);
            return rs;//未获取openid（用户未登录）
        }
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/MonthsExpendTypeStatisticData", method = RequestMethod.POST)
    @ResponseBody
    public Result MonthsExpendTypeStatisticData(@RequestBody String dateStr, @RequestHeader("Authorization") String token){
        Result rs=new Result();
        dateStr+='%';
        try {
            rs.setData(bookkeepingService.listExpendByType(redisUtil.hget(token, "id").toString(),dateStr));
        } catch (Exception e) {
            rs.setResult(ResultStatus.SERVERERR);
            return rs;//未获取openid（用户未登录）
        }
        return rs;
    }
}
