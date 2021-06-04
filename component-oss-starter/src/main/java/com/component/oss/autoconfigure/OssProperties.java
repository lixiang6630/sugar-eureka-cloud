package com.component.oss.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OSS 配置
 *
 * @author: LX
 */
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * 地域节点
     */
    private String endpoint = "";

    /**
     * accessKey Id
     */
    private String accessKeyId = "";

    /**
     * accessKey Secret
     */
    private String accessKeySecret = "";

    /**
     * bucket name
     */
    private String bucketName = "";

    /**
     * 父文件夹路径 无自动创建
     */
    private String diskNamePrefix = "resources/";

    /**
     * 文件访问域名,Bucket域名/自定义域名
     */
    private String requestUrl = "";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDiskNamePrefix() {
        return diskNamePrefix;
    }

    public void setDiskNamePrefix(String diskNamePrefix) {
        this.diskNamePrefix = diskNamePrefix;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
