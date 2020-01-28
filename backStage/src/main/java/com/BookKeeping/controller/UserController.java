package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.entity.*;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.service.UserService;
import com.BookKeeping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    //自动注入
    @Autowired
    private UserService userService;


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

}
