package com.BookKeeping.service;

import com.BookKeeping.entity.User;
import com.alibaba.fastjson.JSONObject;

public interface LoginService {

    //用户首次登录请求
    User userFirstLogin(String EncryptedData, String code, String ivs);
}
