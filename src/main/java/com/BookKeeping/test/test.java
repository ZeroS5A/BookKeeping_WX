package com.BookKeeping.test;

import com.BookKeeping.service.userService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        userService us = (userService) ac.getBean("user");
        us.selAll();
    }
}
