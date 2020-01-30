package com.BookKeeping.common;

import com.BookKeeping.entity.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT核心过滤器配置（重写shiro原生的方法）
 * 所有的请求都会先经过Filter，所以我们继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法。
 * 执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要进行 需要验证的操作
     * 检测header里面是否包含Authorization字段即可
     */
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        String auth = getAuthzHeader(request);
//        return auth != null && !auth.equals("");
//
//    }
    /**
     * 此方法调用登陆，验证逻辑
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("允许登录测试");
        JwtToken token = new JwtToken(getAuthzHeader(request));
        if(token.getToken()!=null){

        try {
            //交给Realm验证
            getSubject(request, response).login(token);
        }catch (AuthenticationException e){
            //返回的是false
            System.out.println("Token验证失败");
            return false;
        }
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("拒绝登录");
        //拒绝时跳转的页面
        redirectToLogin(request,response);
        return false;
    }

    /**
     * 提供跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            System.out.println("跨域操作");
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    //重写跳转方法
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, "/Error/notoken");
    }
}