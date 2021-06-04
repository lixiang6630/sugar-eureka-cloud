package com.component.mpush;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.push.model.v20160801.MassPushRequest;
import com.aliyuncs.push.model.v20160801.MassPushResponse;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
import com.component.mpush.autoconfigure.PushAliyunProperties;
import com.component.mpush.model.ANDMpushParam;
import com.component.mpush.model.IOSMpushParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OSS 工具包
 *
 * @author: LX
 */
public class MpushSender {

    private static final Logger log = LoggerFactory.getLogger(MpushSender.class);

    private String ACCESS_KEY_ID;

    private String ACCESS_KEY_SECRET;

    private String IOS_APP_KEY;

    private String AND_APP_KEY;

    private String IOS_APNS_ENV;

    private String ANDROID_POPUP_ACTIVITY;

    private String PUSH_TYPE;

    private String Target;

    @Autowired
    private PushAliyunProperties pushAliyunProperties;

    @PostConstruct
    public void init() {
        this.ACCESS_KEY_ID = pushAliyunProperties.getAccessKeyId();
        this.ACCESS_KEY_SECRET = pushAliyunProperties.getAccessKeySecret();
        this.IOS_APP_KEY = pushAliyunProperties.getIosAppKey();
        this.AND_APP_KEY = pushAliyunProperties.getAndAppKey();
        this.IOS_APNS_ENV = pushAliyunProperties.getIosApnsEnv();
        this.ANDROID_POPUP_ACTIVITY = pushAliyunProperties.getAndroidPopupActivity();
        this.PUSH_TYPE = pushAliyunProperties.getPushType();
    }

    /**
     * @description: 获取阿里云Mpush客户端对象
     * @author: LX
     */
    public final IAcsClient getMpushClient() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public void sendAndPush(ANDMpushParam andMpushParam){
        IAcsClient mpushClient = this.getMpushClient();
        PushRequest request = new PushRequest();
        request.setRegionId("cn-hangzhou");
        request.setAppKey(Long.valueOf(AND_APP_KEY));
        request.setPushType(PUSH_TYPE);
        request.setDeviceType("ANDROID");
        request.setTarget("ALIAS");
        request.setTargetValue(andMpushParam.getToUserId());
        request.setBody(andMpushParam.getContent());
        request.setTitle(andMpushParam.getTitle());
        request.setStoreOffline(true);
        request.setAndroidNotifyType("BOTH");
        request.setAndroidOpenType("APPLICATION");
        request.setAndroidPopupActivity(ANDROID_POPUP_ACTIVITY);
        request.setAndroidPopupTitle(andMpushParam.getTitle());
        request.setAndroidPopupBody(andMpushParam.getContent());
        request.setAndroidNotificationChannel("1");

        try {
            PushResponse response = mpushClient.getAcsResponse(request);
            log.info("阿里云android推送结果：【{}】", JSON.toJSONString(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("androidErrCode:" + e.getErrCode());
            log.info("androidErrMsg:" + e.getErrMsg());
            log.info("androidRequestId:" + e.getRequestId());
        }

    }

    public Boolean sendIOSPush(IOSMpushParam iosMpushParam){
        IAcsClient mpushClient = this.getMpushClient();
        PushRequest request = new PushRequest();
        request.setRegionId("cn-hangzhou");
        request.setAppKey(Long.valueOf(iosMpushParam.getAppKey()));
        request.setPushType(PUSH_TYPE);
        request.setDeviceType("iOS");
        request.setTarget("ALIAS");
        request.setTargetValue(iosMpushParam.getToUserId());
        request.setBody(iosMpushParam.getContent());
        request.setTitle(iosMpushParam.getTitle());
        request.setIOSApnsEnv(iosMpushParam.getIosApnsEnv());
        request.setIOSRemind(true);
        request.setIOSRemindBody(iosMpushParam.getContent());
        request.setIOSMusic(iosMpushParam.getMusic());
        request.setIOSSilentNotification(false);
        request.setIOSExtParameters(iosMpushParam.getiOSExtParameters());
        try {
            PushResponse response = mpushClient.getAcsResponse(request);
            log.info("阿里云IOS推送结果：【{}】",JSON.toJSONString(response));
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            log.info("IOSErrCode:" + e.getErrCode());
            log.info("IOSErrMsg:" + e.getErrMsg());
            log.info("IOSRequestId:" + e.getRequestId());
            return false;
        }
        return true;
    }

    /**
     * 使用批量推送
     * @param iosMpushParams
     */
    public void doPushTask(List<IOSMpushParam> iosMpushParams){
        IAcsClient client = this.getMpushClient();
        try {
            Map<String, List<IOSMpushParam>> stringListMap = iosMpushParams.stream().collect(Collectors.groupingBy(IOSMpushParam::getAppKey));
            Set<String> appKeys = stringListMap.keySet();
            log.info("批量推送  分组appKeys【{}】",appKeys);
            for (String appKey : appKeys) {
                List<IOSMpushParam> iosMpushParamsByAppKey = stringListMap.get(appKey);
                List<String> collect = iosMpushParamsByAppKey.stream().map(IOSMpushParam::getToUserId).collect(Collectors.toList());
                log.info("执行批量推送的当前分组appKey【{}】的用户有【{}】",appKey,JSON.toJSONString(collect));
                MassPushRequest massPushRequest = new MassPushRequest();
                massPushRequest.setAppKey(Long.parseLong(appKey));
                List<MassPushRequest.PushTask> pushTasks = new ArrayList<MassPushRequest.PushTask>();
                for (IOSMpushParam mpushParam : iosMpushParamsByAppKey) {
                    MassPushRequest.PushTask pushTask = new MassPushRequest.PushTask();
                    // 推送目标
                    pushTask.setTarget("ALIAS"); //推送目标: DEVICE:推送给设备; ACCOUNT:推送给指定帐号,TAG:推送给自定义标签; ALIAS: 按别名推送; ALL: 全推
                    pushTask.setTargetValue(mpushParam.getToUserId()); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
                    pushTask.setDeviceType("iOS"); // 设备类型deviceType, iOS设备: "iOS"; Android设备: "ANDROID"; 全部: "ALL", 这是默认值.
                    // 推送配置
                    pushTask.setPushType("NOTICE"); // MESSAGE:表示消息(默认), NOTICE:表示通知
                    pushTask.setTitle(mpushParam.getTitle()); // 消息的标题
                    pushTask.setBody(mpushParam.getContent()); // 消息的内容
                    // 推送配置: iOS
                    pushTask.setIOSMusic(mpushParam.getMusic()); // iOS通知声音
                    pushTask.setIOSApnsEnv(mpushParam.getIosApnsEnv());//iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。'DEV': 表示开发环境 'PRODUCT': 表示生产环境
                    pushTask.setIOSRemind(true); //  消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：**离线消息转通知仅适用于`生产环境`**
                    pushTask.setIOSRemindBody(mpushParam.getContent()); // iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=`PRODUCT` && iOSRemind为true时有效
                    pushTask.setIOSExtParameters(mpushParam.getiOSExtParameters()); //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
                    pushTasks.add(pushTask);
                }
                massPushRequest.setPushTasks(pushTasks);
                MassPushResponse massPushResponse = client.getAcsResponse(massPushRequest);
                log.info("阿里云批量推送 RequestId: {}, MessageIds: {}\n", massPushResponse.getRequestId(), massPushResponse.getMessageIds().toString());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }



}
