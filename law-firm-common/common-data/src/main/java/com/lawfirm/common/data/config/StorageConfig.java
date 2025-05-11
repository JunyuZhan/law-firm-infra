package com.lawfirm.common.data.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象存储配置
 */
@Data
@Configuration
public class StorageConfig {

    /**
     * MinIO配置
     */
    @Data
    @Configuration
    @ConfigurationProperties(prefix = "law-firm.common.storage.minio")
    @ConditionalOnProperty(prefix = "law-firm.common.storage.minio", name = "enabled", havingValue = "true")
    public static class MinioConfig {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucketName;

        @Bean(name = "commonMinioClient")
        public MinioClient minioClient() {
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        }
    }

    /**
     * 阿里云OSS配置
     */
    @Data
    @Configuration
    @ConfigurationProperties(prefix = "law-firm.common.storage.aliyun")
    @ConditionalOnProperty(prefix = "law-firm.common.storage.aliyun", name = "enabled", havingValue = "true")
    public static class AliyunOssConfig {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;

        @Bean(name = "aliyunOssClient")
        public OSS ossClient() {
            return new OSSClientBuilder()
                    .build(endpoint, accessKeyId, accessKeySecret);
        }
    }
} 