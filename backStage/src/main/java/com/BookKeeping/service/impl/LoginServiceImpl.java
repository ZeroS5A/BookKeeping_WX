package com.BookKeeping.service.impl;

import com.BookKeeping.common.Aes;
import com.BookKeeping.common.HttpsRequest;
import com.BookKeeping.dao.UserDao;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {


    @Override
    public User userFirstLogin(String EncryptedData,String code,String ivs) {
        HttpsRequest hrs=new HttpsRequest();
        Aes aes=new Aes();
        User us=new User();

        //获取session_key
        JSONObject session_key=hrs.domain("getSession_key",code);
        //使用session_key获取用户数据
        if(session_key.getString("session_key")!=null){
            JSONObject userData=aes.domain(EncryptedData,session_key.getString("session_key"),ivs);
            us.setAvatarUrl(userData.getString("avatarUrl"));
            us.setGender(userData.getInteger("gender"));
            us.setNickName(userData.getString("nickName"));
            us.setOpenId(userData.getString("openId"));
        }else {
            System.out.println("获取不到session");
            System.out.println(session_key);
        }

        return us;
    }
}
