package com.BookKeeping.entity;

import java.io.Serializable;
import java.util.Date;

public class user implements Serializable {
    private int id;
    private String avatarUrl;
    private String nickName;
    private String openId;
    private int gender;

    public int getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", openId='" + openId + '\'' +
                ", gender=" + gender +
                '}';
    }
}
