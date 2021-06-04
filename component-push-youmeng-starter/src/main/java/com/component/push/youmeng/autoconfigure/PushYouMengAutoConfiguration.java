package com.component.sms.yuntongxun.autoconfigure;

import com.component.sms.yuntongxun.SmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PushYouMengProperties.class)
@ConditionalOnClass({SmsSender.class})
public class SmsYuntongxunAutoConfiguration {

    @Bean
    public SmsSender smsSender() {
        return new SmsSender();
    }

}
