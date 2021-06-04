package com.cloud.webcore.service;

public interface BaseLoginService {

    //根据token检查用户登录状态
    public boolean checkLoginByToken(Long userId,String token);

    //延期token
    public boolean deferToken(Long userId,String token);

    //刷新用户token
    public String refreshToken(Long userId);
}
