package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class userCon {

    @RequestMapping("/selAll")
    @ResponseBody
    public Result selAll(){
        Result rs= new Result();
        System.out.println("查询成功");
        rs.setMessage("success");
        return rs;
    }
}
