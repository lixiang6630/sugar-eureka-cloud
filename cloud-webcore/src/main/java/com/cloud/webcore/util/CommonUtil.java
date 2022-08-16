package com.cloud.webcore.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * rest 工具类
 *
 * @author: lx
 */
public class CommonUtil {

    /**
     * 对象 2 Json 2 byte 数组
     *
     * @param object
     * @return
     */
    public static byte[] objectToByteArray(Object object) {
        return JSON.toJSONString(object).getBytes();
    }

    /**
     * byte 数组 2 对象
     *
     * @param byteArray
     * @param clz
     * @return
     */
    public static Object byteArrayToObject(byte[] byteArray, Class clz) {
        return JSONObject.parseObject(new String(byteArray), clz);
    }

    /**
     * 获取封面宽高
     *
     * @param url
     * @return
     */
    public static int[] getWidthAndHeight(String url) {
        try {
            InputStream is = new URL(url).openStream();
            BufferedImage sourceImg = ImageIO.read(is);
            int width = sourceImg.getWidth();
            int height = sourceImg.getHeight();
            return new int[]{width, height};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{720, 1280};
    }
    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<T>> result = new ArrayList<List<T>>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, (i + 1) * len > size ? size : len * (i + 1));
            result.add(subList);
        }
        return result;
    }

    public static <T> List<T> getSubListByPage(Collection<T> inset, int page, int rows) {
        if(inset != null && inset.size() > 0 && page > 0) {
            int fromIndex = (page - 1) * rows;
            int toIndex = page * rows;
            if (fromIndex > inset.size()) {
                System.out.println("没了");
                return null;
            }
            if (toIndex > inset.size()) {
                int left = toIndex - inset.size();
                toIndex = fromIndex + (rows - left);
            }
            List<T> strings = new ArrayList<T>(inset);
            return strings.subList(fromIndex, toIndex);
        }else {
            return null;
        }
    }
}
