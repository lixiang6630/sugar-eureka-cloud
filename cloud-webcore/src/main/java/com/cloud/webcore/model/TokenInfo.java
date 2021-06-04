package com.cloud.webcore.model;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class TokenInfo {

    private String token;

    private Long userId;

    private String phone;

    private Date expiredDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean hasCorrectToken() {
        boolean rt = StringUtils.isNotBlank(token) && userId!=null && expiredDate!=null;
        return rt;
    }

    public boolean isExpired() {
        if(expiredDate==null) {
            return true;
        }
        Date now = new Date();
        //验证token是否过期
        if (expiredDate.before(now)) {
            return true;
        }
        return false;
    }
}
