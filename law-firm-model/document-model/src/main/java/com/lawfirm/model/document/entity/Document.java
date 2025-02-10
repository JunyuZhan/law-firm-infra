package com.lawfirm.model.document.entity;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import com.lawfirm.model.document.enums.OcrStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document")
public class Document extends AuditableDocumentEntity<Document> {

    @NotNull(message = "文档编号不能为空")
    @Column(nullable = false, length = 50, unique = true)
    private String documentNumber;  // 文档编号

    @NotNull(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String documentName;    // 文档名称

    @NotNull(message = "文档类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentTypeEnum documentType;  // 文档类型

    @NotNull(message = "文档密级不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentSecurityLevelEnum securityLevel;  // 文档密级

    @NotNull(message = "文档状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentStatusEnum documentStatus = DocumentStatusEnum.DRAFT;  // 文档状态

    @NotNull(message = "分类ID不能为空")
    @Column(nullable = false)
    private Long categoryId;      // 分类ID

    @Column(length = 500)
    private String folder;        // 所属文件夹

    @Column(length = 1000)
    private String tags;          // 标签（JSON数组）

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;      // 律所ID

    @Column
    private Long caseId;         // 案件ID

    @Column
    private Long clientId;       // 客户ID

    @Column
    private Long contractId;     // 合同ID

    @Column(length = 50)
    private String departmentId; // 所属部门

    @Column(length = 500)
    private String keywords;     // 关键词

    @Column(length = 2000)
    private String summary;      // 文档摘要

    @Column(length = 20)
    private String currentVersion; // 当前版本号

    @Column
    private LocalDateTime retentionTime; // 保留期限

    @Column(length = 500)
    private String relatedDocuments;  // 关联文档IDs（JSON数组）

    @Column(length = 100)
    private String templateId;    // 模板ID（如果基于模板创建）

    @Column(length = 100)
    private String language;      // 文档语言

    @Column
    private Boolean needArchive = false;  // 是否需要归档

    @Column
    private LocalDateTime archiveTime;    // 归档时间

    @OneToOne(mappedBy = "documentId", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentStorage storage;  // 文档存储信息

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OcrStatusEnum ocrStatus = OcrStatusEnum.NOT_STARTED;  // OCR状态

    @Column
    private LocalDateTime ocrTime;        // OCR完成时间

    @Column(length = 500)
    private String ocrErrorMessage;       // OCR错误信息

    @Override
    public Document setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public Document setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public void setStatus(StatusEnum status) {
        if (DocumentStatusEnum.DEPRECATED.equals(documentStatus)) {
            super.setStatus(StatusEnum.DISABLED);
        } else {
            super.setStatus(StatusEnum.ENABLED);
        }
    }

    @Override
    public Document setVersion(Integer version) {
        super.setVersion(version);
        return this;
    }
} 