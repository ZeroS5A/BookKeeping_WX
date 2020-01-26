package com.BookKeeping.common;

public enum ResultStatus {
    SUCCESS(200, "success"),     //成功
    ERROR(4001, "Error"),       //其他失败
    NODATA(4002,"Database query failed"), //数据库查询失败
    DATAEXIST(4003,"no data"),//无数据
    DATAERR(4004,"data error"),//数据错误
    SESSIONERR(4101,"User not logged in"),//用户未登录
    LOGINERR(4102,"User login failed"),//用户登录失败
    PARAMERR(4103,"Parameter error"),//参数错误
    USERERR(4104,"User does not exist or is not activated"),//用户不存在或未激活
    ROLEERR(4105,"User identity error"),//用户身份错误
    PWDERR(4106,"wrong password"),//密码错误
    SMSERR(4017,"SMS failed"),//短信失败
    REQERR(4201,"Illegal request or request is limited"),//非法请求或请求次数受限
    IPERR(4202,"IP restricted"),//IP受限
    THIRDERR(4301,"Third-party system error"),//第三方系统错误
    IOERR(4302,"Third-party system error"),//第三方系统错误
    SERVERERR(4500,"internal error"),//内部错误
    UNKNOWERR(4501,"unknown mistake"),//未知错误
    SERVICE_EXCEPTION(5000, "service exception");

    private final int value;

    private final String reasonPhrase;

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
