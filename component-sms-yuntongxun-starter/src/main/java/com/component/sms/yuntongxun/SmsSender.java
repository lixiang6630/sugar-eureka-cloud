package com.component.sms.yuntongxun;

import com.common.base.util.JsonUtils;
import com.component.sms.yuntongxun.autoconfigure.SmsYuntongxunProperties;
import com.component.sms.yuntongxun.util.EncryptUtil;
import com.component.sms.yuntongxun.util.HttpClientUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class SmsSender {

    private static Logger logger = LoggerFactory.getLogger(SmsSender.class);

    @Autowired
    private SmsYuntongxunProperties smsYuntongxunProperties;

    //发送验证码
    public SmsResult sendCode(Integer templateId,String mobile,String mobileCode,String [] otherArgs) {
        logger.info("SmsSender sendCode start,mobile={},mobileCode={},templateId={}",mobile,mobileCode,templateId);

        SmsResult smsResult = new SmsResult(mobile,mobileCode);
        try {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp= dateFormat.format(now);
            String accountSid = smsYuntongxunProperties.getAccountSid();
            //sig=MD5加密（账户Sid + 账户授权令牌 + 时间戳）
            String sig = accountSid + smsYuntongxunProperties.getAuthToken() + timestamp;
            String signature= EncryptUtil.md5Digest(sig);

            String src = accountSid + ":" + timestamp;
            String auth = EncryptUtil.base64Encoder(src);
            Map<String,String> headerMap= new HashMap<String,String>();
            headerMap.put("Authorization",auth);

            String realUrl = new StringBuffer(smsYuntongxunProperties.getUrl())
                    .append("/2013-12-26/Accounts/")
                    .append(accountSid)
                    .append("/SMS/TemplateSMS?sig=")
                    .append(signature).toString();


            List<String> datasList =new ArrayList<String>();
            datasList.add(mobileCode);
            if(otherArgs!=null && otherArgs.length>0) {
                for(String per:otherArgs) {
                    datasList.add(per);
                }
            }
            String [] datas= datasList.toArray(new String [datasList.size()]);
            YumSmsInfo smsInfo = new YumSmsInfo();
            smsInfo.setTemplateId(""+templateId);
            smsInfo.setTo(mobile);
            smsInfo.setAppId(smsYuntongxunProperties.getAppId());
            smsInfo.setDatas(datas);
            String bodyJsonStr =JsonUtils.beanToJson(smsInfo);

            logger.info("SmsSender sendCode 组装信息为:realUrl={},bodyJsonStr={},signature={},auth={}",realUrl,bodyJsonStr,signature,auth);

            String reqTimeoutStr = smsYuntongxunProperties.getReqTimeout();
            String connectTimeoutStr =smsYuntongxunProperties.getConnectTimeout();
            int reqTimeout = NumberUtils.isNumber(reqTimeoutStr)?Integer.parseInt(reqTimeoutStr):HttpClientUtil.SO_TIMEOUT_MS;
            int connectTimeout = NumberUtils.isNumber(connectTimeoutStr)?Integer.parseInt(connectTimeoutStr):HttpClientUtil.CONNECTION_TIMEOUT_MS;

            String rtInfo=HttpClientUtil.post(realUrl,headerMap,bodyJsonStr,connectTimeout,reqTimeout);
            logger.info("SmsSender sendCode return mobile={},rtInfo={}",mobile,rtInfo);

            YunSmsResult yunSmsResult = JsonUtils.jsonToBean(rtInfo,YunSmsResult.class);
            String rtCode = yunSmsResult.getStatusCode();
            smsResult.setCode(rtCode);

            if (rtCode!=null && SmsResultTypeEnum.OK.getCode().equals(rtCode)) {
                smsResult.setState(0);
                smsResult.setSmsId(yunSmsResult.getTemplateSMS().getSmsMessageSid());
            } else {
                smsResult.setState(1);
                String errorMsg=yunSmsResult.getStatusMsg();
                smsResult.setMsg(errorMsg);
                smsResult.setReason("错误码=【" + rtCode + "】,错误信息=【"+errorMsg+"】");
                logger.info("【短信发送】短信发送失败,手机号=【{}】,错误码=【{}】,错误信息=【{}】", mobile, rtCode, errorMsg);
            }

        } catch (Exception e) {
            logger.error("SmsSender sendCode error,mobile={},",mobile,e);
            smsResult.setState(2);
            String exceptionInfo = e==null?"":e.getMessage();
            smsResult.setReason(exceptionInfo);
        }
        logger.info("SmsSender sendCode end,mobile={},smsResult={}",mobile,smsResult);
        return smsResult;
    }

    //生成随机数字
    public static String getRandomNum(int length) {
        // length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
