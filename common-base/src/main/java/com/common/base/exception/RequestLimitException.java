package com.common.base.exception;

/**
 * @author: LX
 */
public class RequestLimitException extends RuntimeException {
    private Integer code;

    private String message;

    public RequestLimitException(String message) {
        this.code = 10000;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
