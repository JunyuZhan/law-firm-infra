package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import com.lawfirm.model.document.enums.DocumentPermissionEnum;
import com.lawfirm.model.document.enums.DocumentPermissionTargetEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档权限实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_permission")
public class DocumentPermission extends AuditableDocumentEntity<DocumentPermission> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @NotNull(message = "目标类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentPermissionTargetEnum targetType;    // 目标类型

    @NotNull(message = "目标ID不能为空")
    @Column(nullable = false)
    private Long targetId;        // 目标ID

    @NotNull(message = "权限不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentPermissionEnum permission;    // 权限

    @Column
    private LocalDateTime effectiveTime;  // 权限生效时间

    @Column
    private LocalDateTime expiryTime;     // 权限过期时间

    @Column(length = 100)
    private String grantedBy;             // 授权人

    @Column(length = 500)
    private String grantReason;           // 授权原因

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;        // 备注
} 