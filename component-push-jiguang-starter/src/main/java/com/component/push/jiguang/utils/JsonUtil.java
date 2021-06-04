package com.component.push.jiguang.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json 工具类
 *
 * @author: LX
 */
public class JsonUtil {

    /**
     * 对象转JSON
     *
     * @param object
     * @return
     */
    public static String entityToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json转对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T jsonToEntity(String json, Class<T> type) {
        return JSONObject.parseObject(json, type);
    }
}
