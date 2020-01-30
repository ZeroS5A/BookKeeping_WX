package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.Token;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.util.TokenUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        String session=loginService.getSession(code);
        String token=tku.creatToken(loginService.getOpenId(session),"user");

        result.setSession(session);
        result.setToken(token);

        rs.setData(result);
        return rs;
    }

    @RequiresRoles("user")
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

    @RequiresRoles("user")
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

}
