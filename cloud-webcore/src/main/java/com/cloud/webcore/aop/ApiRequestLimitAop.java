package com.cloud.webcore.aop;

import com.cloud.webcore.annotation.ApiRequestLimit;
import com.cloud.webcore.config.redisson.RedissonUtil;
import com.cloud.webcore.util.MacUtil;
import com.cloud.webcore.util.RedisUtil;
import com.common.base.exception.GunsException;
import com.common.base.model.Resp;
import com.common.base.util.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class ApiRequestLimitAop {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonUtil redissonUtil;

    @Pointcut("@annotation(com.cloud.webcore.annotation.ApiRequestLimit)")
    public void apiRequestLimit(){

    }
    @Around("apiRequestLimit()")
    public Object doCheck(ProceedingJoinPoint point) throws Throwable{
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        ApiRequestLimit apiRequestLimit = method.getAnnotation(ApiRequestLimit.class);
        int expireTime = apiRequestLimit.expireTime();
        String apiMethod = apiRequestLimit.apiMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = MacUtil.getIpAddr(request);
        String api_limit_key = "api_limit_key:" + apiMethod + ipAddr;
        String lockKey = "api_request_limit_key:" + apiMethod + ipAddr;
        String errorMsg = "OPERATING TOO FREQUENTLY, PLEASE TRY AGAIN LATER";
        log.info("api请求限流验证 api_limit_key【{}】",api_limit_key);
        boolean locked = redissonUtil.lock(lockKey, 60);
        if (!locked) {
            return Resp.error(errorMsg);
        }
        try {
            String hasLimit = redisUtil.get(api_limit_key);
            if(StrKit.isEmpty(hasLimit)){
                redisUtil.setEx(api_limit_key,ipAddr,expireTime, TimeUnit.SECONDS);
                return point.proceed();
            }else {
                log.info("api请求限流 ipAddr【{}】expireTime【{}】操作太频繁 ",expireTime,ipAddr);
                return Resp.error(errorMsg);
            }
        } finally {
            redissonUtil.unlock(lockKey);
        }
    }
}
