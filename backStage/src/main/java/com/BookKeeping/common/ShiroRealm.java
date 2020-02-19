package com.BookKeeping.common;

        import com.BookKeeping.entity.JwtToken;
        import com.BookKeeping.entity.Token;
        import com.BookKeeping.util.TokenUtil;
        import com.auth0.jwt.exceptions.JWTDecodeException;
        import org.apache.shiro.authc.AuthenticationException;
        import org.apache.shiro.authc.AuthenticationInfo;
        import org.apache.shiro.authc.AuthenticationToken;
        import org.apache.shiro.authc.SimpleAuthenticationInfo;
        import org.apache.shiro.authc.credential.CredentialsMatcher;
        import org.apache.shiro.authz.AuthorizationInfo;
        import org.apache.shiro.authz.SimpleAuthorizationInfo;
        import org.apache.shiro.realm.AuthorizingRealm;
        import org.apache.shiro.subject.PrincipalCollection;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

/**
 * 自定义的验证方法
 */

public class ShiroRealm extends AuthorizingRealm {

    @Override
    //获得自己定义的token
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Override
    //授权方法，要在控制器层调用才会执行，验证方法在下面
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //这里PrincipalCollection对象存放的是SimpleAuthenticationInfo(jwtToken, role, getName())里的验证信息

        //System.out.println("Realm处理授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        TokenUtil tku=new TokenUtil();

        String token = (String) principals.getPrimaryPrincipal();

        //logger.info("realm授权获取token:"+token);
        System.out.println("Realm获取的token解密后：\n"+tku.getTokenData(token));
        //System.out.println("角色:"+tku.getTokenData(token).getRole());

        authorizationInfo.addRole(tku.getTokenData(token).getRole());
        return authorizationInfo;
    }

    /**
     * 校验 验证token逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        System.out.println("Realm-开始Token验证");
        String jwtToken = (String) token.getCredentials();
        System.out.println(jwtToken);
        try {
            TokenUtil tku=new TokenUtil();
            Token tk=tku.getTokenData(jwtToken);
            String role=tk.getRole();
            if(role!=null){
                //logger.info("用户有效");
            }else{
                //token解密失败时，返回filter
                throw new AuthenticationException("token is invalid , please check your token");
            }
        }catch (JWTDecodeException e){
            //token解密失败时，返回filter
            throw new AuthenticationException("token is invalid , please check your token");
        }

        //坑在这里（否则验证不通过！）
        setCredentialsMatcher(credentialsMatcher());
        return new SimpleAuthenticationInfo(jwtToken, jwtToken, getName());
    }

    /**
     * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    private CredentialsMatcher credentialsMatcher() {
        return (token, info) -> true;
    }
}
