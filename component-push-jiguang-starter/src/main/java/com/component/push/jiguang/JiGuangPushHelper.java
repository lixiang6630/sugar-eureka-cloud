package com.component.push.jiguang;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.component.push.jiguang.autoconfigure.PushJiGuangProperties;
import com.component.push.jiguang.enums.CustomerConfParamEnum;
import com.component.push.jiguang.model.AndroidNotificationConf;
import com.component.push.jiguang.model.IosNotificationConf;
import com.component.push.jiguang.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光推送
 *
 * @author: LX
 */
public class JiGuangPushHelper {

    private static final Logger logger = LoggerFactory.getLogger(JiGuangPushHelper.class);

    private String JG_SECRET;

    private String JG_KEY;

    private boolean MODEL;

    @Autowired
    private PushJiGuangProperties pushJiGuangProperties;

    @PostConstruct
    public void init() {
        this.JG_SECRET = this.pushJiGuangProperties.getSecret();
        this.JG_KEY = this.pushJiGuangProperties.getKey();
        this.MODEL = this.pushJiGuangProperties.getModel();
    }

    /**
     * 推送所有用户->无条件
     *
     * @param msg
     * @return
     */
    public boolean pushMsgAllWithouCondition(String msg) {
        try {
            PushResult pushResult = getJPushClient().sendPush(PushPayload.alertAll(msg));
            logger.info("极光推送...推送所有用户...推送结果={}", pushResult);
            return true;
        } catch (Exception e) {
            logger.error("极光推送...推送所有用户...推送异常", e);
            return false;
        }
    }

