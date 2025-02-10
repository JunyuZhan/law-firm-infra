package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档变更记录实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_change_log")
public class DocumentChangeLog extends AuditableDocumentEntity<DocumentChangeLog> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;          // 文档ID

    @Size(max = 50, message = "变更类型长度不能超过50个字符")
    @Column(length = 50)
    private String changeType;        // 变更类型（新建、修改、删除等）

    @Column(columnDefinition = "TEXT")
    private String changeContent;     // 变更内容（JSON格式记录具体变更）

    @Size(max = 50, message = "操作人长度不能超过50个字符")
    @Column(length = 50)
    private String operator;          // 操作人

    @Column
    private LocalDateTime changeTime; // 变更时间

    @Column
    private Integer version;          // 版本号

    @Size(max = 500, message = "变更说明长度不能超过500个字符")
    @Column(length = 500)
    private String changeDescription; // 变更说明

    @Size(max = 100, message = "客户端信息长度不能超过100个字符")
    @Column(length = 100)
    private String clientInfo;        // 客户端信息

    @Column
    private String ipAddress;         // IP地址

    @Column
    private String sessionId;         // 会话ID
} 