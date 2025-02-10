package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档存储实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_storage")
public class DocumentStorage extends AuditableDocumentEntity<DocumentStorage> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @Size(max = 500, message = "文件路径长度不能超过500个字符")
    @Column(length = 500)
    private String filePath;       // 文件路径

    @Column
    private Long fileSize;         // 文件大小

    @Size(max = 50, message = "文件类型长度不能超过50个字符")
    @Column(length = 50)
    private String fileType;       // 文件类型

    @Size(max = 100, message = "文件Hash长度不能超过100个字符")
    @Column(length = 100)
    private String fileHash;       // 文件Hash

    @Column
    private Boolean isEncrypted = false; // 是否加密

    @Column(length = 100)
    private String storageType;    // 存储类型（本地/MinIO/OSS等）

    @Column(length = 100)
    private String bucketName;     // 存储桶名称

    @Column(length = 500)
    private String objectKey;      // 对象键名

    @Column(length = 100)
    private String contentType;    // 内容类型
} 