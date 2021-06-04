package com.component.oss;

/**
 * OSS 上传结果
 *
 * @author: LX
 */
public class OssResult {


    /**
     * 访问路径-带域名 绝对路径
     */
    private String accessPath;
    /**
     * 相对路径-不带域名 相对路径
     */
    private String savePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小 单位 k
     */
    private Long size;

    public OssResult() {
    }

    public OssResult(String accessPath, String savePath, String fileName, Long size) {
        this.accessPath = accessPath;
        this.savePath = savePath;
        this.fileName = fileName;
        this.size = size;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
