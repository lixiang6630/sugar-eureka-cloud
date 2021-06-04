package com.component.sms.ihuyi;

public class SmsResult {

    //手机号
    private String mobile;

    //验证码
    private String mobileCode;

    //发送结果 0--成功  1--失败 2--发送异常
    private Integer state;

    //发送失败原因或异常原因
    private String reason;

    //发送短信回执的smsId
    private String smsId;

    //发送短信回执的msg
    private String msg;

    //发送短信回执的code, code=2表示发送成功，此时state=0
    private String code;

    public SmsResult() {

    }

    public SmsResult(String mobile,String mobileCode) {
        this.mobile=mobile;
        this.mobileCode=mobileCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public  boolean isSuccess() {

        return state!=null && state==0;
    }

    @Override
    public String toString() {
        return "SmsResult{" +
                "mobile='" + mobile + '\'' +
                ", mobileCode='" + mobileCode + '\'' +
                ", state=" + state +
                ", reason='" + reason + '\'' +
                ", smsId='" + smsId + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
