package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void testSaveDocument() {
        Document document = new Document()
            .setDocumentNumber("TEST-2024-001")
            .setDocumentName("测试文档")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);
        assertNotNull(savedDocument.getId());
        assertEquals("TEST-2024-001", savedDocument.getDocumentNumber());
    }

    @Test
    void testFindByDocumentNumber() {
        // 准备测试数据
        Document document = new Document()
            .setDocumentNumber("TEST-2024-002")
            .setDocumentName("测试文档2")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        documentRepository.save(document);

        // 测试查询
        Document found = documentRepository.findByDocumentNumber("TEST-2024-002");
        assertNotNull(found);
        assertEquals("测试文档2", found.getDocumentName());
    }

    @Test
    void testUpdateDocument() {
        // 准备测试数据
        Document document = new Document()
            .setDocumentNumber("TEST-2024-003")
            .setDocumentName("原始文档")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);

        // 更新文档
        savedDocument.setDocumentName("更新后的文档");
        Document updatedDocument = documentRepository.save(savedDocument);

        assertEquals("更新后的文档", updatedDocument.getDocumentName());
        assertEquals(document.getId(), updatedDocument.getId());
    }

    @Test
    void testDeleteDocument() {
        // 准备测试数据
        Document document = new Document()
            .setDocumentNumber("TEST-2024-004")
            .setDocumentName("待删除文档")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);
        
        // 删除文档
        documentRepository.delete(savedDocument);
        
        // 验证删除
        Document found = documentRepository.findByDocumentNumber("TEST-2024-004");
        assertNull(found);
    }
} 