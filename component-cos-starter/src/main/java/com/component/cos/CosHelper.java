package com.component.cos;

import com.component.cos.autoconfigure.CosProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class CosHelper {

    private static Logger logger = LoggerFactory.getLogger(CosHelper.class);

    @Autowired
    private CosProperties cosProperties;

    //上传资源
    public CosResult uploadResource(String toPath,MultipartFile file) {
        String fileOrglName = file.getOriginalFilename();
        logger.info("CosHelper uploadResource start,toPath={},fileName={}",toPath,fileOrglName);

        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
        String regionName =cosProperties.getRegion();
        String bucketName = cosProperties.getBucket();

        Region region  =new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosclient = new COSClient(cred, clientConfig);

        if(toPath==null || "".equals(toPath)) {
            toPath="/";
        }
        if(!toPath.endsWith("/")) {
            toPath= toPath+"/";
        }

        String fileName = UUID.randomUUID().toString().replaceAll("\\-", "") + "." + file.getOriginalFilename().split("\\.")[1];
        String filePath = toPath+ fileName;
        Long fileSize = file.getSize();

        CosResult cosResult = new CosResult();
        try {
            InputStream inputStream = file.getInputStream();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            logger.info("CosHelper uploadResource fileName={},metadata={}", fileName, metadata);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream,metadata);
            putObjectRequest.setStorageClass(StorageClass.Standard);

            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            String url ="https://"+bucketName+".cos."+regionName+".myqcloud.com"+filePath;

            cosResult.setSuccess(true);
            cosResult.setBucketName(bucketName);
            cosResult.setFullPath(filePath);
            cosResult.setRegionName(regionName);
            cosResult.setOrglUrl(url);

        } catch (Exception e) {
            logger.error("CosHelper uploadResource error,fileName={},bucketName={}",fileOrglName,bucketName,e);
        }finally {
            //关闭客户端
            cosclient.shutdown();
        }
        return cosResult;
    }

    //上传资源
    public CosResult uploadResource(String toPath,File sourceFile) {
        logger.info("CosHelper uploadResource start,toPath={},sourceName={}",toPath,sourceFile.getName());
        CosResult cosResult = this.uploadResource(toPath,null,sourceFile);
        return cosResult;
    }

    //上传资源
    public CosResult uploadResource(String toPath,String fileName,File sourceFile) {
        logger.info("CosHelper uploadResource start,toPath={},fileName={},sourceName={}",toPath,fileName,sourceFile.getName());

        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
        String regionName =cosProperties.getRegion();
        String bucketName = cosProperties.getBucket();

        Region region  =new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosclient = new COSClient(cred, clientConfig);

        if(toPath==null || "".equals(toPath)) {
            toPath="/";
        }
        if(!toPath.endsWith("/")) {
            toPath= toPath+"/";
        }
        if(fileName==null || "".equals(fileName)) {
            fileName= sourceFile.getName();
        }
        String newFile =toPath+fileName;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFile, sourceFile);
        putObjectRequest.setStorageClass(StorageClass.Standard);

        CosResult cosResult = new CosResult();
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            String key = putObjectRequest.getKey();//上传到云后的fullPath
            String url ="https://"+bucketName+".cos."+regionName+".myqcloud.com"+key;

            cosResult.setSuccess(true);
            cosResult.setBucketName(bucketName);
            cosResult.setFullPath(key);
            cosResult.setRegionName(regionName);
            cosResult.setOrglUrl(url);

        } catch (Exception e) {
            logger.error("CosHelper uploadResource error,sourceFile={},bucketName={}",sourceFile.getName(),bucketName,e);
        }finally {
            //关闭客户端
            cosclient.shutdown();
        }
        return cosResult;
    }

    public String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("bmp".equalsIgnoreCase(fileExtension))
            return "image/bmp";
        if ("mp4".equalsIgnoreCase(fileExtension))
            return "audio/mp4";
        if ("apk".equalsIgnoreCase(fileExtension) || "ipa".equalsIgnoreCase(fileExtension))
            return "application/octet-stream";
        if ("gif".equalsIgnoreCase(fileExtension))
            return "image/gif";
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)
                || "png".equalsIgnoreCase(fileExtension))
            return "image/jpeg";
        if ("html".equalsIgnoreCase(fileExtension))
            return "text/html";
        if ("txt".equalsIgnoreCase(fileExtension))
            return "text/plain";
        if ("vsd".equalsIgnoreCase(fileExtension))
            return "application/vnd.visio";
        if ("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension))
            return "application/vnd.ms-powerpoint";
        if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension))
            return "application/msword";
        if ("xml".equalsIgnoreCase(fileExtension))
            return "text/xml";
        if ("mp3".equalsIgnoreCase(fileExtension) || "m4a".equalsIgnoreCase(fileExtension) || "WMA".equalsIgnoreCase(fileExtension))
            return "audio/x-wav";
        return "text/html";
    }

    public String getBaseUrl() {
        String regionName =cosProperties.getRegion();
        String bucketName = cosProperties.getBucket();
        String url ="https://"+bucketName+".cos."+regionName+".myqcloud.com";
        return url;
    }
}
