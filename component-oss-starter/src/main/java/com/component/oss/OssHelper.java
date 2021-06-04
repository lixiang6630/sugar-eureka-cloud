package com.component.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.component.oss.autoconfigure.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * OSS 工具包
 *
 * @author: LX
 */
public class OssHelper {

    private static final Logger logger = LoggerFactory.getLogger(OssHelper.class);

    private String ENDPOINT;
    private String ACCESS_KEY_ID;
    private String ACCESS_KEY_SECRET;
    private String BUCKET_NAME;
    private String DISK_NAME_PREFIX;
    private String REQUEST_URL;

    @Autowired
    private OssProperties ossProperties;

    @PostConstruct
    public void init() {
        this.ENDPOINT = ossProperties.getEndpoint();
        this.ACCESS_KEY_ID = ossProperties.getAccessKeyId();
        this.ACCESS_KEY_SECRET = ossProperties.getAccessKeySecret();
        this.BUCKET_NAME = ossProperties.getBucketName();
        this.DISK_NAME_PREFIX = ossProperties.getDiskNamePrefix();
        this.REQUEST_URL = ossProperties.getRequestUrl();

        if (!DISK_NAME_PREFIX.endsWith(Constant.SEPARATOR)) {
            DISK_NAME_PREFIX = DISK_NAME_PREFIX + Constant.SEPARATOR;
        }
        if (!REQUEST_URL.endsWith(Constant.SEPARATOR)) {
            REQUEST_URL = REQUEST_URL + Constant.SEPARATOR;
        }

    }

    /**
     * @description: 获取阿里云OSS客户端对象
     * @author: LX
     */
    public final OSSClient getOSSClient() {
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * @description: 新建Bucket --Bucket权限:私有
     * @author: LX
     */
    public boolean createBucket(OSSClient client, String bucketName) {
        Bucket bucket = this.getOSSClient().createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * @description: 删除Bucket
     * @author: LX
     */
    public void deleteBucket(String bucketName) {
        this.getOSSClient().deleteBucket(bucketName);
        logger.info("删除 {} Bucket成功", bucketName);
    }

    /**
     * @description: 上传阿里云OSS
     * @author: LX
     */
    public OssResult uploadObject2OSS(MultipartFile file, String fileType) {
        String filePath = null;
        try {
            InputStream is = file.getInputStream();
            String fileName = UUID.randomUUID().toString().replaceAll("\\-", "") + "." + file.getOriginalFilename().split("\\.")[1];
            filePath = DISK_NAME_PREFIX + fileType + "/" + fileName;
            Long fileSize = file.getSize();
            upload(filePath, fileName, fileType, is, fileSize);
        } catch (Exception e) {
            logger.error("上传阿里云OSS服务器异常.", e);
        }
        return new OssResult(REQUEST_URL + filePath,
                filePath,
                file.getOriginalFilename().split("\\.")[0],
                file.getSize() / Constant.SCALE
        );
    }

    public void upload(String filePath, String fileName, String fileType, InputStream is, Long fileSize) {
        uploadFile(filePath, fileName, fileType, is, fileSize);
    }

    public void uploadFile(String filePath, String fileName, String fileType, InputStream is, Long fileSize) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            logger.info("bucketName:{},diskName:{} fileName:{} is:{},metadata:{}", BUCKET_NAME, DISK_NAME_PREFIX + fileType, fileName, is, metadata);
            this.getOSSClient().putObject(BUCKET_NAME, filePath, is, metadata);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常.", e);
        }
    }

    /**
     * @description: 根据key获取OSS服务器上的文件输入流
     * @author: LX
     */
    public InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName,
                                          String key) {
        OSSObject ossObj = this.getOSSClient().getObject(bucketName, diskName + key);
        return ossObj.getObjectContent();
    }

    /**
     * @description: 根据key删除OSS服务器上的文件
     * @author: LX
     */
    public void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
        client.deleteObject(bucketName, diskName + key);
        logger.info("删除 {} 下的文件 {}{} 成功", bucketName, diskName, key);
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String filenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (filenameExtension.equalsIgnoreCase(".bmp")) {
            return "application/x-bmp";
        }
        if (filenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".jpeg") ||
                filenameExtension.equalsIgnoreCase(".jpg") ||
                filenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".pptx") ||
                filenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".docx") ||
                filenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase(".xla") ||
                filenameExtension.equalsIgnoreCase(".xlc") ||
                filenameExtension.equalsIgnoreCase(".xlm") ||
                filenameExtension.equalsIgnoreCase(".xls") ||
                filenameExtension.equalsIgnoreCase(".xlt") ||
                filenameExtension.equalsIgnoreCase(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (filenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (filenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (filenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (filenameExtension.equalsIgnoreCase(".tar")) {
            return "application/x-tar";
        }
        if (filenameExtension.equalsIgnoreCase(".avi")) {
            return "video/avi";
        }
        if (filenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (filenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (filenameExtension.equalsIgnoreCase(".mp2")) {
            return "audio/mp2";
        }
        return "application/octet-stream";
    }
}
