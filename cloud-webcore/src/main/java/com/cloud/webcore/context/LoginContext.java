package com.cloud.webcore.context;

import com.cloud.webcore.model.SessionUser;
import com.cloud.webcore.model.TokenInfo;
import com.cloud.webcore.util.TokenUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class LoginContext {

    public static SessionUser getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object obj =request.getAttribute("issuer");
        if(obj==null) {
            TokenInfo tokenInfo= TokenUtil.tipTokenInfo(request);
            if(tokenInfo==null || !tokenInfo.hasCorrectToken() ||tokenInfo.isExpired() ) {
                return null;
            }
            SessionUser sessionUser = new SessionUser(tokenInfo.getUserId(),tokenInfo.getPhone());
            return sessionUser;
        }
        SessionUser sessionUser = (SessionUser)obj;
        return sessionUser;
    }
}
