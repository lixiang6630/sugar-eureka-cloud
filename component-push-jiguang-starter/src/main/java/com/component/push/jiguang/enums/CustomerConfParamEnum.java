package com.component.push.jiguang.enums;

/**
 * 自定义配置客户端参数名称
 *
 * @author: LX
 */
public enum CustomerConfParamEnum {

    AndroidNotificationConf("androidNotificationConf", "安卓通知设置"),
    IosNotificationConf("iosNotificationConf", "iOS通知设置");

    private String name;
    private String remark;

    CustomerConfParamEnum(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }
}
