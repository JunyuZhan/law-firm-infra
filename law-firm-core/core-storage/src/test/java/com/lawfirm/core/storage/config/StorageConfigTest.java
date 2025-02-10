package com.lawfirm.core.storage.config;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class StorageConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withUserConfiguration(TestConfig.class);

    @Test
    void storageProperties_LoadsCorrectly() {
        contextRunner.withPropertyValues(
            "storage.endpoint=http://localhost:9000",
            "storage.access-key=minioadmin",
            "storage.secret-key=minioadmin",
            "storage.bucket-name=test-bucket",
            "storage.base-path=test",
            "storage.preview-path=preview"
        ).run(context -> {
            StorageProperties properties = context.getBean(StorageProperties.class);
            assertThat(properties.getEndpoint()).isEqualTo("http://localhost:9000");
            assertThat(properties.getAccessKey()).isEqualTo("minioadmin");
            assertThat(properties.getSecretKey()).isEqualTo("minioadmin");
            assertThat(properties.getBucketName()).isEqualTo("test-bucket");
            assertThat(properties.getBasePath()).isEqualTo("test");
            assertThat(properties.getPreviewPath()).isEqualTo("preview");
        });
    }

    @Test
    void minioClient_CreatedCorrectly() {
        contextRunner.withPropertyValues(
            "storage.endpoint=http://localhost:9000",
            "storage.access-key=minioadmin",
            "storage.secret-key=minioadmin"
        ).run(context -> {
            MinioClient minioClient = context.getBean(MinioClient.class);
            assertThat(minioClient).isNotNull();
        });
    }

    @Test
    void storageAutoConfiguration_LoadsCorrectly() {
        contextRunner.withPropertyValues(
            "storage.endpoint=http://localhost:9000",
            "storage.access-key=minioadmin",
            "storage.secret-key=minioadmin",
            "storage.bucket-name=test-bucket"
        ).run(context -> {
            assertThat(context).hasSingleBean(StorageAutoConfiguration.class);
            assertThat(context).hasSingleBean(MinioConfig.class);
            assertThat(context).hasSingleBean(StorageProperties.class);
            assertThat(context).hasSingleBean(MinioClient.class);
        });
    }

    @Configuration
    static class TestConfig {
        @Bean
        StorageProperties storageProperties() {
            return new StorageProperties();
        }

        @Bean
        MinioConfig minioConfig(StorageProperties properties) {
            return new MinioConfig();
        }

        @Bean
        StorageAutoConfiguration storageAutoConfiguration() {
            return new StorageAutoConfiguration();
        }
    }
} 