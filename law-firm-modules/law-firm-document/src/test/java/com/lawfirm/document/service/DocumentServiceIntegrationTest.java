package com.lawfirm.document.service;

import com.lawfirm.document.DocumentApplication;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DocumentApplication.class)
@Transactional
@Sql("/sql/document-test-data.sql")
class DocumentServiceIntegrationTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentPreviewService previewService;

    private Document testDocument;
    private MockMultipartFile testFile;

    @BeforeEach
    void setUp() {
        testDocument = new Document();
        testDocument.setDocumentNumber("DOC-TEST-001");
        testDocument.setDocumentName("测试文档");
        testDocument.setDocumentType("CONTRACT");
        
        testFile = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            "Test PDF Content".getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    void upload_ShouldCreateNewDocument() {
        DocumentVO result = documentService.upload(testFile, testDocument, "admin");

        assertNotNull(result);
        assertEquals(testDocument.getDocumentName(), result.getDocumentName());
        assertEquals("1.0", result.getVersion());
        assertEquals(0, result.getStatus());
        assertNotNull(result.getUploadTime());
        assertEquals("admin", result.getUploadBy());
    }

    @Test
    void batchUpload_ShouldCreateMultipleDocuments() {
        List<MockMultipartFile> files = Arrays.asList(
            testFile,
            new MockMultipartFile("file2", "test2.pdf", "application/pdf", "Test Content 2".getBytes())
        );

        Document doc2 = new Document();
        doc2.setDocumentNumber("DOC-TEST-002");
        doc2.setDocumentName("测试文档2");
        doc2.setDocumentType("CONTRACT");

        List<Document> documents = Arrays.asList(testDocument, doc2);

        List<DocumentVO> results = documentService.batchUpload(files, documents, "admin");

        assertEquals(2, results.size());
        results.forEach(result -> {
            assertNotNull(result.getDocumentNumber());
            assertEquals("1.0", result.getVersion());
            assertEquals(0, result.getStatus());
            assertNotNull(result.getUploadTime());
            assertEquals("admin", result.getUploadBy());
        });
    }

    @Test
    void update_ShouldCreateNewVersion() {
        // 先上传一个文档
        DocumentVO original = documentService.upload(testFile, testDocument, "admin");

        // 更新文档
        testDocument.setDocumentName("测试文档-更新");
        MockMultipartFile newFile = new MockMultipartFile(
            "file",
            "test-v2.pdf",
            "application/pdf",
            "Updated Content".getBytes()
        );

        DocumentVO updated = documentService.update(original.getId(), newFile, testDocument, "admin");

        assertNotNull(updated);
        assertEquals("测试文档-更新", updated.getDocumentName());
        assertEquals("2.0", updated.getVersion());
        assertNotNull(updated.getUpdateTime());
        assertEquals("admin", updated.getUpdateBy());
    }

    @Test
    void audit_ShouldUpdateDocumentStatus() {
        // 先上传一个文档
        DocumentVO original = documentService.upload(testFile, testDocument, "admin");

        // 审核文档
        DocumentVO audited = documentService.audit(original.getId(), 1, "通过", "auditor");

        assertNotNull(audited);
        assertEquals(1, audited.getStatus());
        assertEquals("通过", audited.getAuditComment());
        assertEquals("auditor", audited.getAuditBy());
        assertNotNull(audited.getAuditTime());
    }

    @Test
    void getVersionHistory_ShouldReturnAllVersions() {
        // 先上传一个文档
        DocumentVO v1 = documentService.upload(testFile, testDocument, "admin");

        // 更新几个版本
        testDocument.setDocumentName("测试文档-v2");
        DocumentVO v2 = documentService.update(v1.getId(), testFile, testDocument, "admin");

        testDocument.setDocumentName("测试文档-v3");
        DocumentVO v3 = documentService.update(v2.getId(), testFile, testDocument, "admin");

        List<DocumentVO> versions = documentService.getVersionHistory(v1.getId());

        assertEquals(3, versions.size());
        assertEquals("3.0", versions.get(0).getVersion());
        assertEquals("2.0", versions.get(1).getVersion());
        assertEquals("1.0", versions.get(2).getVersion());
    }

    @Test
    void exportToExcel_ShouldGenerateExcelFile() {
        // 先创建一些测试数据
        documentService.upload(testFile, testDocument, "admin");

        DocumentQuery query = new DocumentQuery();
        query.setDocumentType("CONTRACT");

        byte[] excelData = documentService.exportToExcel(query);

        assertNotNull(excelData);
        assertTrue(excelData.length > 0);
    }

    @Test
    void preview_ShouldGeneratePreviewUrl() {
        // 先上传一个文档
        DocumentVO uploaded = documentService.upload(testFile, testDocument, "admin");

        String previewUrl = previewService.generatePreviewUrl(uploaded.getId());
        assertNotNull(previewUrl);
        assertTrue(previewUrl.contains("/preview/"));

        boolean supported = previewService.isPreviewSupported(uploaded.getId());
        assertTrue(supported);
    }
} 