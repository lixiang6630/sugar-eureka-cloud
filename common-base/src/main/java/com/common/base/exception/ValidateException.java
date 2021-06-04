package com.common.base.exception;


/**
 * @description: 参数验证失败异常
 * @author: LX
 */
public class ValidateException extends RuntimeException{

    private Integer code;
    private String message;

    public ValidateException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMsg();
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
