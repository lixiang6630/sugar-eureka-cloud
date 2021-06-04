package com.component.push.jiguang.model;

/**
 * 安卓 通知设置
 *
 * @author: LX
 */
public class AndroidNotificationConf {

    /**
     * 通知栏样式类型 根据安卓自定义
     */
    private Integer style;


    public Integer getStyle() {
        return style;
    }

    public AndroidNotificationConf setStyle(Integer style) {
        this.style = style;
        return this;
    }
}
