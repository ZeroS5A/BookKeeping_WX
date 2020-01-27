package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.entity.*;
import com.BookKeeping.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class userCon {
    //自动注入
    @Autowired
    private userService ius;


    @RequestMapping(value = "/selAll",method = RequestMethod.POST)
    @ResponseBody
    public Result selAll(){
        Result rs= new Result();
        System.out.println("调用查询");
        List<user> user = ius.selAll();
        rs.setData(user);
        rs.setMessage("success");
        return rs;
    }

    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    @ResponseBody
    public Result insertUser(@RequestBody user us){
        Result rs=new Result();
        System.out.println("调用写入用户");
        rs.setCode(ius.insertUser(us));
        return rs;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody login login){
        System.out.println("登录调用");
        Result rs=new Result();

        return rs;
    }
}
