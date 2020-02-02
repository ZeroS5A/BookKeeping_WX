package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import com.BookKeeping.common.ShiroRealm;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.RedisUtil;
import com.BookKeeping.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private RedisUtil redisUtil;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody String code){
        Result rs=new Result();
        Login result=new Login();
        TokenUtil tku=new TokenUtil();

        logger.info("前端发来的code:"+code);
        //获取微信session和生成自定义token
        HttpUtil httpUtil=new HttpUtil();

        //获取session_key和openid
        JSONObject session_key=httpUtil.domain("getSession_key",code);
        String session=session_key.getString("session_key");
        String openid=session_key.getString("openid");

        //判断是否获取到信息
        if(session!=null){
            //生成Token
            String token=tku.creatToken(openid,"user");

            result.setSession(session);
            result.setToken(token);

            rs.setData(result);

        }else{
            rs.setResult(ResultStatus.LOGINERR);
        }
        return rs;
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/getUserData",method = RequestMethod.POST)
    @ResponseBody                                       //从头部字段中获取信息
    public Result getUserData(@RequestBody Login login, @RequestHeader("Authorization") String token){
        System.out.println("获取用户数据");
        System.out.println("头部中的token信息"+token);

        Result rs=new Result();
        TokenUtil tku=new TokenUtil();
        //获取openid
        String openid=tku.getTokenDataOpenId(token);
        //处理用户信息
        User user=loginService.processUserdata(openid,login);

        //放入reids
        logger.info("开始放入redis");
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("openid", openid);
            map.put("id", user.getId());
            redisUtil.hmset(token, map);
        }catch (Exception e){
            logger.info("Redis写入失败"+e);
        }

        //System.out.println("从redis中获取的openid："+loginService.getDataByRedis(token,"openid"));

        //销毁openid,id后返回前端
        user.setOpenId("");
        user.setId(0);
        rs.setData(user);

        return rs;
    }

    @RequiresRoles("user")    //需要角色user才能操作
    @RequestMapping(value = "/getOpenId",method = RequestMethod.POST)
    @ResponseBody                                       //提取头部的验证信息
    public Result getOpenId(@RequestBody String session,@RequestHeader("Authorization") String token){
        Result rs=new Result();
        TokenUtil tku=new TokenUtil();

        try {
            String result=redisUtil.hget(token,"id").toString();
            //从redis获取openid
            if(result!=null){
                logger.info("Redis中的id："+result);
            }else {
                logger.info("error");
            }
        }catch (Exception e){

        }


        String openid=tku.getTokenDataOpenId(token);
        logger.info("token中获取的openid："+openid);
        rs.setData(openid);
        return rs;
    }

}
