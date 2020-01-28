package com.BookKeeping.entity;

import java.io.Serializable;
import java.util.Date;

public class Bookkeeping implements Serializable {

    private Integer id;
    private Integer userId;
    private String bkType;
    private Date bkDate;
    private Float bkMoney;
    private String remarkText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBkType() {
        return bkType;
    }

    public void setBkType(String bkType) {
        this.bkType = bkType;
    }

    public Date getBkDate() {
        return bkDate;
    }

    public void setBkDate(Date bkDate) {
        this.bkDate = bkDate;
    }

    public Float getBkMoney() {
        return bkMoney;
    }

    public void setBkMoney(Float bkMoney) {
        this.bkMoney = bkMoney;
    }

    public String getRemarkText() {
        return remarkText;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    @Override
    public String toString() {
        return "Bookkeeping{" +
                "id=" + id +
                ", userId=" + userId +
                ", bkType='" + bkType + '\'' +
                ", bkDate=" + bkDate +
                ", bkMoney=" + bkMoney +
                ", remarkText='" + remarkText + '\'' +
                '}';
    }

}
