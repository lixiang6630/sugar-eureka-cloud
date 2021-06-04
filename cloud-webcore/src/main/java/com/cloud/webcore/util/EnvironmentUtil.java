package com.cloud.webcore.util;

import org.springframework.core.env.Environment;

/**
 * 运行环境工具类
 *
 * @author: LX
 */
public class EnvironmentUtil {

    /**
     * 正式/测试环境
     */
    public static String prodEnv = "prod";
    public static String testEnv = "test";

    /**
     * 是否正式环境
     *
     * @return
     */
    public static boolean isProdEnv() {
        for (String activeProfile : getActiveProfiles()) {
            if (activeProfile.contains(prodEnv)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTest() {
        for (String activeProfile : getActiveProfiles()) {
            if (activeProfile.contains(testEnv)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getActiveProfiles() {
        Environment environment = getEnvironment();
        return environment.getActiveProfiles();
    }

    public static Environment getEnvironment() {
        return SpringContextHolder.getBean(Environment.class);
    }
}
