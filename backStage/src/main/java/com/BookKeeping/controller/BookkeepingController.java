package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.entity.PageBean;
import com.BookKeeping.service.BookkeepingService;
import com.BookKeeping.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookkeeping")
public class BookkeepingController {

    @Autowired
    private BookkeepingService bookkeepingService;

    @ResponseBody
    @RequestMapping("/listIncome")
    public Result listIncome(@RequestBody Map<String, Object> map) {
        if(map.get("page") != null && map.get("page") != "" && map.get("rows") != null && map.get("rows") != "") {
            PageBean pageBean = new PageBean(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
            map.put("start", pageBean.getStart());
            map.put("size", pageBean.getPageSize());
        }
        if(map.get("bkDate") != null) {
            map.put("bkDate", StringUtil.formatLike(map.get("bkDate").toString()));
        }
        if(map.get("remarkText") != null) {
            map.put("remarkText", StringUtil.formatLike(map.get("remarkText").toString()));
        }
        //查询
        List<Bookkeeping> bookkeepingList = bookkeepingService.listIncome(map);
        //写入数据
        Result rs = new Result();
        rs.setData(bookkeepingList);
        rs.setMessage("success");
        return rs;
    }

}
