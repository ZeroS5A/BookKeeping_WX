package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
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
    public Result login(@RequestBody String code){
        Result rs=new Result();
        System.out.println(code);
        String session=loginService.getSession(code);
        rs.setData(session);
        return rs;
    }

    @RequestMapping(value = "/getUserData",method = RequestMethod.POST)
    @ResponseBody
    public Result getUserData(@RequestBody Login login){
        System.out.println("获取用户数据");

        Result rs=new Result();
        User us=loginService.getUserData(login.getEncryptedData(),login.getCode(),login.getIv());
        System.out.println(us.toString());
        rs.setData(us);
        return rs;
    }

    @RequestMapping(value = "/getOpenId",method = RequestMethod.POST)
    @ResponseBody
    public Result getOpenId(@RequestBody String session){
        Result rs=new Result();
        if(loginService.getOpenId(session).equals("NOT_OPENID")){
            rs.setResult(ResultStatus.DATAEXIST);
        }else
            rs.setData(loginService.getOpenId(session));
        return rs;
    }

    //拒绝请求
    @RequestMapping(value = "/unauthorized",method = RequestMethod.POST)
    public Result unauthorized(){
        Result rs=new Result();
        rs.setResult(ResultStatus.ROLEERR);
        return rs;
    }
}
