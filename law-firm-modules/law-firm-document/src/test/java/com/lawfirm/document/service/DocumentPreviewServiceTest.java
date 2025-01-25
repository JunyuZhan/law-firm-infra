package com.lawfirm.document.service;

import com.lawfirm.document.service.impl.DocumentPreviewServiceImpl;
import com.lawfirm.model.document.entity.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentPreviewServiceTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentPreviewServiceImpl previewService;

    private Document testDocument;
    private static final Long TEST_ID = 1L;
    private static final String TEST_FILE_PATH = "src/test/resources/test-files/test.pdf";

    @BeforeEach
    void setUp() {
        testDocument = new Document();
        testDocument.setId(TEST_ID);
        testDocument.setDocumentNumber("DOC-001");
        testDocument.setDocumentName("测试文档");
        testDocument.setFileType("pdf");
        testDocument.setFilePath(TEST_FILE_PATH);

        ReflectionTestUtils.setField(previewService, "previewUrlPrefix", "http://localhost:8080");
        ReflectionTestUtils.setField(previewService, "supportedTypes", "pdf,doc,docx");
    }

    @Test
    void generatePreviewUrl_ShouldReturnCorrectUrl() {
        when(documentService.getById(TEST_ID)).thenReturn(testDocument);

        String url = previewService.generatePreviewUrl(TEST_ID);

        assertEquals("http://localhost:8080/preview/" + TEST_ID, url);
        verify(documentService).getById(TEST_ID);
    }

    @Test
    void generatePreviewUrl_ShouldThrowException_WhenDocumentNotFound() {
        when(documentService.getById(TEST_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> previewService.generatePreviewUrl(TEST_ID));
    }

    @Test
    void isPreviewSupported_ShouldReturnTrue_ForSupportedType() {
        when(documentService.getById(TEST_ID)).thenReturn(testDocument);

        assertTrue(previewService.isPreviewSupported(TEST_ID));
    }

    @Test
    void isPreviewSupported_ShouldReturnFalse_ForUnsupportedType() {
        testDocument.setFileType("xyz");
        when(documentService.getById(TEST_ID)).thenReturn(testDocument);

        assertFalse(previewService.isPreviewSupported(TEST_ID));
    }

    @Test
    void getPreviewContent_ShouldReturnContent_ForPdfFile() throws Exception {
        // 创建测试PDF文件
        Path testFilePath = Paths.get(TEST_FILE_PATH);
        Files.createDirectories(testFilePath.getParent());
        Files.write(testFilePath, "Test PDF Content".getBytes());

        when(documentService.getById(TEST_ID)).thenReturn(testDocument);

        byte[] content = previewService.getPreviewContent(TEST_ID);

        assertNotNull(content);
        assertTrue(content.length > 0);

        // 清理测试文件
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void generateThumbnail_ShouldThrowException_ForUnsupportedType() {
        testDocument.setFileType("xyz");
        when(documentService.getById(TEST_ID)).thenReturn(testDocument);

        assertThrows(UnsupportedOperationException.class,
                () -> previewService.generateThumbnail(TEST_ID, 100, 100));
    }
} 