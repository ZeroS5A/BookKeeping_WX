package com.BookKeeping.service.impl;

import com.BookKeeping.common.Aes;
import com.BookKeeping.dao.LoginDao;
import com.BookKeeping.dao.UserDao;
import com.BookKeeping.entity.Login;
import com.BookKeeping.entity.User;
import com.BookKeeping.service.LoginService;
import com.BookKeeping.util.HttpUtil;
import com.BookKeeping.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LoginDao loginDao;
    @Autowired
    UserDao userDao;

    Logger logger = LoggerFactory.getLogger(LoginService.class);

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
            return session;
        }else{
            System.out.println("无法获得session_key");
            System.out.println(session_key);
            return "NOT_SESSION";
        }
    }

    @Override
    public boolean setTokenToRedis(String token,String openid){
        try {
            //登录信息写入redis，有效期30分钟
            logger.info("开始将Token写入Redis");
            redisUtil.set(token, openid, 1800);
        }catch (Exception e){
            logger.info("Redis错误！请检查");
            return false;
        }
        return true;
    }

    @Override
    public String getDataByRedis(String token,String method) {
        //从redis中找openid
        String result = redisUtil.hget(token,method).toString();
        System.out.println(result);
        if(result!=null){
            return result;
        }else{

            System.out.println("Token失效");
            return "TokenError";
        }
    }

    @Override
    public User processUserdata(String openId, Login login) {
        User us=loginDao.getByOpenId(openId);

        if(us!=null){
            System.out.println("用户存在");
            us=getUserData(login.getEncryptedData(),login.getCode(),login.getIv());
            System.out.println(us.toString());
            //更新用户信息
            userDao.updateUser(us);
        }else {
            System.out.println("用户不存在");
            us=getUserData(login.getEncryptedData(),login.getCode(),login.getIv());
            System.out.println(us.toString());
            //写入数据库
            userDao.insertUser(us);
        }
        return loginDao.getByOpenId(openId);
    }
}
