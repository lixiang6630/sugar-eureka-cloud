package com.component.mpush.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云推送
 *
 * @author: LX
 */
@ConfigurationProperties(prefix = "push.aliyun")
public class PushAliyunProperties {

    /**
     * 秘钥
     */
    private String accessKeyId = "";

    /**
     * key
     */
    private String accessKeySecret = "";

    private String iosAppKey = "";

    private String andAppKey = "";

    private String androidPopupActivity = "";
    /**
     * MESSAGE：表示消息，
     * NOTICE：表示通知
     */
    private String pushType = "";
    /**
     * DEV：表示开发环境
     * PRODUCT：表示生产环境
     */
    private String iosApnsEnv = "";

    private String target = "";

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getIosAppKey() {
        return iosAppKey;
    }

    public void setIosAppKey(String iosAppKey) {
        this.iosAppKey = iosAppKey;
    }

    public String getAndAppKey() {
        return andAppKey;
    }

    public void setAndAppKey(String andAppKey) {
        this.andAppKey = andAppKey;
    }

    public String getAndroidPopupActivity() {
        return androidPopupActivity;
    }

    public void setAndroidPopupActivity(String androidPopupActivity) {
        this.androidPopupActivity = androidPopupActivity;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getIosApnsEnv() {
        return iosApnsEnv;
    }

    public void setIosApnsEnv(String iosApnsEnv) {
        this.iosApnsEnv = iosApnsEnv;
    }
}
