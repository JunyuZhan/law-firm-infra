package com.lawfirm.common.data.storage.impl.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "storage.aliyun")
public class AliyunOssProperties {
    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 访问密钥ID
     */
    private String accessKeyId;

    /**
     * 访问密钥密码
     */
    private String accessKeySecret;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 默认存储桶名称
     */
    private String defaultBucketName;

    /**
     * 默认过期时间（秒）
     */
    private Integer defaultExpiry = 3600;
} 