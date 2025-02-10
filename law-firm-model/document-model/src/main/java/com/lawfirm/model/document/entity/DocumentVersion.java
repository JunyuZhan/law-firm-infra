package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档版本实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_version")
public class DocumentVersion extends AuditableDocumentEntity<DocumentVersion> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @Column(nullable = false)
    private Integer version;      // 版本号

    @Column(nullable = false)
    private Long storageId;       // 存储ID

    @Column
    private Long fileSize;        // 文件大小

    @Size(max = 200, message = "文件名长度不能超过200个字符")
    @Column(length = 200)
    private String fileName;      // 文件名

    @Size(max = 500, message = "变更说明长度不能超过500个字符")
    @Column(length = 500)
    private String changeLog;     // 变更说明

    @Size(max = 500, message = "文件路径长度不能超过500个字符")
    @Column(length = 500)
    private String filePath;      // 该版本的文件存储路径

    @Size(max = 100, message = "文件Hash长度不能超过100个字符")
    @Column(length = 100)
    private String fileHash;      // 文件Hash值

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;        // 备注信息
} 