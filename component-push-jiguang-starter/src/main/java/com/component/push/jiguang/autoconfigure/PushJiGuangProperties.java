package com.component.push.jiguang.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 极光推送
 *
 * @author: LX
 */
@ConfigurationProperties(prefix = "push.jiguang")
public class PushJiGuangProperties {

    /**
     * 秘钥
     */
    private String secret = "";

    /**
     * key
     */
    private String key = "";

    /**
     * 推送模式 true 正式环境 false 测试环境
     */
    private Boolean model = false;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getModel() {
        return model;
    }

    public void setModel(Boolean model) {
        this.model = model;
    }
}
