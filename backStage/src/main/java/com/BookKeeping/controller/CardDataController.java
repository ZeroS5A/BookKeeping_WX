package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.service.CardDataService;
import com.BookKeeping.util.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/card")
public class CardDataController extends ExceptionController{
    @Autowired
    CardDataService cardDataService;
    @Autowired
    RedisUtil redisUtil;


    @RequiresRoles("user")
    @RequestMapping(value = "/InsertCardData", method = RequestMethod.POST)
    @ResponseBody
    public Result InsertCardData(@RequestHeader("Authorization") String token){
        System.out.println("InsertCardData_Run:"+redisUtil.hget(token, "id").toString());
        Result rs=new Result();
        try {
            rs.setData(cardDataService.insertCardData(redisUtil.hget(token, "id").toString()));
        } catch (Exception e) {
            System.out.println(e);
            rs.setResult(ResultStatus.SERVERERR);
            return rs;//未获取openid（用户未登录）
        }
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/deleteCardData", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCardData(@RequestHeader("Authorization") String token){
        System.out.println("deleteCardData_Run:"+redisUtil.hget(token, "id").toString());
        Result rs=new Result();
        try {
            rs.setData(cardDataService.deleteCardData(redisUtil.hget(token, "id").toString()));
        } catch (Exception e) {
            System.out.println(e);
            rs.setResult(ResultStatus.SERVERERR);
            return rs;//未获取openid（用户未登录）
        }
        return rs;
    }
}
