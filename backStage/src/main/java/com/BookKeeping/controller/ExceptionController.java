package com.BookKeeping.controller;

import com.BookKeeping.common.Result;
import com.BookKeeping.common.ResultStatus;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
*权限异常处理，控制器都应该继承
*/

@Controller
@RequestMapping(value = "/Error")
public class ExceptionController {
    //拒绝请求
    @RequestMapping(value = "/notoken",method = RequestMethod.GET)
    @ResponseBody
    public Result notoken(){
        Result rs=new Result();
        System.out.println("Token无效");
        rs.setResult(ResultStatus.NOTOKEN);
        return rs;
    }

    //无权时的异常处理
    @ExceptionHandler({UnauthorizedException.class})
    @RequestMapping(value = "/unauthorized",method = RequestMethod.GET)
    @ResponseBody
    public Result unauthorized(){
        Result rs=new Result();
        System.out.println("无权访问");
        rs.setResult(ResultStatus.ROLEERR);
        return rs;
    }

}
