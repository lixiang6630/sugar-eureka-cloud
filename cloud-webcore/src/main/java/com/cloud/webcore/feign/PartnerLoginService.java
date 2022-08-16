package com.cloud.webcore.feign;

import com.cloud.webcore.model.SessionUser;
import com.common.base.model.Req;
import com.common.base.model.Resp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "${application.userService:defaultUser}")
public interface PartnerLoginService {

    //根据token检查用户登录状态
    @PostMapping("/partnerCheckLogin")
    public Resp<Void> partnerCheckLogin(Req<Void> req);

    //根据token检查用户状态
    @PostMapping("/partnerCheckUserState")
    public Resp<Boolean> partnerCheckUserState(Req<Void> req);
}

