package com.lawfirm.document.service;

import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文档存储服务测试类
 */
@SpringBootTest
@Transactional
public class DocumentStorageServiceTest {

    @Autowired
    private IDocumentStorageService documentStorageService;

    @Test
    public void testUploadAndDownloadFile() throws IOException {
        // 准备测试数据
        String content = "测试文件内容";
        MockMultipartFile file = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            content.getBytes(StandardCharsets.UTF_8)
        );

        // 执行上传
        String path = documentStorageService.uploadFile(file, "test");
        assertNotNull(path);

        // 执行下载
        try (InputStream is = documentStorageService.downloadFile(path)) {
            String downloadedContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(content, downloadedContent);
        }

        // 执行删除
        documentStorageService.deleteFile(path);
        assertThrows(BusinessException.class, () -> documentStorageService.downloadFile(path));
    }

    @Test
    public void testUploadInvalidFile() {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
            "test.txt",
            "test.txt",
            "text/plain",
            new byte[0]
        );

        // 验证空文件上传
        assertThrows(BusinessException.class, () -> documentStorageService.uploadFile(file, "test"));
    }

    @Test
    public void testDownloadNonExistentFile() {
        // 验证下载不存在的文件
        assertThrows(BusinessException.class, () -> documentStorageService.downloadFile("non-existent-file"));
    }

    @Test
    public void testDeleteNonExistentFile() {
        // 验证删除不存在的文件不会抛出异常
        assertDoesNotThrow(() -> documentStorageService.deleteFile("non-existent-file"));
    }

    @Test
    public void testConvertFormat() {
        // 验证格式转换功能（当前未实现）
        assertThrows(BusinessException.class, () -> documentStorageService.convertFormat("test.txt", "pdf"));
    }
} 