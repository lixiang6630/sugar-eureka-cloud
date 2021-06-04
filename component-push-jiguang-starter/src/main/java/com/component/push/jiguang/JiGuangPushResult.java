package com.component.push.jiguang;

/**
 * @author: LX
 */
public class JiGuangPushResult {

    private String msg;

    private int code;

    private Boolean success;

    public JiGuangPushResult() {
    }

    public JiGuangPushResult(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}
