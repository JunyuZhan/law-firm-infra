package com.lawfirm.model.document.entity;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void testDocumentBuilder() {
        Document document = new Document()
            .setDocumentNumber("DOC-2024-001")
            .setDocumentName("测试文档")
            .setDocumentType(DocumentTypeEnum.CONTRACT)
            .setSecurityLevel(DocumentSecurityLevelEnum.NORMAL)
            .setDocumentStatus(DocumentStatusEnum.DRAFT)
            .setCategoryId(1L)
            .setLawFirmId(1L);

        assertNotNull(document);
        assertEquals("DOC-2024-001", document.getDocumentNumber());
        assertEquals("测试文档", document.getDocumentName());
        assertEquals(DocumentTypeEnum.CONTRACT, document.getDocumentType());
        assertEquals(DocumentSecurityLevelEnum.NORMAL, document.getSecurityLevel());
        assertEquals(DocumentStatusEnum.DRAFT, document.getDocumentStatus());
        assertEquals(1L, document.getCategoryId());
        assertEquals(1L, document.getLawFirmId());
    }

    @Test
    void testSetStatus() {
        Document document = new Document();
        
        // 测试废弃状态
        document.setDocumentStatus(DocumentStatusEnum.DEPRECATED);
        document.setStatus(StatusEnum.ENABLED);
        assertEquals(StatusEnum.DISABLED, document.getStatus());

        // 测试正常状态
        document.setDocumentStatus(DocumentStatusEnum.NORMAL);
        document.setStatus(StatusEnum.ENABLED);
        assertEquals(StatusEnum.ENABLED, document.getStatus());
    }

    @Test
    void testChainedSetters() {
        Document document = new Document()
            .setId(1L)
            .setRemark("测试备注")
            .setVersion(1);

        assertEquals(1L, document.getId());
        assertEquals("测试备注", document.getRemark());
        assertEquals(1, document.getVersion());
    }
} 