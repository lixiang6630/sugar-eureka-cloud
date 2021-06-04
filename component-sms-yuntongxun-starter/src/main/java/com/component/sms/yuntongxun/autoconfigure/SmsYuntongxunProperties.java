package com.component.sms.yuntongxun.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms.yuntongxun")
public class SmsYuntongxunProperties {

    //服务url
    private String url = "https://app.cloopen.com:8883";

    //账号sid
    private String accountSid = "";

    //授权token
    private String authToken = "";

    //应用appId
    private String appId = "";

    //连接超时时间，毫秒
    private String connectTimeout="";

    //请求超时时间，毫秒
    private String reqTimeout="";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getReqTimeout() {
        return reqTimeout;
    }

    public void setReqTimeout(String reqTimeout) {
        this.reqTimeout = reqTimeout;
    }
}