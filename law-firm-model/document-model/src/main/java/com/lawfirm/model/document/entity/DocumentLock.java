package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import com.lawfirm.model.document.enums.DocumentLockScopeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档锁定实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_lock")
public class DocumentLock extends AuditableDocumentEntity<DocumentLock> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @NotNull(message = "用户ID不能为空")
    @Column(nullable = false)
    private Long userId;          // 锁定用户ID

    @Size(max = 50, message = "用户名长度不能超过50个字符")
    @Column(length = 50)
    private String userName;      // 锁定用户名称

    @Size(max = 100, message = "会话ID长度不能超过100个字符")
    @Column(length = 100)
    private String sessionId;     // 会话ID

    @Size(max = 500, message = "客户端信息长度不能超过500个字符")
    @Column(length = 500)
    private String clientInfo;    // 客户端信息（浏览器、操作系统等）

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private DocumentLockScopeEnum lockScope = DocumentLockScopeEnum.FULL;  // 锁定范围

    @Column
    private LocalDateTime lockTime;    // 锁定时间

    @Column
    private LocalDateTime expireTime;  // 过期时间
} 