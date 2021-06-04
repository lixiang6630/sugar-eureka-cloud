package com.cloud.webcore.interceptor;

import com.cloud.webcore.annotation.InnerInvoke;
import com.cloud.webcore.annotation.InnerInvokeNotLogin;
import com.cloud.webcore.annotation.NeedLogin;
import com.cloud.webcore.config.FeignHeadersInterceptor;
import com.cloud.webcore.config.JwtProperties;
import com.cloud.webcore.feign.PartnerLoginService;
import com.cloud.webcore.model.SessionUser;
import com.cloud.webcore.model.TokenInfo;
import com.cloud.webcore.service.BaseLoginService;
import com.cloud.webcore.util.JwtTokenUtil;
import com.cloud.webcore.util.RedisUtil;
import com.cloud.webcore.util.SpringContextHolder;
import com.cloud.webcore.util.TokenUtil;
import com.common.base.model.Req;
import com.common.base.model.Resp;
import com.common.base.util.DateUtil;
import com.common.base.util.JsonUtils;
import com.common.base.util.RenderUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AppLoginInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AppLoginInterceptor.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisUtil redisUtil;

    private BaseLoginService baseLoginService = null;

    private boolean loadBaseLogin = false;

    private PartnerLoginService partnerLoginService;

    public BaseLoginService getBaseLoginService() {
        if (loadBaseLogin) {
            return baseLoginService;
        }
        try {
            baseLoginService = SpringContextHolder.getBean(BaseLoginService.class);
        } catch (Exception e) {
            logger.info("AppLoginInterceptor getBaseLoginService getBean BaseLoginService fail,不存在BaseLoginService的bean");
        }
        loadBaseLogin = true;
        return baseLoginService;
    }

    public PartnerLoginService getPartnerLoginService() {
        if (partnerLoginService != null) {
            return partnerLoginService;
        }
        try {
            partnerLoginService = SpringContextHolder.getBean(PartnerLoginService.class);
        } catch (Exception e) {
            logger.info("AppLoginInterceptor getPartnerLoginService getBean PartnerLoginService fail,不存在PartnerLoginService的bean");
        }
        return partnerLoginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqUri = request.getRequestURI();
        logger.info("AppLoginInterceptor preHandle app登录校验开始,reqUri={}", reqUri);

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //是否内部调用
        boolean comefromInnerFeign = isFromInnerFeign(request);
        InnerInvokeNotLogin innerInvokeNotLogin = method.getAnnotation(InnerInvokeNotLogin.class);
        if (innerInvokeNotLogin != null && comefromInnerFeign) {
            //内部调用并且不需要登录
            return true;
        }
        InnerInvoke innerInvoke = method.getAnnotation(InnerInvoke.class);
        if (innerInvoke != null && !comefromInnerFeign) {
            logger.warn("AppLoginInterceptor preHandle 内部接口非内部调用,reqUri={},methodName={}", reqUri, method.getName());
            RenderUtil.renderJson(response, JsonUtils.beanToJson(Resp.error("非法调用")));
            return false;
        }

        NeedLogin appLogin = method.getAnnotation(NeedLogin.class);
        if (appLogin == null) {
            //不需要登录验证
            return true;
        }
        TokenInfo tokenInfo = TokenUtil.tipTokenInfo(request);
        if (tokenInfo == null || !tokenInfo.hasCorrectToken()) {
            logger.warn("AppLoginInterceptor preHandle Authorization header is null or not startsWith Bearer ");
            RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login expiration")));
            return false;
        }
        if (tokenInfo.isExpired()) {
            logger.warn("AppLoginInterceptor preHandle Authorization token is expired");
            RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login expiration")));
            return false;
        }

        Long userId = tokenInfo.getUserId();
        String token = tokenInfo.getToken();

        BaseLoginService loginService = this.getBaseLoginService();
        if (loginService != null) {
            //自身就是用户服务
            logger.info("AppLoginInterceptor preHandle 自身用户服务登录验证..............");
            boolean checkResult = loginService.checkLoginByToken(userId, token);
            if (!checkResult) {
                logger.warn("AppLoginInterceptor preHandle checkLoginByToken not pass");
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login expiration")));
                return false;
            }
            long diff = DateUtil.getDayDiff(tokenInfo.getExpiredDate(), new Date());
            //过期时间不到2天，重新生成下token
            if (diff <= 1) {
                if (comefromInnerFeign) {
                    //来自内部feign调用,token有效期快到了，延期token
                    loginService.deferToken(userId, token);
                } else {
                    //刷新新token
                    String newToken = loginService.refreshToken(userId);
                    if (!StringUtils.isBlank(newToken)) {
                        response.setHeader(jwtTokenUtil.getTokenKey(), newToken);
                    }
                }
            }
        } else {
            //第三方服务调用用户服务登录验证
            logger.info("AppLoginInterceptor preHandle 第三方服务调用用户服务登录验证..............");
            PartnerLoginService partnerLoginService = this.getPartnerLoginService();
            if (partnerLoginService == null) {
                logger.warn("AppLoginInterceptor preHandle partnerLoginService is null ");
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login authentication failed")));
                return false;
            }
            try {
                Req<Void> req = new Req<Void>();
                Resp<Void> resp = partnerLoginService.partnerCheckLogin(req);
                if (!resp.isSuccess()) {
                    RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login authentication failed")));
                    return false;
                }
            } catch (Exception e) {
                logger.warn("AppLoginInterceptor preHandle invoke partnerLoginService error,", e);
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login authentication failed")));
                return false;
            }
        }
        //验证用户是否被冻结
        Boolean aBoolean = redisUtil.hasKey("freeze_user_id_key:" + userId);
        if(aBoolean){ //正在冻结中
            RenderUtil.renderJson(response, JsonUtils.beanToJson(Resp.error("User account is frozen")));
            return false;
        }
        SessionUser issuer = new SessionUser(userId, tokenInfo.getPhone());
        request.setAttribute("issuer", issuer);
        return true;
    }

    //是否来自内部feign调用
    public boolean isFromInnerFeign(HttpServletRequest request) {
        String accessFrom = request.getHeader(FeignHeadersInterceptor.ACCESS_FROM_HEARDER);
        if (StringUtils.isNotBlank(accessFrom) && FeignHeadersInterceptor.FEIGN_ACCESS_VALUE.equals(accessFrom)) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}