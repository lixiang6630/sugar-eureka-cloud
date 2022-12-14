package com.cloud.webcore.config.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonConfig.class)
public class RedissonAutoConfiguration {
    @Autowired
    RedissonConfig redissonConfig;

    /**
     * @description: 哨兵模式自动装配
     * @author: LX
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonConfig.getSentinelAddresses())
                .setMasterName(redissonConfig.getMasterName())
                .setTimeout(redissonConfig.getTimeout())
                .setMasterConnectionPoolSize(redissonConfig.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonConfig.getSlaveConnectionPoolSize());

        if (StringUtils.isNotBlank(redissonConfig.getPassword())) {
            serverConfig.setPassword(redissonConfig.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * @description: 单机模式自动装配
     * @author: LX
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonConfig.getAddress())
                .setTimeout(redissonConfig.getTimeout())
                .setConnectionPoolSize(redissonConfig.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonConfig.getConnectionMinimumIdleSize());

        if (StringUtils.isNotBlank(redissonConfig.getPassword())) {
            serverConfig.setPassword(redissonConfig.getPassword());
        }

        return Redisson.create(config);
    }
}