    /**
     * 推送所有用户
     *
     * @param msg
     * @param extra
     * @return
     */
    public boolean pushMsgAllUser(String msg, Map<String, String> extra) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAll(msg, extra);
        return pushToJG(client, payload);
    }

    private boolean pushToJG(JPushClient client, PushPayload payload) {
        try {
            PushResult pushResult = client.sendPush(payload);
            logger.info("极光推送...推送结果={}", pushResult);
            return true;
        } catch (Exception e) {
            logger.error("极光推送...推送异常", e);
            return false;
        }
    }

    private String pushToJG(String time, String scheduleName, JPushClient client, PushPayload payload) {
        try {
            ScheduleResult result = client.createSingleSchedule(scheduleName, time, payload);
            logger.info("极光推送...定时推送...推送结果={}", result);
            return result.getSchedule_id();
        } catch (Exception e) {
            logger.info("极光推送...定时推送...推送异常", e);
            return null;
        }
    }


    /**
     * 推送指定用户
     *
     * @param msg
     * @param extraMsg
     * @param userIdList
     * @return
     */
    public boolean pushMsgAssignUser(String msg, Map<String, String> extraMsg, List<String> userIdList) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAssignUser(msg, extraMsg, userIdList);
        return pushToJG(client, payload);
    }

    /**
     * 推送指定用户
     *
     * @param msg
     * @param extraMsg
     * @param userId
     * @return
     */
    public boolean pushMsgAssignUser(String msg, Map<String, String> extraMsg, String... userId) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAssignUser(msg, extraMsg, userId);
        return pushToJG(client, payload);
    }

    /**
     * 推送指定标签
     *
     * @param msg
     * @param extraMsg
     * @param tagName
     * @return
     */
    public boolean pushMsgAssignTag(String msg, Map<String, String> extraMsg, String tagName) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAssignTag(msg, extraMsg, tagName);
        return pushToJG(client, payload);
    }


    /**
     * 定时推送到所有用户: time:推送时间  scheduleName:调度器名字  msg:推送消息
     *
     * @param time
     * @param scheduleName
     * @param msg
     * @return
     */
    public String schedulePushMsgAllUser(String time, String scheduleName, String msg) {
        JPushClient client = getJPushClient();
        PushPayload payload = PushPayload.alertAll(msg);
        try {
            ScheduleResult result = client.createSingleSchedule(scheduleName, time, payload);
            String scheduleId = result.getSchedule_id();
            logger.info("极光推送...定时推送...推送结果={}", result);
            return scheduleId;
        } catch (Exception e) {
            logger.info("极光推送...定时推送...推送异常", e);
            return null;
        }
    }


    /**
     * 定时推送到所有用户: time:推送时间  scheduleName:调度器名字  msg:推送消息  extraMsg:业务参数
     *
     * @param time
     * @param scheduleName
     * @param msg
     * @param extraMsg
     * @return
     */
    public String schedulePushMsgAllUser(String time, String scheduleName, String msg, Map<String, String> extraMsg) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAll(msg, extraMsg);
        return pushToJG(time, scheduleName, client, payload);
    }

    /**
     * 定时推送到指定用户: time:推送时间  scheduleName:调度器名字  msg:推送消息  extraMsg:业务参数  userIdList:推送用户
     *
     * @param time
     * @param scheduleName
     * @param msg
     * @param extraMsg
     * @param userIdList
     * @return
     */
    public String schedulePushMsgAssignUser(String time, String scheduleName, String msg, Map<String, String> extraMsg, List<String> userIdList) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAssignUser(msg, extraMsg, userIdList);
        return pushToJG(time, scheduleName, client, payload);
    }

    /**
     * 定时推送到指定用户: time:推送时间  scheduleName:调度器名字  msg:推送消息  extraMsg:业务参数  userId:推送用户
     *
     * @param time
     * @param scheduleName
     * @param msg
     * @param extraMsg
     * @param userId
     * @return
     */
    public String schedulePushMsgAssignUser(String time, String scheduleName, String msg, Map<String, String> extraMsg, String... userId) {
        JPushClient client = getJPushClient();
        PushPayload payload = buildPushBodyForAssignUser(msg, extraMsg, userId);
        return pushToJG(time, scheduleName, client, payload);
    }


    /**
     * 删除定时任务的推送:scheduleId :定时推送调度器ID->返回的ID
     *
     * @param scheduleId
     * @return
     */
    public boolean delSchedulePush(String scheduleId) {
        JPushClient client = getJPushClient();
        try {
            client.deleteSchedule(scheduleId);
            return true;
        } catch (Exception e) {
            logger.info("极光推送...删除定时推送...异常", e);
            return false;
        }
    }


    /**
     * 构建推送所有用户->带额外消息
     *
     * @param msg
     * @param extras
     * @return
     */
    private PushPayload buildPushBodyForAll(String msg, Map<String, String> extras) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(msg)
                                .addPlatformNotification(getAndroidNotification(extras))
                                .addPlatformNotification(getIosNotification(extras))
                                .build())
                //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发
                .setOptions(Options.newBuilder().setApnsProduction(MODEL).build()).build();
    }


    /**
     * 构建推送指定用户;-->List形式
     *
     * @param msg
     * @param extras
     * @param userIdList
     * @return
     */
    private PushPayload buildPushBodyForAssignUser(String msg, Map<String, String> extras, List<String> userIdList) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(userIdList))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(msg)
                                .addPlatformNotification(getAndroidNotification(extras))
                                .addPlatformNotification(getIosNotification(extras))
                                .build())
                //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发
                .setOptions(Options.newBuilder().setApnsProduction(MODEL).build()).build();
    }

    /**
     * 构建推送指定用户;->可变参数形式
     *
     * @param msg
     * @param extras
     * @param userId
     * @return
     */
    private PushPayload buildPushBodyForAssignUser(String msg, Map<String, String> extras, String... userId) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(userId))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(msg)
                                .addPlatformNotification(getAndroidNotification(extras))
                                .addPlatformNotification(getIosNotification(extras))
                                .build())
                //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发
                .setOptions(Options.newBuilder().setApnsProduction(MODEL).build())
                .build();
    }


    /**
     * 构建指定标签推送;
     *
     * @param msg
     * @param extras
     * @param tagName
     * @return
     */
    private PushPayload buildPushBodyForAssignTag(String msg, Map<String, String> extras, String tagName) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagName))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(msg)
                                .addPlatformNotification(getAndroidNotification(extras))
                                .addPlatformNotification(getIosNotification(extras))
                                .build())
                //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发
                .setOptions(Options.newBuilder().setApnsProduction(MODEL).build()).build();
    }


    /**
     * 获取极光推送实例
     *
     * @return
     */
    private JPushClient getJPushClient() {
        return new JPushClient(JG_SECRET, JG_KEY);
    }

    /**
     * 安卓自定义配置
     *
     * @param extras
     * @return
     */
    public AndroidNotificationConf getAndroidNotificationConf(Map<String, String> extras) {
        String androidConf = extras.get(CustomerConfParamEnum.AndroidNotificationConf.getName());
        return androidConf != null && !"".equals(androidConf) ? JsonUtil.jsonToEntity(androidConf, AndroidNotificationConf.class) : null;

    }

    /**
     * 安卓通知
     *
     * @param extras
     * @return
     */
    public AndroidNotification getAndroidNotification(Map<String, String> extras) {
        logger.info("极光推送...构建Android通知结构体,参数=【{}】", extras);

        AndroidNotificationConf androidNotificationConf = getAndroidNotificationConf(extras);
        AndroidNotification.Builder andBuilder = AndroidNotification.newBuilder()
                .addExtras(clearParam(extras));
        if (androidNotificationConf != null) {
            andBuilder.setStyle(androidNotificationConf.getStyle());
        }
        return andBuilder.build();

    }

    /**
     * iOS自定义配置
     *
     * @param extras
     * @return
     */
    public IosNotificationConf getIosNotificationConf(Map<String, String> extras) {
        String iosConf = extras.get(CustomerConfParamEnum.IosNotificationConf.getName());
        return iosConf != null && !"".equals(iosConf) ? JsonUtil.jsonToEntity(iosConf, IosNotificationConf.class) : null;

    }

    /**
     * iOS通知
     *
     * @param extras
     * @return
     */
    public IosNotification getIosNotification(Map<String, String> extras) {
        logger.info("极光推送...构建iOS通知结构体,参数=【{}】", extras);

        IosNotificationConf iosNotificationConf = getIosNotificationConf(extras);
        IosNotification.Builder iosBuilder = IosNotification.newBuilder().addExtras(clearParam(extras));
        if (iosNotificationConf != null) {
            iosBuilder.setSound(iosNotificationConf.getHaveSound() ? "default" : "");
        }
        return iosBuilder.build();
    }

    public Map<String, String> clearParam(Map<String, String> extras) {
        Map<String, String> tempMap = new HashMap<String, String>();
        tempMap.putAll(extras);
        tempMap.remove(CustomerConfParamEnum.IosNotificationConf.getName());
        tempMap.remove(CustomerConfParamEnum.AndroidNotificationConf.getName());
        return tempMap;
    }
}
