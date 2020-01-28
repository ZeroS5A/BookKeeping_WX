package com.BookKeeping.controller;

import com.BookKeeping.common.Aes;
import com.BookKeeping.common.Result;
import com.BookKeeping.entity.*;
import com.BookKeeping.service.UserService;
import com.BookKeeping.entity.User;
import com.BookKeeping.common.HttpsRequest;
import com.alibaba.fastjson.JSONObject;
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
    //private HttpsRequestService httpsRequestService;


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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Login login){
        System.out.println("登录调用");

        Result rs=new Result();
        User us=new User();
        HttpsRequest hrs=new HttpsRequest();
        Aes aes=new Aes();

        JSONObject session_key=hrs.domain("getSession_key",login.getCode());
        JSONObject userData=aes.domain(login.getEncryptedData(),session_key.getString("session_key"),login.getIv());
//        System.out.println(login.getEncryptedData());
//        System.out.println(jo.getString("session_key"));
//        System.out.println(login.getIv());
        us.setAvatarUrl(userData.getString("avatarUrl"));
        us.setGender(userData.getInteger("gender"));
        us.setNickName(userData.getString("nickName"));
        us.setOpenId(userData.getString("openId"));
        System.out.println(us.toString());
        rs.setData(us);
        return rs;
    }

}
