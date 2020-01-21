package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/api")
public class mainCon {
    //查询
    @RequestMapping(value = "/Sel", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public Result SelUsers() {
        Result result = new Result();
        result.setMessage("OK");
        return result;
    }
}
