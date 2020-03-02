package com.BookKeeping.service.impl;

import com.BookKeeping.dao.BookkeepingDao;
import com.BookKeeping.dao.CardDataDao;
import com.BookKeeping.entity.Bookkeeping;
import com.BookKeeping.service.CardDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service("cardDataServer")
public class CardDataServerImpl implements CardDataService {
    @Autowired
    CardDataDao cardDataDao;
    @Autowired
    BookkeepingDao bookkeepingDao;

    @Override
    public Integer insertCardData(String openid){
        //获取两个表中最新的时间
        Date cardDate=cardDataDao.selTopTimeInCard(openid);
        Date expendDate=cardDataDao.selTopTimeInExpend(openid);
        System.out.println("expend"+expendDate+"\n"+"card"+cardDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String,Object>> list = null;
        //先判断是否为空
        if(expendDate!=null){
            //支出表和爬取表比新
            if(expendDate.before(cardDate)){
                //爬取表取出比支出表更新的数据
                 list = cardDataDao.listCardData(openid, expendDate);
                System.out.println("CardData-更新部分");
            }
            else {
                System.out.println("CardData-无需更新");
                return 0;
            }
        }
        else {
            try {
                list = cardDataDao.listCardData(openid,sdf.parse("1999-06-09 12:12:12") );
                System.out.println("CardData-更新全部");
            }catch (Exception e){
                System.out.println(e);
            }

        }


        //迭代
        Iterator<Map<String,Object>> iter =list.iterator();
        while (iter.hasNext()){
            //每条数据
            Map<String,Object> map = iter.next();
            Bookkeeping bookkeeping = new Bookkeeping();

            //设置是卡的数据
            bookkeeping.setIsCard(1);
            //泛型，字符串转时间
            bookkeeping.setBkDate((Date) map.get("expendTime"));
            bookkeeping.setUserId(map.get("userId_id").toString());
            //字符串转float
            bookkeeping.setBkMoney(Float.parseFloat(map.get("expendMoney").toString())*Float.parseFloat("-1"));
            //设置备注
            String bkType = map.get("shopName").toString();
            bookkeeping.setRemarkText(bkType);
            //设置图标正则匹配
            if (Pattern.matches(".*车.*",bkType)){
                bookkeeping.setBkType("taxi");
            }
            else if(Pattern.matches(".*食.*",bkType) || Pattern.matches(".*综合服务楼.*",bkType)){
                bookkeeping.setBkType("emoji");
            }
            else if(Pattern.matches(".*水.*",bkType)){
                bookkeeping.setBkType("pay");
            }else{
                bookkeeping.setBkType("more");
            }
            bookkeepingDao.insertExpend(bookkeeping);
            //System.out.println(bookkeeping);
        }
        return list.size();
    }

    @Override
    public Integer deleteCardData(String openid) {
        return cardDataDao.delateCardData(openid);
    }


}
