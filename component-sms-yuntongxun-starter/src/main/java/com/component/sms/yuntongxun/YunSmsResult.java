package com.component.sms.yuntongxun;

public class YunSmsResult {

    public static String SUCCESS_CODE="000000";

    private String statusCode;

    private String statusMsg;

    private  TemplateSMS templateSMS;


    public  static class TemplateSMS {

        private String smsMessageSid;

        private String dateCreated;

        public String getSmsMessageSid() {
            return smsMessageSid;
        }

        public void setSmsMessageSid(String smsMessageSid) {
            this.smsMessageSid = smsMessageSid;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String toString() {
            return "TemplateSMS{" +
                    "smsMessageSid='" + smsMessageSid + '\'' +
                    ", dateCreated='" + dateCreated + '\'' +
                    '}';
        }
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public TemplateSMS getTemplateSMS() {
        return templateSMS;
    }

    public void setTemplateSMS(TemplateSMS templateSMS) {
        this.templateSMS = templateSMS;
    }

    @Override
    public String toString() {
        return "YunSmsResult{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", templateSMS=" + templateSMS +
                '}';
    }
}
