package com.BookKeeping.service;

import com.BookKeeping.entity.User;
import com.alibaba.fastjson.JSONObject;

public interface LoginService {

    //用户首次登录请求
    User getUserData(String EncryptedData, String session, String ivs);

    String getSession(String code);

    String getOpenId(String token);

    boolean setTokenToRedis(String token,String openid);

    //按openid查询用户
    String processUserdata(String openId);
}
