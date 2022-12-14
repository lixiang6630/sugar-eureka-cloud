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
            logger.info("AppLoginInterceptor getBaseLoginService getBean BaseLoginService fail,?????????BaseLoginService???bean");
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
            logger.info("AppLoginInterceptor getPartnerLoginService getBean PartnerLoginService fail,?????????PartnerLoginService???bean");
        }
        return partnerLoginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqUri = request.getRequestURI();
        logger.info("AppLoginInterceptor preHandle app??????????????????,reqUri={}", reqUri);

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //??????????????????
        boolean comefromInnerFeign = isFromInnerFeign(request);
        InnerInvokeNotLogin innerInvokeNotLogin = method.getAnnotation(InnerInvokeNotLogin.class);
        if (innerInvokeNotLogin != null && comefromInnerFeign) {
            //?????????????????????????????????
            return true;
        }
        InnerInvoke innerInvoke = method.getAnnotation(InnerInvoke.class);
        if (innerInvoke != null && !comefromInnerFeign) {
            logger.warn("AppLoginInterceptor preHandle ???????????????????????????,reqUri={},methodName={}", reqUri, method.getName());
            RenderUtil.renderJson(response, JsonUtils.beanToJson(Resp.error("????????????")));
            return false;
        }

        NeedLogin appLogin = method.getAnnotation(NeedLogin.class);
        if (appLogin == null) {
            //?????????????????????
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
            //????????????????????????
            logger.info("AppLoginInterceptor preHandle ??????????????????????????????..............");
            boolean checkResult = loginService.checkLoginByToken(userId, token);
            if (!checkResult) {
                logger.warn("AppLoginInterceptor preHandle checkLoginByToken not pass");
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login expiration")));
                return false;
            }
            boolean checkUserStateByTokenResult = loginService.checkUserStateByToken(userId, token);
            logger.info("loginService ???????????????????????????{}???",checkUserStateByTokenResult);
            if (!checkUserStateByTokenResult) {
                logger.warn("AppLoginInterceptor preHandle checkUserStateByToken not pass");
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login expiration")));
                return false;
            }
            long diff = DateUtil.getDayDiff(tokenInfo.getExpiredDate(), new Date());
            //??????????????????2?????????????????????token
            if (diff <= 1) {
                if (comefromInnerFeign) {
                    //????????????feign??????,token???????????????????????????token
                    loginService.deferToken(userId, token);
                } else {
                    //?????????token
                    String newToken = loginService.refreshToken(userId);
                    if (!StringUtils.isBlank(newToken)) {
                        response.setHeader(jwtTokenUtil.getTokenKey(), newToken);
                    }
                }
            }
        } else {
            //?????????????????????????????????????????????
            logger.info("AppLoginInterceptor preHandle ?????????????????????????????????????????????..............");
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
                Boolean checkUserState = partnerLoginService.partnerCheckUserState(req).getData();
                logger.info("partnerLoginService ???????????????????????????{}???",checkUserState);
                if (!checkUserState) {
                    RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login authentication failed")));
                    return false;
                }
            } catch (Exception e) {
                logger.warn("AppLoginInterceptor preHandle invoke partnerLoginService error,", e);
                RenderUtil.renderJson(response, JsonUtils.beanToJson(new Resp<Void>(401, "User login authentication failed")));
                return false;
            }
        }
        //???????????????????????????
        Boolean aBoolean = redisUtil.hasKey("freeze_user_id_key:" + userId);
        if(aBoolean){ //???????????????
            RenderUtil.renderJson(response, JsonUtils.beanToJson(Resp.error("User account is frozen")));
            return false;
        }
        SessionUser issuer = new SessionUser(userId, tokenInfo.getPhone());
        request.setAttribute("issuer", issuer);
        return true;
    }

    //??????????????????feign??????
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