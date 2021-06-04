package com.component.sms.ihuyi.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 互相无线短信
 *
 * @author LX
 */
@ConfigurationProperties(prefix = "sms.ihuyi")
public class SmsIhuyiProperties {

    /**
     * 互相无线服务url
     */
    private String url = "";

    /**
     * 账号
     */
    private String account = "";

    /**
     * 密码
     */
    private String password = "";

    /**
     * 模板 验证码参数位用{}代替
     */
    private String content = "验证码：{}，请您在10分钟内填写。如非本人操作，请忽略本短信。";

    /**
     * 短信签名 多签名模式下使用 无签名使用默认签名
     */
    private String sign = "";

    /**
     * 验证码长度 默认4位
     */
    private Integer length = 4;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}