package com.cloud.webcore.common;

import com.common.base.exception.SelfNoticeException;
import com.common.base.model.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常拦截
 *
 * @author LX
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常拦截
     */
    @ExceptionHandler(SelfNoticeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Resp<Void> paramsValidException(SelfNoticeException ex) {
        log.error("SelfNoticeException 自定义异常拦截 find error,errocode={},errormsg={}", ex.getCode(), ex.getMessage());
        return new Resp<>(ex.getCode(), ex.getMessage());
    }
    /**
     * 全局异常拦截
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Resp<Void> globalException(SelfNoticeException ex) {
        log.error("RuntimeException 全局异常拦截 find error,errocode={},errormsg={}", ex.getCode(), ex.getMessage());
        return new Resp<>(ex.getCode(), ex.getMessage());
    }
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Resp<Void> exceptionHandler( Exception e){
        log.error("未知异常！原因是:",e);
        return Resp.error(e.getMessage());
    }
}
