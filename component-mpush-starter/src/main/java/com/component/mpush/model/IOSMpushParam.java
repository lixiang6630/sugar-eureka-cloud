package com.component.mpush.model;

public class IOSMpushParam {
    private String iosApnsEnv;
    private String appKey;
    private String toUserId;
    private String content;
    private String title;
    private String iOSExtParameters;
    private String music;

    public String getiOSExtParameters() {
        return iOSExtParameters;
    }

    public void setiOSExtParameters(String iOSExtParameters) {
        this.iOSExtParameters = iOSExtParameters;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getIosApnsEnv() {
        return iosApnsEnv;
    }

    public void setIosApnsEnv(String iosApnsEnv) {
        this.iosApnsEnv = iosApnsEnv;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
