package com.component.push.jiguang.utils;

import com.component.push.jiguang.enums.CustomerConfParamEnum;
import com.component.push.jiguang.model.AndroidNotificationConf;
import com.component.push.jiguang.model.IosNotificationConf;

import java.util.Map;

/**
 * 配置填充工具类
 *
 * @author: LX
 */
public class JiGuangConfFillUtil {

    /**
     * 配置参数填充
     *
     * @param params
     * @param androidNotificationConf
     * @param iosNotificationConf
     */
    public static void fill(Map<String, String> params,
                            AndroidNotificationConf androidNotificationConf,
                            IosNotificationConf iosNotificationConf) {

        if (androidNotificationConf != null) {
            params.put(CustomerConfParamEnum.AndroidNotificationConf.getName(), JsonUtil.entityToJson(androidNotificationConf));
        }
        if (iosNotificationConf != null) {
            params.put(CustomerConfParamEnum.IosNotificationConf.getName(), JsonUtil.entityToJson(iosNotificationConf));
        }
    }
}
