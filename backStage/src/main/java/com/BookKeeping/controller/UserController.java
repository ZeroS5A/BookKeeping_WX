package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
<<<<<<< HEAD:backStage/src/main/java/com/BookKeeping/controller/userCon.java
import com.BookKeeping.entity.*;
import com.BookKeeping.service.userService;
=======
import com.BookKeeping.entity.User;
import com.BookKeeping.service.UserService;
>>>>>>> ebf6c0d621cdfe98e702fb3f0f76c5e626d3495a:backStage/src/main/java/com/BookKeeping/controller/UserController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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

<<<<<<< HEAD:backStage/src/main/java/com/BookKeeping/controller/userCon.java
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody login login){
        System.out.println("登录调用");
        Result rs=new Result();

        return rs;
    }
=======
>>>>>>> ebf6c0d621cdfe98e702fb3f0f76c5e626d3495a:backStage/src/main/java/com/BookKeeping/controller/UserController.java
}
