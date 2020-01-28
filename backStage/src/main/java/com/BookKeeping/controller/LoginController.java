package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Login login){
        System.out.println("首次登录调用");

        Result rs=new Result();
        User us=loginService.userFirstLogin(login.getEncryptedData(),login.getCode(),login.getIv());
        System.out.println(us.toString());
        rs.setData(us);
        return rs;
    }
}
