package com.component.sms.ihuyi;

import com.component.sms.ihuyi.autoconfigure.SmsIhuyiProperties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HuYiSmsSender {

    private static Logger logger = LoggerFactory.getLogger(HuYiSmsSender.class);

    @Autowired
    private SmsIhuyiProperties smsIhuyiProperties;

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @return
     */
    public SmsResult sendCode(String mobile) {
        logger.info("HuYiSmsSender sendCode start,mobile={}", mobile);

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsIhuyiProperties.getUrl());
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        String mobileCode = getRandomNum(smsIhuyiProperties.getLength());
        String content = smsIhuyiProperties.getContent().replace("{}", mobileCode);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>() {{
            add(new NameValuePair("account", smsIhuyiProperties.getAccount()));
            add(new NameValuePair("password", smsIhuyiProperties.getPassword()));
            add(new NameValuePair("mobile", mobile));
            add(new NameValuePair("content", content));
            if (smsIhuyiProperties.getSign() != null && !"".equals(smsIhuyiProperties.getSign().trim().replaceAll(" ", ""))) {
                add(new NameValuePair("sign", smsIhuyiProperties.getSign()));
            }
        }};

        NameValuePair[] data = new NameValuePair[nameValuePairs.size()];
        nameValuePairs.toArray(data);
        method.setRequestBody(data);

        SmsResult smsResult = new SmsResult(mobile, mobileCode);
        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            smsResult.setCode(code);
            smsResult.setMsg(msg);
            smsResult.setSmsId(smsid);

            if (code != null && "2".equals(code)) {
                smsResult.setState(0);
            } else {
                smsResult.setState(1);
                smsResult.setReason("错误码=【" + code + "】,错误信息=【" + msg + "】");
                logger.info("【短信发送】短信发送失败,手机号=【{}】,错误码=【{}】,错误信息=【{}】,消息ID=【{}】", mobile, code, msg, smsid);
            }

        } catch (Exception e) {
            logger.error("HuYiSmsSender sendCode error,mobile={},", mobile, e);
            smsResult.setState(2);
            String exceptionInfo = e == null ? "" : e.getMessage();
            smsResult.setReason(exceptionInfo);
        }
        logger.info("HuYiSmsSender sendCode end,mobile={},smsResult={}", mobile, smsResult);
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
