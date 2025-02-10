package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.entity.DocumentStorage;
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
class DocumentStorageTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentStorageRepository documentStorageRepository;

    @Test
    void testSaveDocumentStorage() {
        // 创建文档
        Document document = new Document()
            .setDocumentNumber("TEST-2024-005")
            .setDocumentName("存储测试文档")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);

        // 创建文档存储
        DocumentStorage storage = new DocumentStorage()
            .setDocumentId(savedDocument.getId())
            .setFileName("test.pdf")
            .setFileType("pdf")
            .setFileSize(1024L)
            .setStoragePath("/storage/test.pdf")
            .setMd5("123456789abcdef")
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        DocumentStorage savedStorage = documentStorageRepository.save(storage);

        assertNotNull(savedStorage.getId());
        assertEquals("test.pdf", savedStorage.getFileName());
        assertEquals(1024L, savedStorage.getFileSize());
    }

    @Test
    void testFindByDocumentId() {
        // 创建文档
        Document document = new Document()
            .setDocumentNumber("TEST-2024-006")
            .setDocumentName("存储查询测试")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);

        // 创建文档存储
        DocumentStorage storage = new DocumentStorage()
            .setDocumentId(savedDocument.getId())
            .setFileName("query.pdf")
            .setFileType("pdf")
            .setFileSize(2048L)
            .setStoragePath("/storage/query.pdf")
            .setMd5("abcdef123456789")
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        documentStorageRepository.save(storage);

        // 测试查询
        DocumentStorage found = documentStorageRepository.findByDocumentId(savedDocument.getId());
        assertNotNull(found);
        assertEquals("query.pdf", found.getFileName());
        assertEquals(2048L, found.getFileSize());
    }

    @Test
    void testUpdateStorage() {
        // 创建文档
        Document document = new Document()
            .setDocumentNumber("TEST-2024-007")
            .setDocumentName("存储更新测试")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        Document savedDocument = documentRepository.save(document);

        // 创建文档存储
        DocumentStorage storage = new DocumentStorage()
            .setDocumentId(savedDocument.getId())
            .setFileName("original.pdf")
            .setFileType("pdf")
            .setFileSize(1024L)
            .setStoragePath("/storage/original.pdf")
            .setMd5("original123")
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());

        DocumentStorage savedStorage = documentStorageRepository.save(storage);

        // 更新存储信息
        savedStorage.setFileName("updated.pdf")
            .setFileSize(2048L)
            .setStoragePath("/storage/updated.pdf")
            .setMd5("updated123");

        DocumentStorage updatedStorage = documentStorageRepository.save(savedStorage);

        assertEquals("updated.pdf", updatedStorage.getFileName());
        assertEquals(2048L, updatedStorage.getFileSize());
        assertEquals("updated123", updatedStorage.getMd5());
    }
} 