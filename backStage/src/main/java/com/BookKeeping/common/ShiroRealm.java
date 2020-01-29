package com.BookKeeping.common;

import com.BookKeeping.entity.JwtToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * 自定义的验证方法
 */

public class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("处理权限");
        System.out.println("Realm处理授权");
        String token = (String) principals.getPrimaryPrincipal();
        System.out.println("realm授权获取token:"+token);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("user");
        return new SimpleAuthorizationInfo();
    }

    /**
     * 校验 验证token逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        System.out.println("开始");
        String jwtToken = (String) token.getCredentials();
        System.out.println(jwtToken);

        //坑在这里（否则验证不通过！）
        setCredentialsMatcher(credentialsMatcher());
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    private CredentialsMatcher credentialsMatcher() {
        return (token, info) -> true;
    }
}
