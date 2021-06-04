package com.component.push.jiguang.model;

/**
 * ios 通知设置
 *
 * @author: LX
 */
public class IosNotificationConf {

    /**
     * 是否有声音
     */
    private Boolean haveSound;

    public Boolean getHaveSound() {
        return haveSound;
    }

    public IosNotificationConf setHaveSound(Boolean haveSound) {
        this.haveSound = haveSound;
        return this;
    }

    @Override
    public String toString() {
        return "IosNotificationConf{" +
                "haveSound=" + haveSound +
                '}';
    }
}
