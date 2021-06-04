package com.cloud.webcore.util;

import com.cloud.webcore.config.JwtProperties;
import com.cloud.webcore.model.TokenInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtil {

    private static Logger logger= LoggerFactory.getLogger(TokenUtil.class);

    //提取token
    public static TokenInfo tipTokenInfo(HttpServletRequest request) {
        JwtProperties jwtProperties= SpringContextHolder.getBean(JwtProperties.class);
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            return null;
        }
        String authToken = requestHeader.substring(7);
        TokenInfo tokenInfo= tipTokenInfo(authToken);
        return tokenInfo;
    }

    //提取token
    public static TokenInfo tipTokenInfo(String token) {
        TokenInfo tokenInfo= new TokenInfo();
        tokenInfo.setToken(token);
        try {
            JwtTokenUtil jwtTokenUtil = SpringContextHolder.getBean(JwtTokenUtil.class);
            Claims claims = jwtTokenUtil.getClaimFromToken(token);
            Date expiredDate = claims.getExpiration();
            Long userId = Long.valueOf(claims.getId());
            String phone = claims.getSubject();
            tokenInfo.setUserId(userId);
            tokenInfo.setPhone(phone);
            tokenInfo.setExpiredDate(expiredDate);
        }catch(Exception e) {
            logger.error("TokenUtil tipTokenInfo error,",e);
        }
        return tokenInfo;
    }
}
