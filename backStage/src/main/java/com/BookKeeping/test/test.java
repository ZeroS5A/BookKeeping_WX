package com.BookKeeping.test;

import com.BookKeeping.common.Aes;
import com.BookKeeping.common.DBContextHolder;
import com.BookKeeping.dao.UserDao;
import com.BookKeeping.dao.UserDao_db2;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.Token;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.BookkeepingService;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.service.UserService;
import com.BookKeeping.service.UserService_db2;
import com.BookKeeping.service.impl.UserServiceImpl;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.RedisUtil;
import com.BookKeeping.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.BookKeeping.common.HttpsRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestOperations;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {

    @Test
    //测试Spring配置
    public void test(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService us = (UserService) ac.getBean("userService");
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
        User us=new User();
        String enc=
                "nwsBCa9NaD5vHIDj/F9jDeirSAmqw4iza6m0r+Tir25if1awmEifwP4VoNpxjVGHkdpwYldOyVsQKAIyqNqhNqMTk6kGr8EVgycbQOb/0cZcShUe/3ZbF38kk6YPsx5XqfoJDpFG9Y/sQmbJntL9IDN7+ZoKuiNGKlPd8jjR4M9cQ9bCb+ljpchnDTno+h/uE9lJ7PD+yr48LeC6RBGqBB7XLAlF0fZ+oQE9CH7HF9CySv3+ZPNDEpCA5gotYOz1wRyNu9ltHnkMF5j1lqRLskTT10r6UxqV4bizkodQIdb/SuPL1xD2c5Se6x1D+TPpnrqYrt41/pgSSvehY8LcVW5wc+mZbQUos3MeWOPNv4GQHgy41EBz4ilUhUTj9y17lJN7+Hf8YUOyQTEY8owFIAL9zoiWx/9MRmK0JQGtN9LPg/eXJojn5c9aSrAFPfrh6bNBAVCIHTnCQic5dtTddQ=="
        ;
        String session="vV/C0TnQPfQX5Dul0sbJ1A==";
        String iv="VjdzmfgaJsnB+Xsyo8+42A==";
        JSONObject jo=aes.domain(enc,session,iv);
        us.setNickName(jo.getString("nickName"));
        System.out.println(jo);

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
        //map.put("id", "meepo");
        //map.put("openid", "password");
        //redisUtil.hmset("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsYXN0TG9naW4iOjE1ODA2MjI1MzMsInJvbGUiOiJ1c2VyIiwib3BlbklkIjoib0xJNGQ1TnlRVFlhazg3UU1TRXU4eDZPd1NsWSIsImV4cCI6MTU4MDYyNDMzM30.XJIHtNOgoEMuGcKXGIAhDB3m7pQ-OIOh35bGGqK-81k", map);
        System.out.println(redisUtil.hget("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsYXN0TG9naW4iOjE1ODA2MjMzMDMsInJvbGUiOiJ1c2VyIiwib3BlbklkIjoib0xJNGQ1TnlRVFlhazg3UU1TRXU4eDZPd1NsWSIsImV4cCI6MTU4MDYyNTEwM30.3GUnBlpLpPe_vYJWEqRaKLGgE3adwkQfAyS5utA-cAo","id"));
    }

    @Test
    //Token加解密测试
    public void TokenTest(){
        TokenUtil tku=new TokenUtil();
        Token tk=new Token();

        String result=tku.creatToken("oLI4d5O1YqSf-qmiTkSryrgBLeeQ","user");

        System.out.println(result);

        tk=tku.getTokenData(result);

        System.out.println(tk.toString());
    }

    @Test
    //测试查询用户
    public void openIdTest(){
        //ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //UserService us = (UserService) ac.getBean("userService");
        //us.inserFeedback("nihao","wjdbc");

        //LoginService ls=(LoginService) ac.getBean("loginService");
        //ls.processUserdata("oLI4d5NyQTYak87QMSEu8x6OwSlY");
        //ls.getDataByRedis("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsYXN0TG9naW4iOjE1ODA2MjM4NDQsInJvbGUiOiJ1c2VyIiwib3BlbklkIjoib0xJNGQ1TnlRVFlhazg3UU1TRXU4eDZPd1NsWSIsImV4cCI6MTU4MDYyNTY0NH0.Fgr81juha0uEK0FwTjJtvTz6Jazdv5zdMI7nxQzmXlM","openid");

        //BookkeepingService bs =(BookkeepingService) ac.getBean("bookkeepingService");
        //bs.listExpendByType("otGE75JlF4-J14NvvI9E1B4_B7vw","2020-01%");
        //System.out.println(bs.listExpendTypeList("otGE75JlF4-J14NvvI9E1B4_B7vw","2020-02%","cart","bkMoney"));
    }

    @Test
    //时间测试
    public void timeTest() throws ParseException {
        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");
        Date date = sdf.parse("2019-09-10");

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        System.out.println(sdf1.format(date)+weekDays[w]);
    }

    @Test
    //字符串转码测试
    public void utf8Test() {
        String str = "寮�濮婽oken楠岃瘉";

        try{
        str = new String(str.getBytes("gbk"), "UTF-8");
        }catch (Exception e){

        }
        System.out.println(str);
    }

    @Test
    //双数据源测试(失败)
    public void doubleDb(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService_db2 us = (UserService_db2) ac.getBean("userService_db2");
        us.selAll();
        //设置数据源
//        //UserDao_db2.selAll();
    }
}
