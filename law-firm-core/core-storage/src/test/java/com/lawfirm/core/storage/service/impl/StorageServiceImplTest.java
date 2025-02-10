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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceImplTest {

    @Mock
    private MinioStorageStrategy storageStrategy;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private StorageServiceImpl storageService;

    private MultipartFile testFile;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            "Hello, World!".getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    void uploadFile_Success() throws IOException {
        // Given
        String expectedPath = "test/test.txt";
        when(storageStrategy.uploadFile(any(), any())).thenReturn(expectedPath);
        when(storageProperties.getBasePath()).thenReturn("test");

        // When
        String actualPath = storageService.uploadFile(testFile);

        // Then
        assertEquals(expectedPath, actualPath);
        verify(storageStrategy).uploadFile(any(), any());
    }

    @Test
    void downloadFile_Success() throws IOException {
        // Given
        String filePath = "test/test.txt";
        byte[] expectedContent = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        when(storageStrategy.downloadFile(filePath)).thenReturn(expectedContent);

        // When
        byte[] actualContent = storageService.downloadFile(filePath);

        // Then
        assertArrayEquals(expectedContent, actualContent);
        verify(storageStrategy).downloadFile(filePath);
    }

    @Test
    void deleteFile_Success() {
        // Given
        String filePath = "test/test.txt";
        doNothing().when(storageStrategy).deleteFile(filePath);

        // When
        storageService.deleteFile(filePath);

        // Then
        verify(storageStrategy).deleteFile(filePath);
    }

    @Test
    void getFileUrl_Success() {
        // Given
        String filePath = "test/test.txt";
        String expectedUrl = "http://localhost:9000/bucket/test/test.txt";
        when(storageStrategy.getFileUrl(filePath)).thenReturn(expectedUrl);

        // When
        String actualUrl = storageService.getFileUrl(filePath);

        // Then
        assertEquals(expectedUrl, actualUrl);
        verify(storageStrategy).getFileUrl(filePath);
    }
} 