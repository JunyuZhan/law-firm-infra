package com.lawfirm.document.service.impl;

import com.lawfirm.document.BaseDocumentTest;
import com.lawfirm.document.service.DocumentStorageService;
import com.lawfirm.document.service.IDocumentConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DocumentConversionServiceImplTest extends BaseDocumentTest {

    @Autowired
    private IDocumentConversionService conversionService;

    @MockBean
    private DocumentStorageService storageService;

    @BeforeEach
    void setUp() {
        // 设置测试环境
        new File(TEST_FILE_PATH).mkdirs();
        new File(TEST_TEMP_PATH).mkdirs();
    }

    @Test
    void testConvertDocToPdf() throws IOException {
        // 准备测试数据
        String sourceFile = getTestFilePath("test.doc");
        String targetFile = getTempFilePath("test.pdf");

        // Mock存储服务
        when(storageService.getPhysicalPath(any())).thenReturn(sourceFile);
        when(storageService.createTempFile(any(), any())).thenReturn(new File(targetFile));

        // 执行转换
        File result = conversionService.convertToPdf(sourceFile);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(targetFile, result.getPath());
    }

    @Test
    void testConvertDocxToPdf() throws IOException {
        // 准备测试数据
        String sourceFile = getTestFilePath("test.docx");
        String targetFile = getTempFilePath("test.pdf");

        // Mock存储服务
        when(storageService.getPhysicalPath(any())).thenReturn(sourceFile);
        when(storageService.createTempFile(any(), any())).thenReturn(new File(targetFile));

        // 执行转换
        File result = conversionService.convertToPdf(sourceFile);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(targetFile, result.getPath());
    }

    @Test
    void testConvertPdfToHtml() throws IOException {
        // 准备测试数据
        String sourceFile = getTestFilePath("test.pdf");
        String targetFile = getTempFilePath("test.html");

        // Mock存储服务
        when(storageService.getPhysicalPath(any())).thenReturn(sourceFile);
        when(storageService.createTempFile(any(), any())).thenReturn(new File(targetFile));

        // 执行转换
        File result = conversionService.convertToHtml(sourceFile);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(targetFile, result.getPath());
    }
} 