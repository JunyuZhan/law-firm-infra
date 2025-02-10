package com.lawfirm.document.service;

import com.lawfirm.document.dto.request.DocumentVersionAddRequest;
import com.lawfirm.document.dto.response.DocumentVersionResponse;
import com.lawfirm.document.entity.Document;
import com.lawfirm.document.entity.DocumentVersion;
import com.lawfirm.document.service.impl.DocumentVersionServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文档版本服务测试类
 */
@SpringBootTest
@Transactional
public class DocumentVersionServiceTest {

    @Autowired
    private IDocumentVersionService documentVersionService;

    @Autowired
    private IDocumentService documentService;

    @Test
    public void testAddVersion() {
        // 准备测试数据
        Document document = createTestDocument();
        documentService.save(document);

        DocumentVersionAddRequest request = new DocumentVersionAddRequest();
        request.setDocId(document.getId());
        request.setFileSize(1024L);
        request.setStoragePath("/test/path");
        request.setChangeDesc("测试版本");

        // 执行添加
        Long versionId = documentVersionService.addVersion(request);

        // 验证结果
        assertNotNull(versionId);
        DocumentVersion version = documentVersionService.getById(versionId);
        assertEquals(request.getDocId(), version.getDocId());
        assertEquals(request.getFileSize(), version.getFileSize());
        assertEquals(request.getStoragePath(), version.getStoragePath());
        assertEquals(request.getChangeDesc(), version.getChangeDesc());
        assertEquals(1, version.getVersionNo());
    }

    @Test
    public void testGetVersionsByDocId() {
        // 准备测试数据
        Document document = createTestDocument();
        documentService.save(document);

        DocumentVersionAddRequest request1 = new DocumentVersionAddRequest();
        request1.setDocId(document.getId());
        request1.setFileSize(1024L);
        request1.setStoragePath("/test/path1");
        request1.setChangeDesc("测试版本1");
        documentVersionService.addVersion(request1);

        DocumentVersionAddRequest request2 = new DocumentVersionAddRequest();
        request2.setDocId(document.getId());
        request2.setFileSize(2048L);
        request2.setStoragePath("/test/path2");
        request2.setChangeDesc("测试版本2");
        documentVersionService.addVersion(request2);

        // 执行查询
        List<DocumentVersionResponse> versions = documentVersionService.getVersionsByDocId(document.getId());

        // 验证结果
        assertEquals(2, versions.size());
        assertEquals(2, versions.get(0).getVersionNo());
        assertEquals(1, versions.get(1).getVersionNo());
    }

    @Test
    public void testRollbackToVersion() {
        // 准备测试数据
        Document document = createTestDocument();
        documentService.save(document);

        DocumentVersionAddRequest request1 = new DocumentVersionAddRequest();
        request1.setDocId(document.getId());
        request1.setFileSize(1024L);
        request1.setStoragePath("/test/path1");
        request1.setChangeDesc("测试版本1");
        documentVersionService.addVersion(request1);

        DocumentVersionAddRequest request2 = new DocumentVersionAddRequest();
        request2.setDocId(document.getId());
        request2.setFileSize(2048L);
        request2.setStoragePath("/test/path2");
        request2.setChangeDesc("测试版本2");
        documentVersionService.addVersion(request2);

        // 执行回滚
        documentVersionService.rollbackToVersion(document.getId(), 1);

        // 验证结果
        Document updatedDocument = documentService.getById(document.getId());
        assertEquals(1, updatedDocument.getVersion());
        assertEquals(1024L, updatedDocument.getFileSize());
        assertEquals("/test/path1", updatedDocument.getStoragePath());
    }

    @Test
    public void testAddVersionWithNonExistentDocument() {
        // 准备测试数据
        DocumentVersionAddRequest request = new DocumentVersionAddRequest();
        request.setDocId(999L);
        request.setFileSize(1024L);
        request.setStoragePath("/test/path");
        request.setChangeDesc("测试版本");

        // 验证异常
        assertThrows(BusinessException.class, () -> documentVersionService.addVersion(request));
    }

    private Document createTestDocument() {
        Document document = new Document();
        document.setDocNo("TEST-001");
        document.setDocName("测试文档");
        document.setDocType(1);
        document.setFileType("pdf");
        document.setFileSize(1024L);
        document.setStoragePath("/test/path");
        document.setStatus(1);
        return document;
    }
} 