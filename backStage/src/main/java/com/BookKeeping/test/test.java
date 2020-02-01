package com.BookKeeping.test;

import com.BookKeeping.common.Aes;
import com.BookKeeping.entity.Token;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.service.UserService;
import com.BookKeeping.service.impl.UserServiceImpl;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.RedisUtil;
import com.BookKeeping.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.BookKeeping.common.HttpsRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestOperations;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {

    @Test
    //测试Spring配置
    public void test(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService us = (UserService) ac.getBean("user");
        us.selAll();
    }
    @Test
    //测试Https请求
    public void httpRequestTest(){
        HttpsRequest rq=new HttpsRequest();
        String code="0a1oHEu90shr9A13Sjs90SbCu90oHEuv";

        //把字符串转换为json数组
        JSONObject jo=rq.domain("getSession_key","0016GKel2ryhlC0tM4cl2WEBel26GKes");
        //使用键取值
        System.out.println(jo);
        System.out.println(jo.getString("errcode"));
        System.out.println(jo.getString("openid"));
    }

    @Test
    //测试另外一种请求
    public void anotherHttpsTest(){
        String code="0016GKel2ryhlC0tM4cl2WEBel26GKes";
        String result = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session?appid=wx4d29257ff29de851&secret=1e17bdcea1a00407a083b74de2395e83&js_code="+code+"&grant_type=authorization_code");
        System.out.println(result);
    }

    @Test
    //测试解密
    public void AesDecrypt(){
        Aes aes=new Aes();
        String enc="wIjbrp8sW/x1FVeldU+Nb9IuqPQdlktgztNvqnSM1WrN8YFC1ZYHDt4MgR08p0LvlfJYA3Vk3pthe3z6NZIlbryfsbesGACt3As1JGimQO7jLbwCUhGavrsvARq2UOVntfg0jX/5ynuvwMSsHVmSSyXQtHc5bygJhldGm4CFtMFx7VDztu8Wy/otT7QuBOryqJa+JItxuALBJiIy4Bf5uOM0RZ9vmSrE+919nrJb9tPE/brLGb3tlaf3m4jvLhALH7Ls/osjJ99OwXFNYZ+z+qmMil6Kc+biQWugUppbWuswlZiXr8zJimkZBufVM/jKZWhFE7CyYdIovCqW0ha04qpkosEFx/S2ahgY7GDJlZ20aV/kcysT3STxBFq5jVUhPsijRlbh9CQFloj/S76Zzph5gfnv+OQUbNqZ/nn0SWkkaajd+iMez03QWAZ0c/p3pPVUg5D7IQNeew8z7qVPXK1U4EbS8NSFaT0E0IH5w+U=";
        String session="D11fa//1ULbQazQDt3ryXw==";
        String iv="5xcJVrXNyQDIxK1l2RS9nw==";
        JSONObject jo=aes.domain(enc,session,iv);
        System.out.println(jo);
        System.out.println(jo.getString("openId"));
    }

    @Test
    //Redis测试
    public void RedisTest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        RedisUtil redisUtil=(RedisUtil) context.getBean("redisUtil");


        //=====================testString======================
        redisUtil.set("name", "how2java");
        System.out.println(redisUtil.get("name"));
        redisUtil.del("name");
        System.out.println(redisUtil.get("w22u+bhkrpjzf1ftvUoynQ=="));

        //=====================testNumber======================
        long incr = redisUtil.incr("number", 1);
        System.out.println(incr);
        incr =redisUtil.incr("number", 1);
        System.out.println(incr);

        //=====================testMap======================
        Map<String,Object> map=new HashMap<>();
        map.put("name", "meepo");
        map.put("pwd", "password");
        redisUtil.hmset("user", map);
        System.out.println(redisUtil.hget("user","name"));
    }

    @Test
    //Token加解密测试
    public void TokenTest(){
        TokenUtil tku=new TokenUtil();
        Token tk=new Token();

        String result=tku.creatToken("asdfghjkl","admin");

        System.out.println(result);

        tk=tku.getTokenData(result);

        System.out.println(tk.toString());
    }

    @Test
    //测试查询用户
    public void openIdTest(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService us = (UserService) ac.getBean("userService");
        //us.selFromOpenId("asdf");
        LoginService ls=(LoginService) ac.getBean("loginService");
        ls.processUserdata("asdf");
    }
}
