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
class PreviewServiceImplTest {

    @Mock
    private MinioStorageStrategy storageStrategy;

    @Mock
    private StorageProperties storageProperties;

    @InjectMocks
    private PreviewServiceImpl previewService;

    private MultipartFile testFile;
    private String filePath;

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile(
            "test.pdf",
            "test.pdf",
            "application/pdf",
            "PDF content".getBytes(StandardCharsets.UTF_8)
        );
        filePath = "test/test.pdf";
    }

    @Test
    void generatePreview_Success() throws IOException {
        // Given
        String expectedPreviewPath = "preview/test.pdf";
        when(storageStrategy.uploadFile(any(), any())).thenReturn(expectedPreviewPath);
        when(storageProperties.getPreviewPath()).thenReturn("preview");

        // When
        String actualPreviewPath = previewService.generatePreview(testFile);

        // Then
        assertEquals(expectedPreviewPath, actualPreviewPath);
        verify(storageStrategy).uploadFile(any(), any());
    }

    @Test
    void getPreviewUrl_Success() {
        // Given
        String expectedUrl = "http://localhost:9000/bucket/preview/test.pdf";
        when(storageStrategy.getFileUrl(any())).thenReturn(expectedUrl);

        // When
        String actualUrl = previewService.getPreviewUrl(filePath);

        // Then
        assertEquals(expectedUrl, actualUrl);
        verify(storageStrategy).getFileUrl(any());
    }

    @Test
    void deletePreview_Success() {
        // Given
        doNothing().when(storageStrategy).deleteFile(any());

        // When
        previewService.deletePreview(filePath);

        // Then
        verify(storageStrategy).deleteFile(any());
    }

    @Test
    void isPreviewable_Success() {
        // Given
        String pdfFile = "test.pdf";
        String txtFile = "test.txt";
        String imgFile = "test.jpg";
        String unknownFile = "test.xyz";

        // When & Then
        assertTrue(previewService.isPreviewable(pdfFile));
        assertTrue(previewService.isPreviewable(txtFile));
        assertTrue(previewService.isPreviewable(imgFile));
        assertFalse(previewService.isPreviewable(unknownFile));
    }
} 