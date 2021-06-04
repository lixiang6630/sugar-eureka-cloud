package com.cloud.webcore.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class FeignHeadersInterceptor implements RequestInterceptor {

    public static String ACCESS_FROM_HEARDER = "ACCESS_FROM";

    public static String FEIGN_ACCESS_VALUE = "inner_feign";

    private static Logger logger = LoggerFactory.getLogger(FeignHeadersInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        template.header(ACCESS_FROM_HEARDER, FEIGN_ACCESS_VALUE);
        HttpServletRequest request = this.getHttpServletRequest();

        if (Objects.isNull(request)) {
            return;
        }

        Map<String, String> headers = this.getHeaders(request);
        if (headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                // 把请求过来的header请求头 原样设置到feign请求头中
                // 包括token
                template.header(entry.getKey(), entry.getValue());
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {

        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enums = request.getHeaderNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}

