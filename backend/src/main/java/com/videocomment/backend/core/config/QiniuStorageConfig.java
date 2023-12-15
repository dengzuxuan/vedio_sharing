package com.videocomment.backend.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName QiniuStorageConfig
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/6 17:45
 * @Version 1.0
 */
@Data
@Configuration
public class QiniuStorageConfig {
    /**
     * 七牛域名domain
     */
    @Value("${oss.qiniu.domain}")
    private String qiniuDomain;
    /**
     * 七牛ACCESS_KEY
     */
    @Value("${oss.qiniu.accessKey}")
    private String qiniuAccessKey;
    /**
     * 七牛SECRET_KEY
     */
    @Value("${oss.qiniu.secretKey}")
    private String qiniuSecretKey;
    /**
     * 七牛空间名
     */
    @Value("${oss.qiniu.bucketName}")
    private String qiniuBucketName;
}
