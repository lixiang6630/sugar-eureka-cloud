package com.component.cos.autoconfigure;

import com.component.cos.CosHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CosProperties.class)
@ConditionalOnClass({CosHelper.class})
public class CosAutoConfiguration {

    @Bean
    public CosHelper cosHelper() {
        return new CosHelper();
    }

}
