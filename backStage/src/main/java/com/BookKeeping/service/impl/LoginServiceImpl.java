package com.BookKeeping.service.impl;

import com.BookKeeping.common.Aes;
import com.BookKeeping.common.HttpsRequest;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public User getUserData(String EncryptedData,String session,String ivs) {
        Aes aes=new Aes();
        User us=new User();

        //使用session_key获取用户数据
        JSONObject userData=aes.domain(EncryptedData,session,ivs);
        us.setAvatarUrl(userData.getString("avatarUrl"));
        us.setGender(userData.getInteger("gender"));
        us.setNickName(userData.getString("nickName"));
        us.setOpenId(userData.getString("openId"));
        return us;
    }

    @Override
    public String getSession(String code) {
        HttpUtil hrs=new HttpUtil();

        //获取session_key
        JSONObject session_key=hrs.domain("getSession_key",code);
        String session=session_key.getString("session_key");
        String openid=session_key.getString("openid");
        if(session!=null){
            //登录信息写入redis，有效期30分钟
            redisUtil.set(session, openid, 1800);
            return session;
        }else{
            System.out.println("无法获得session_key");
            System.out.println(session_key);
            return "NOT_SESSION";
        }
    }

    @Override
    public String getOpenId(String session) {
        //从redis中找openid
        if(redisUtil.get(session)!=null){
            return redisUtil.get(session).toString();
        }else{
            System.out.println("session失效");
            return "NOT_OPENID";
        }
    }


}
