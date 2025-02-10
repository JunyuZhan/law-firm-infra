package com.lawfirm.core.storage.integration;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.service.impl.StorageServiceImpl;
import com.lawfirm.core.storage.strategy.MinioStorageStrategy;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class StorageIntegrationTest {

    @Container
    static GenericContainer<?> minioContainer = new GenericContainer<>(DockerImageName.parse("minio/minio"))
        .withExposedPorts(9000)
        .withEnv("MINIO_ACCESS_KEY", "minioadmin")
        .withEnv("MINIO_SECRET_KEY", "minioadmin")
        .withCommand("server /data");

    @Autowired
    private StorageServiceImpl storageService;

    @Autowired
    private MinioStorageStrategy storageStrategy;

    @Autowired
    private StorageProperties storageProperties;

    private MultipartFile testFile;
    private String fileName;

    @DynamicPropertySource
    static void registerMinioProperties(DynamicPropertyRegistry registry) {
        registry.add("storage.endpoint", () -> 
            String.format("http://%s:%d", minioContainer.getHost(), minioContainer.getFirstMappedPort()));
        registry.add("storage.access-key", () -> "minioadmin");
        registry.add("storage.secret-key", () -> "minioadmin");
        registry.add("storage.bucket-name", () -> "test-bucket");
    }

    @BeforeEach
    void setUp() throws Exception {
        testFile = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            "Integration test content".getBytes(StandardCharsets.UTF_8)
        );
        fileName = "test/test.txt";

        // 确保bucket存在
        MinioClient minioClient = MinioClient.builder()
            .endpoint(storageProperties.getEndpoint())
            .credentials(storageProperties.getAccessKey(), storageProperties.getSecretKey())
            .build();

        if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder()
            .bucket(storageProperties.getBucketName())
            .build())) {
            minioClient.makeBucket(io.minio.MakeBucketArgs.builder()
                .bucket(storageProperties.getBucketName())
                .build());
        }
    }

    @Test
    void fullStorageFlow_Success() throws IOException {
        // 1. 上传文件
        String uploadedPath = storageService.uploadFile(testFile);
        assertNotNull(uploadedPath);
        assertTrue(uploadedPath.endsWith(fileName));

        // 2. 获取文件URL
        String fileUrl = storageService.getFileUrl(uploadedPath);
        assertNotNull(fileUrl);
        assertTrue(fileUrl.contains(storageProperties.getBucketName()));

        // 3. 下载文件
        byte[] downloadedContent = storageService.downloadFile(uploadedPath);
        assertNotNull(downloadedContent);
        assertTrue(Arrays.equals(testFile.getBytes(), downloadedContent));

        // 4. 删除文件
        storageService.deleteFile(uploadedPath);
        
        // 5. 验证文件已删除
        assertThrows(Exception.class, () -> storageService.downloadFile(uploadedPath));
    }

    @Test
    void chunkedUploadFlow_Success() throws Exception {
        // 1. 初始化分片上传
        String uploadId = storageStrategy.initMultipartUpload(fileName);
        assertNotNull(uploadId);

        // 2. 上传分片
        String etag = storageStrategy.uploadPart(uploadId, 1, testFile);
        assertNotNull(etag);

        // 3. 完成分片上传
        String completedPath = storageStrategy.completeMultipartUpload(uploadId, fileName);
        assertNotNull(completedPath);
        assertEquals(fileName, completedPath);

        // 4. 验证上传的文件
        byte[] downloadedContent = storageService.downloadFile(completedPath);
        assertNotNull(downloadedContent);
        assertTrue(Arrays.equals(testFile.getBytes(), downloadedContent));

        // 5. 清理
        storageService.deleteFile(completedPath);
    }
} 