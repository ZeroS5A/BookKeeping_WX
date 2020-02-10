package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.entity.*;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.service.UserService;
import com.BookKeeping.entity.User;
import com.BookKeeping.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class UserController {

    //自动注入
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/selAll", method = RequestMethod.POST)
    @ResponseBody
    public Result selAll(){
        Result rs= new Result();
        System.out.println("调用查询");
        List<User> userList = userService.selAll();
        rs.setData(userList);
        rs.setMessage("success");
        return rs;
    }

    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUser(@RequestBody User us){
        Result rs = new Result();
        System.out.println("调用写入用户");
        rs.setCode(userService.insertUser(us));
        return rs;
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @ResponseBody
    public Result feedback(@RequestBody Map<String,String> feeBackData, @RequestHeader("Authorization") String token){
        Result rs = new Result();
        try {
            rs.setData(userService.insertFeedback(redisUtil.hget(token, "id").toString(),feeBackData));
        } catch (Exception e) {
            rs.setResult(ResultStatus.SERVERERR);
            return rs;//未获取openid（用户未登录）
        }
        return rs;
    }
}
