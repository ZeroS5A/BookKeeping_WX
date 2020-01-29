package com.BookKeeping.entity;


import java.util.Date;

public class Token {
    private Integer id;
    private String openId;
    private String role;
    private Date lastLogin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", role='" + role + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
