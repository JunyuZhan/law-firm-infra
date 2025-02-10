package com.lawfirm.common.data.storage.impl.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "storage.aliyun", name = "enabled", havingValue = "true")
public class AliyunOssConfiguration {
    
    @Bean
    public OSS ossClient(AliyunOssProperties properties) {
        return new OSSClientBuilder().build(
            properties.getEndpoint(),
            properties.getAccessKeyId(),
            properties.getAccessKeySecret()
        );
    }
}