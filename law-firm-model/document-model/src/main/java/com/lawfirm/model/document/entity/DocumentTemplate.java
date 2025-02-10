package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档模板实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_template")
public class DocumentTemplate extends AuditableDocumentEntity<DocumentTemplate> {

    @NotNull(message = "模板编号不能为空")
    @Column(nullable = false, length = 50, unique = true)
    private String templateCode;   // 模板编号

    @NotNull(message = "模板名称不能为空")
    @Size(max = 200, message = "模板名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String templateName;   // 模板名称

    @Column(length = 2000)
    private String description;    // 模板描述

    @Column(length = 1000)
    private String placeholders;   // 占位符定义（JSON格式）

    @Column(length = 100)
    private String category;       // 模板分类

    @NotNull(message = "律所ID不能为空")
    @Column(nullable = false)
    private Long lawFirmId;       // 律所ID

    @Column(length = 50)
    private String departmentId;   // 所属部门

    @Column
    private Boolean isPublic = false;  // 是否公开

    @Column
    private Integer version = 1;   // 版本号

    @OneToOne(mappedBy = "documentId", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentStorage storage;  // 模板文件存储信息
} 