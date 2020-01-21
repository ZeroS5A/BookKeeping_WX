package com.BookKeeping.entity;

import java.io.Serializable;
import java.util.Date;

public class mian implements Serializable {
    private int id;
    private int bkType;
    private Date bkDate;
    private int bkMode;
    private float bkMoney;
    private String remarkText;
    private String remarkImage0;
    private String remarkImage1;
    private String remarkImage2;

    public int get_id(){return id;}
    public void set_id(int id){this.id=id;}

    public int get_bkType(){return bkType;}
    public void set_bkType(int bkType){this.bkType=bkType;}

    public Date get_bkDate(){return bkDate;}
    public void set_bkDate(Date bkType){this.bkDate=bkDate;}

    public int get_bkMode(){return bkMode;}
    public void set_bkMode(int bkMode){this.bkMode=bkMode;}

    public float get_bkMoney(){return bkMoney;}
    public void set_bkMoney(int bkMoney){this.bkMoney=bkMoney;}

    public String get_remakeText(){return remarkText;}
    public void set_remakeText(String remakeText){this.remarkText=remakeText;}

    public String get_remarkImage0(){return remarkImage0;}
    public void set_remarkImage0(String remarkImage0){this.remarkImage0=remarkImage0;}

    public String get_remarkImage1(){return remarkImage1;}
    public void set_remarkImage1(String remarkImage1){this.remarkImage1=remarkImage1;}

    public String get_remarkImage2(){return remarkImage2;}
    public void set_remarkImage2(String remarkImage2){this.remarkImage2=remarkImage2;}
}
