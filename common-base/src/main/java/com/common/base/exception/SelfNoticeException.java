package com.common.base.exception;

/**
 * 自定义提示异常
 */
public class SelfNoticeException extends RuntimeException {
    private Integer code;

    public SelfNoticeException(String message) {
        super(message);
        this.code = 10000;
    }

    public SelfNoticeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
