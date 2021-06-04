package com.component.sms.yuntongxun;

//不全，错误码太多了，详见官网
public enum SmsResultTypeEnum {

    OK("000000", "提交成功"),
    未知错误("111000", "未知错误"),
    请求URL账号格式不正确("111100", "请求URL账号格式不正确"),
    请求包头Authorization参数为空("111101", "请求包头Authorization参数为空"),
    请求地址Sig参数为空("111108", "请求地址Sig参数为空"),
    请求地址Sig校验失败("111109", "请求地址Sig校验失败"),
    超过规定的并发数("111111", "超过规定的并发数"),
    IP地址不在白名单中("111115", "IP地址不在白名单中"),
    主账户ID为空("111128", "主账户ID为空"),
    主账户余额不足("111142", "主账户余额不足"),
    应用不存在("111181", "应用不存在"),
    应用未上线("111212", "应用未上线"),
    短信发送失败("160050 ", "短信发送失败"),
    短信模板无效("160032 ", "短信模板无效"),
    参数解析失败("160031 ", "参数解析失败"),
    短信内容长度限制("160037 ", "短信内容长度限制"),
    短信内容含敏感词("160049 ", "短信内容含敏感词"),
    短信模板ID要求为数字("160057 ", "短信模板ID要求为数字"),
    短信发送失败1("160063 ", "短信发送失败"),
    短信发送失败2("160064 ", "短信发送失败");

    private String code;

    private String sign;

    SmsResultTypeEnum(String code, String sign) {
        this.code = code;
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    public static SmsResultTypeEnum getByCode(String code) {
        if(code==null || "".equals(code)) {
            return null;
        }
        for (SmsResultTypeEnum smsResultTypeEnum : values()) {
            if (code.equalsIgnoreCase(smsResultTypeEnum.getCode())) {
                return smsResultTypeEnum;
            }
        }
        return null;
    }
}
