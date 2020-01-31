package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.Token;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class LoginController extends ExceptionController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody String code){
        Result rs=new Result();
        Login result=new Login();
        TokenUtil tku=new TokenUtil();

        System.out.println(code);
        //获取微信session和生成自定义token
        HttpUtil hrs=new HttpUtil();

        //获取session_key和openid
        JSONObject session_key=hrs.domain("getSession_key",code);
        String session=session_key.getString("session_key");
        String openid=session_key.getString("openid");
        //生成Token
        String token=tku.creatToken(openid,"user");

        result.setSession(session);
        result.setToken(token);

        //放入redis
        loginService.setTokenToRedis(token,openid);

        rs.setData(result);
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/getUserData",method = RequestMethod.POST)
    @ResponseBody                                       //从头部字段中获取信息
    public Result getUserData(@RequestBody Login login, @RequestHeader("Authorization") String token){
        System.out.println("获取用户数据");
        System.out.println("头部中的token信息"+token);

        Result rs=new Result();
        User us=loginService.getUserData(login.getEncryptedData(),login.getCode(),login.getIv());
        System.out.println(us.toString());
        rs.setData(us);
        return rs;
    }

    @RequiresRoles("user")    //需要角色user才能操作
    @RequestMapping(value = "/getOpenId",method = RequestMethod.POST)
    @ResponseBody                                       //提取头部的验证信息
    public Result getOpenId(@RequestBody String session,@RequestHeader("Authorization") String token){
        Result rs=new Result();
        TokenUtil tku=new TokenUtil();

        //从redis获取openid
        if(loginService.getOpenId(token).equals("TokenError")){
            rs.setResult(ResultStatus.DATAEXIST);
        }else{
            System.out.println("从redis中获取的openid："+loginService.getOpenId(token));
        }


        String openid=tku.getTokenDataOpenId(token);
        System.out.println("token中获取的openid："+openid);
        rs.setData(openid);
        return rs;
    }

}
