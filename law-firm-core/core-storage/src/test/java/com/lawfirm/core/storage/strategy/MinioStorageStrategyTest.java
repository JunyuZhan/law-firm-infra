package com.lawfirm.core.storage.strategy;

import com.lawfirm.core.storage.config.StorageProperties;
import io.minio.*;
import io.minio.messages.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinioStorageStrategyTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private MinioStorageStrategy minioStorageStrategy;

    private MultipartFile testFile;
    private String bucketName;
    private String objectName;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            "Hello, Minio!".getBytes(StandardCharsets.UTF_8)
        );
        bucketName = "test-bucket";
        objectName = "test/test.txt";
        
        when(storageProperties.getBucketName()).thenReturn(bucketName);
    }

    @Test
    void uploadFile_Success() throws Exception {
        // Given
        PutObjectResponse response = new PutObjectResponse(null, null, null, null, null);
        when(minioClient.putObject(any(PutObjectArgs.class))).thenReturn(response);

        // When
        String result = minioStorageStrategy.uploadFile(objectName, testFile);

        // Then
        assertEquals(objectName, result);
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    void downloadFile_Success() throws Exception {
        // Given
        byte[] expectedContent = "Hello, Minio!".getBytes(StandardCharsets.UTF_8);
        GetObjectResponse mockResponse = mock(GetObjectResponse.class);
        when(mockResponse.readAllBytes()).thenReturn(expectedContent);
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(mockResponse);

        // When
        byte[] result = minioStorageStrategy.downloadFile(objectName);

        // Then
        assertArrayEquals(expectedContent, result);
        verify(minioClient).getObject(any(GetObjectArgs.class));
    }

    @Test
    void deleteFile_Success() throws Exception {
        // Given
        doNothing().when(minioClient).removeObject(any(RemoveObjectArgs.class));

        // When
        minioStorageStrategy.deleteFile(objectName);

        // Then
        verify(minioClient).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void initMultipartUpload_Success() throws Exception {
        // Given
        String uploadId = "test-upload-id";
        CreateMultipartUploadResponse response = new CreateMultipartUploadResponse(bucketName, objectName, uploadId);
        when(minioClient.createMultipartUpload(any(CreateMultipartUploadArgs.class))).thenReturn(response);

        // When
        String result = minioStorageStrategy.initMultipartUpload(objectName);

        // Then
        assertEquals(uploadId, result);
        verify(minioClient).createMultipartUpload(any(CreateMultipartUploadArgs.class));
    }

    @Test
    void uploadPart_Success() throws Exception {
        // Given
        String uploadId = "test-upload-id";
        int partNumber = 1;
        String etag = "test-etag";
        UploadPartResponse response = new UploadPartResponse(null, null, etag, null, partNumber);
        when(minioClient.uploadPart(any(UploadPartArgs.class))).thenReturn(response);

        // When
        String result = minioStorageStrategy.uploadPart(uploadId, partNumber, testFile);

        // Then
        assertEquals(etag, result);
        verify(minioClient).uploadPart(any(UploadPartArgs.class));
    }

    @Test
    void completeMultipartUpload_Success() throws Exception {
        // Given
        String uploadId = "test-upload-id";
        List<Part> parts = new ArrayList<>();
        parts.add(new Part(1, "etag1"));
        parts.add(new Part(2, "etag2"));
        
        ObjectWriteResponse response = new ObjectWriteResponse(null, bucketName, null, objectName, null, null);
        when(minioClient.completeMultipartUpload(any(CompleteMultipartUploadArgs.class))).thenReturn(response);

        // When
        String result = minioStorageStrategy.completeMultipartUpload(uploadId, objectName);

        // Then
        assertEquals(objectName, result);
        verify(minioClient).completeMultipartUpload(any(CompleteMultipartUploadArgs.class));
    }

    @Test
    void abortMultipartUpload_Success() throws Exception {
        // Given
        String uploadId = "test-upload-id";
        doNothing().when(minioClient).abortMultipartUpload(any(AbortMultipartUploadArgs.class));

        // When
        minioStorageStrategy.abortMultipartUpload(uploadId);

        // Then
        verify(minioClient).abortMultipartUpload(any(AbortMultipartUploadArgs.class));
    }

    @Test
    void getFileUrl_Success() {
        // Given
        String expectedUrl = "http://localhost:9000/test-bucket/test/test.txt";
        when(storageProperties.getEndpoint()).thenReturn("http://localhost:9000");

        // When
        String result = minioStorageStrategy.getFileUrl(objectName);

        // Then
        assertEquals(expectedUrl, result);
    }
} 