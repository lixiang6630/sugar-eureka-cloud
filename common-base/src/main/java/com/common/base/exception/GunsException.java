package com.common.base.exception;

/**
 * 封装guns的异常
 */
public class GunsException extends RuntimeException {

    private Integer code;

    private String message;

    public GunsException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMsg();
    }
    
    public GunsException(Integer code, String message) {
    	this.code = code;
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
