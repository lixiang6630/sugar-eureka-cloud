package com.component.sms.yuntongxun.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "push.youmeng")
public class PushYouMengProperties {

    //IOSAppKey
    private String IOSAppKey = "";

    //IOSSecret
    private String IOSSecret = "";
    //androidAppKey
    private String androidAppKey = "";

    //androidSecret
    private String androidSecret = "";

    public PushYouMengProperties() {
    }

    public PushYouMengProperties(String IOSAppKey, String IOSSecret, String androidAppKey, String androidSecret) {
        this.IOSAppKey = IOSAppKey;
        this.IOSSecret = IOSSecret;
        this.androidAppKey = androidAppKey;
        this.androidSecret = androidSecret;
    }

    public String getIOSAppKey() {
        return IOSAppKey;
    }

    public void setIOSAppKey(String IOSAppKey) {
        this.IOSAppKey = IOSAppKey;
    }

    public String getIOSSecret() {
        return IOSSecret;
    }

    public void setIOSSecret(String IOSSecret) {
        this.IOSSecret = IOSSecret;
    }

    public String getAndroidAppKey() {
        return androidAppKey;
    }

    public void setAndroidAppKey(String androidAppKey) {
        this.androidAppKey = androidAppKey;
    }

    public String getAndroidSecret() {
        return androidSecret;
    }

    public void setAndroidSecret(String androidSecret) {
        this.androidSecret = androidSecret;
    }
}