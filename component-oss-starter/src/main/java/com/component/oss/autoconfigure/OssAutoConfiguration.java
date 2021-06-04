package com.component.oss.autoconfigure;

import com.component.oss.OssHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 自动装配
 *
 * @author: LX
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnClass({OssHelper.class})
public class OssAutoConfiguration {

    @Bean("ossHelper")
    public OssHelper ossHelper() {
        return new OssHelper();
    }
}
