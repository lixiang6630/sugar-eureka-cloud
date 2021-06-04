package com.component.sms.ihuyi;

public enum SmsResultTypeEnum {

    OK(2, "提交成功"),
    非法IP访问(400, "非法 ip 访问"),
    手机号码不能为空(403, "手机号码不能为空"),
    手机号码已被列入黑名单(4030, "手机号码已被列入黑名单"),
    账号被冻结(4050, "账号被冻结"),
    剩余条数不足(4051, "剩余条数不足"),
    手机号码格式不正确(406, "手机号码格式不正确"),
    您的账户疑被恶意利用(408, "发送超限（[20]条）,已加入黑名单,可登入平台解除"),
    同一手机号码同一秒钟之内发送频率不能超过一条(4080, "同一手机号码同一秒钟之内发送频率不能超过一条"),
    超出同一手机号一天之内短信限制(4082, "超出同一手机号一天之内短信限制"),
    同一手机号验证码发出超出平台限制(4085, "同一手机号验证码发出超出平台限制");

    private int code;

    private String sign;

    SmsResultTypeEnum(int code, String sign) {
        this.code = code;
        this.sign = sign;
    }

    public int getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    public static SmsResultTypeEnum getByCode(int code) {
        for (SmsResultTypeEnum smsResultTypeEnum : values()) {
            if (code == smsResultTypeEnum.getCode()) {
                return smsResultTypeEnum;
            }
        }
        return null;
    }
}
