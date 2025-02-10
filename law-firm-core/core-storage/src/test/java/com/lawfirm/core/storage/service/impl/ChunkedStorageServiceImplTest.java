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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChunkedStorageServiceImplTest {

    @Mock
    private MinioStorageStrategy storageStrategy;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private ChunkedStorageServiceImpl chunkedStorageService;

    private MultipartFile testChunk;
    private String uploadId;
    private String fileName;

    @BeforeEach
    void setUp() {
        testChunk = new MockMultipartFile(
            "chunk",
            "test.txt",
            "text/plain",
            "Chunk content".getBytes(StandardCharsets.UTF_8)
        );
        uploadId = UUID.randomUUID().toString();
        fileName = "test.txt";
    }

    @Test
    void initUpload_Success() {
        // Given
        when(storageStrategy.initMultipartUpload(any())).thenReturn(uploadId);
        when(storageProperties.getBasePath()).thenReturn("test");

        // When
        String actualUploadId = chunkedStorageService.initUpload(fileName);

        // Then
        assertEquals(uploadId, actualUploadId);
        verify(storageStrategy).initMultipartUpload(any());
    }

    @Test
    void uploadChunk_Success() throws IOException {
        // Given
        int chunkNumber = 1;
        String etag = "etag123";
        when(storageStrategy.uploadPart(any(), anyInt(), any())).thenReturn(etag);

        // When
        String actualEtag = chunkedStorageService.uploadChunk(uploadId, fileName, chunkNumber, testChunk);

        // Then
        assertEquals(etag, actualEtag);
        verify(storageStrategy).uploadPart(any(), eq(chunkNumber), any());
    }

    @Test
    void completeUpload_Success() {
        // Given
        String expectedPath = "test/test.txt";
        when(storageStrategy.completeMultipartUpload(any(), any())).thenReturn(expectedPath);
        when(storageProperties.getBasePath()).thenReturn("test");

        // When
        String actualPath = chunkedStorageService.completeUpload(uploadId, fileName);

        // Then
        assertEquals(expectedPath, actualPath);
        verify(storageStrategy).completeMultipartUpload(any(), any());
    }

    @Test
    void abortUpload_Success() {
        // Given
        doNothing().when(storageStrategy).abortMultipartUpload(any());

        // When
        chunkedStorageService.abortUpload(uploadId, fileName);

        // Then
        verify(storageStrategy).abortMultipartUpload(any());
    }
} 