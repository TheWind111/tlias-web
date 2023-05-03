package com.example;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


//阿里云oss工具类
public class AliOSSUtils {

//    阿里云oss属性配置
    private AliOSSProperties aliOSSProperties;

//    给当前
    public void setAliOSSProperties(AliOSSProperties aliOSSProperties) {
        this.aliOSSProperties = aliOSSProperties;
    }

    public AliOSSProperties getAliOSSProperties() {
        return aliOSSProperties;
    }

//    给上传文件生成新名字，上传保存至oss,返回该文件访问路径。
    public String upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String endpoint = aliOSSProperties.getEndpoint();
        String accessKeyId = aliOSSProperties.getAccessKeyId();
        String accessKeySecret = aliOSSProperties.getAccessKeySecret();
        String bucketName = aliOSSProperties.getBucketName();

        OSS ossClient = new OSSClientBuilder().build( endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] +"/"+fileName;
        ossClient.shutdown();
        return url;

    }

}
