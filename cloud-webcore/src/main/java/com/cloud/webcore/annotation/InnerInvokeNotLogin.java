package com.cloud.webcore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要登录的接口但是内部调用不需要登录,使用此注解要注意的是,通过LoginContext获取到的user信息要判断空
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InnerInvokeNotLogin {
}
