package com.cloud.webcore.config.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedissonUtil {

    @Autowired
    RedissonClient redissonClient;


    /**
     * @description: 开启分布式锁,name[锁名字]  leaseTime[自动解锁时间(单位秒)]
     *
     * @author: LX
     */
    public boolean lock(String name, long leaseTime) {
        try {
            log.info("开启 [" + name + "]分布式锁.....");
            boolean rt =redissonClient.getLock(name).tryLock(leaseTime, TimeUnit.SECONDS);
            return rt;
        } catch (Exception e) {
            log.error("分布式锁 [" + name + "] lock error", e);
            return false;
        }
    }

    /**
     * @description: 锁名称;
     *
     * @author: LX
     */
    public void unlock(String name) {
        try {
            log.info("解锁 [" + name + "] 分布式锁.....");
            redissonClient.getLock(name).unlock();
        } catch (Exception e) {
            log.error("分布式锁 [" + name + "] unlock error", e);
        }
    }
}
