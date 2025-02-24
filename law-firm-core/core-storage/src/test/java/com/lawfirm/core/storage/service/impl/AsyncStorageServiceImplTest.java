package com.lawfirm.core.storage.service.impl;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.MinioStorageStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncStorageServiceImplTest {

    @Mock
    private MinioStorageStrategy storageStrategy;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private AsyncStorageServiceImpl asyncStorageService;

    private MultipartFile testFile;
    private String filePath;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            "Async content".getBytes(StandardCharsets.UTF_8)
        );
        filePath = "test/test.txt";
    }

    @Test
    void asyncUpload_Success() throws Exception {
        // Given
        String expectedPath = "test/test.txt";
package com.lawfirm.core.storage.service.impl;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.MinioStorageStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncStorageServiceImplTest {

    @Mock
    private MinioStorageStrategy storageStrategy;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private AsyncStorageServiceImpl asyncStorageService;

    private MultipartFile testFile;
    private String filePath;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            "Async content".getBytes(StandardCharsets.UTF_8)
        );
        filePath = "test/test.txt";
    }

    @Test
    void asyncUpload_Success() throws Exception {
        // Given
        String expectedPath = "test/test.txt";
        when(storageStrategy.uploadFile(any(), any())).thenReturn(expectedPath);
        when(storageProperties.getBasePath()).thenReturn("test");

        // When
        CompletableFuture<String> future = asyncStorageService.asyncUpload(testFile);

        // Then
        String actualPath = future.get();
        assertEquals(expectedPath, actualPath);
        verify(storageStrategy).uploadFile(any(), any());
    }

    @Test
    void asyncDownload_Success() throws Exception {
        // Given
        byte[] expectedContent = "Async content".getBytes(StandardCharsets.UTF_8);
        when(storageStrategy.downloadFile(filePath)).thenReturn(expectedContent);

        // When
        CompletableFuture<byte[]> future = asyncStorageService.asyncDownload(filePath);

        // Then
        byte[] actualContent = future.get();
        assertArrayEquals(expectedContent, actualContent);
        verify(storageStrategy).downloadFile(filePath);
    }

    @Test
    void asyncDelete_Success() throws Exception {
        // Given
        doNothing().when(storageStrategy).deleteFile(filePath);

        // When
        CompletableFuture<Void> future = asyncStorageService.asyncDelete(filePath);

        // Then
        future.get(); // Wait for completion
        verify(storageStrategy).deleteFile(filePath);
    }

    @Test
    void getUploadStatus_Success() {
        // Given
        String taskId = "task123";
        boolean expectedStatus = true;

        // When
        boolean actualStatus = asyncStorageService.getUploadStatus(taskId);

        // Then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void cancelUpload_Success() {
        // Given
        String taskId = "task123";

        // When
        boolean result = asyncStorageService.cancelUpload(taskId);

        // Then
        assertTrue(result);
    }
} 