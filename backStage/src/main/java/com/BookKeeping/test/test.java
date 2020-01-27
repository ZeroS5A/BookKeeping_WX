package com.BookKeeping.test;

import com.BookKeeping.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService us = (UserService) ac.getBean("user");
        us.selAll();
    }
}
