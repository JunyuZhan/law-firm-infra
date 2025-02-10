package com.lawfirm.core.storage.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "storage.minio", name = "enabled", havingValue = "true")
public class MinioConfig {

    private final StorageProperties storageProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(storageProperties.getMinio().getEndpoint())
                .credentials(
                        storageProperties.getMinio().getAccessKey(),
                        storageProperties.getMinio().getSecretKey()
                )
                .build();
    }
} 