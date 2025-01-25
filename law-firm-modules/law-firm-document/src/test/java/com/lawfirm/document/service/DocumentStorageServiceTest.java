package com.lawfirm.document.service;

import com.lawfirm.common.storage.LocalStorageService;
import com.lawfirm.document.constant.DocumentConstant;
import com.lawfirm.document.exception.DocumentException;
import com.lawfirm.document.service.impl.LocalDocumentStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentStorageServiceTest {

    @Mock
    private LocalStorageService storageService;

    @InjectMocks
    private LocalDocumentStorageServiceImpl documentStorageService;

    private static final String TEST_ROOT_PATH = "/test/storage";
    private static final String TEST_DOC_NUMBER = "DOC-001";
    private static final String TEST_VERSION = "1.0";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(documentStorageService, "rootPath", TEST_ROOT_PATH);
    }

    @Test
    void store_ShouldStoreFile_WhenValidFile() throws IOException {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            "test content".getBytes()
        );
        String expectedPath = TEST_DOC_NUMBER + "/DOC-001_1.0.pdf";
        
        when(storageService.store(any(), eq(expectedPath))).thenReturn(expectedPath);

        // 执行测试
        String result = documentStorageService.store(file, TEST_DOC_NUMBER, TEST_VERSION);

        // 验证结果
        assertEquals(expectedPath, result);
        verify(storageService).store(any(), eq(expectedPath));
    }

    @Test
    void store_ShouldThrowException_WhenFileSizeExceeded() {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            new byte[DocumentConstant.MAX_FILE_SIZE + 1]
        );

        // 执行测试并验证异常
        DocumentException exception = assertThrows(DocumentException.class,
            () -> documentStorageService.store(file, TEST_DOC_NUMBER, TEST_VERSION));
        
        assertEquals(DocumentException.ERROR_FILE_SIZE_EXCEEDED, exception.getCode());
    }

    @Test
    void store_ShouldThrowException_WhenUnsupportedFileType() {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.xyz",
            "application/octet-stream",
            "test content".getBytes()
        );

        // 执行测试并验证异常
        DocumentException exception = assertThrows(DocumentException.class,
            () -> documentStorageService.store(file, TEST_DOC_NUMBER, TEST_VERSION));
        
        assertEquals(DocumentException.ERROR_FILE_TYPE_NOT_SUPPORTED, exception.getCode());
    }

    @Test
    void delete_ShouldDeleteFile() throws IOException {
        // 准备测试数据
        String filePath = "test/path/file.pdf";
        
        // 执行测试
        documentStorageService.delete(filePath);

        // 验证结果
        verify(storageService).delete(filePath);
    }

    @Test
    void deleteAllVersions_ShouldDeleteDirectory() throws IOException {
        // 执行测试
        documentStorageService.deleteAllVersions(TEST_DOC_NUMBER);

        // 验证结果
        verify(storageService).deleteDirectory(TEST_DOC_NUMBER);
    }

    @Test
    void getFilePath_ShouldReturnCorrectPath() {
        // 执行测试
        Path result = documentStorageService.getFilePath(TEST_DOC_NUMBER, TEST_VERSION);

        // 验证结果
        Path expected = Paths.get(TEST_ROOT_PATH, TEST_DOC_NUMBER, TEST_DOC_NUMBER + "_" + TEST_VERSION);
        assertEquals(expected, result);
    }

    @Test
    void exists_ShouldCheckFileExistence() {
        // 准备测试数据
        String filePath = "test/path/file.pdf";
        when(storageService.exists(filePath)).thenReturn(true);

        // 执行测试
        boolean result = documentStorageService.exists(filePath);

        // 验证结果
        assertTrue(result);
        verify(storageService).exists(filePath);
    }

    @Test
    void getSize_ShouldReturnFileSize() throws IOException {
        // 准备测试数据
        String filePath = "test/path/file.pdf";
        long expectedSize = 1024L;
        when(storageService.getSize(filePath)).thenReturn(expectedSize);

        // 执行测试
        long result = documentStorageService.getSize(filePath);

        // 验证结果
        assertEquals(expectedSize, result);
        verify(storageService).getSize(filePath);
    }
} 