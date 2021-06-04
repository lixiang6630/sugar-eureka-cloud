package com.component.mpush.autoconfigure;

import com.component.mpush.MpushSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云Mpush自动装配
 *
 * @author: LX
 */
@Configuration
@EnableConfigurationProperties(PushAliyunProperties.class)
@ConditionalOnClass({MpushSender.class})
public class PushAliyunAutoConfiguration {

    @Bean("mpushSender")
    public MpushSender MpushSender() {
        return new MpushSender();
    }
}
