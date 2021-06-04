package com.cloud.webcore.common;

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
 * 参数验证拦截器 拦截参数验证
 *
 * @author LX
 */
@Slf4j
@Order(0)
@ControllerAdvice
public class ParametersExceptionHandler {

    /**
     * 参数验证错误拦截
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Resp<Void> paramsValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        log.info("ParametersExceptionHandler 参数异常拦截器 find error,field={},errormsg={}", fieldError.getField(), fieldError.getDefaultMessage());
        return Resp.error(fieldError.getDefaultMessage());
    }
}
