package com.component.cos;

public class CosResult {

    //是否成功
    private boolean success=false;

    //文件全路径，包括文件名
    private String fullPath;

    //存储桶名称
    private String bucketName;

    //地域
    private String regionName;

    //原生url
    private String orglUrl;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getOrglUrl() {
        return orglUrl;
    }

    public void setOrglUrl(String orglUrl) {
        this.orglUrl = orglUrl;
    }


}
