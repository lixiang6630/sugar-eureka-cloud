package com.component.push.jiguang.autoconfigure;

import com.component.push.jiguang.JiGuangPushHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 极光推送自动装配
 *
 * @author: LX
 */
@Configuration
@EnableConfigurationProperties(PushJiGuangProperties.class)
@ConditionalOnClass({JiGuangPushHelper.class})
public class PushJiGuangAutoConfiguration {

    @Bean("jiGuangPushHelper")
    public JiGuangPushHelper jiGuangPushHelper() {
        return new JiGuangPushHelper();
    }
}
