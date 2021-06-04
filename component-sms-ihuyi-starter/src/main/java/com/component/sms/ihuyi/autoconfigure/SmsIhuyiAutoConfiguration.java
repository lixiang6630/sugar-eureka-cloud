package com.component.sms.ihuyi.autoconfigure;

import com.component.sms.ihuyi.HuYiSmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsIhuyiProperties.class)
@ConditionalOnClass({HuYiSmsSender.class})
public class SmsIhuyiAutoConfiguration {

    @Bean("huYiSmsSender")
    public HuYiSmsSender smsSender() {
        return new HuYiSmsSender();
    }

}
