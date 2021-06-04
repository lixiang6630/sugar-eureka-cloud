package com.component.sms.yuntongxun;

public class YumSmsInfo {

    private String to;

    private String appId;

    private String templateId;

    private String [] datas;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String[] getDatas() {
        return datas;
    }

    public void setDatas(String[] datas) {
        this.datas = datas;
    }

}
