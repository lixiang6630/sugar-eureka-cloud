package com.cloud.webcore.annotation;

import java.lang.annotation.*;

/**
 * 接口单位时间内访问次数
 *
 * @example @ApiRequestLimit({roleID1,roleID2})
 * @example @ApiRequestLimit
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiRequestLimit {

    int expireTime() default 10;//秒

    String apiMethod() default "";//限制的接口

}
