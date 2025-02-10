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
 * 文档审批实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_approval")
public class DocumentApproval extends AuditableDocumentEntity<DocumentApproval> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @NotNull(message = "审批类型不能为空")
    @Column(nullable = false, length = 50)
    private String approvalType;  // 审批类型

    @Column(length = 50)
    private String approvalNode;  // 审批节点

    @Column(length = 100)
    private String approver;      // 审批人

    @Column(length = 500)
    private String approvalOpinion; // 审批意见

    @Column
    private LocalDateTime approvalTime; // 审批时间

    @Column(length = 20)
    private String approvalResult;  // 审批结果（同意/拒绝）

    @Column
    private Integer approvalOrder;  // 审批顺序

    @Column(length = 100)
    private String nextApprover;   // 下一审批人

    @Column
    private LocalDateTime deadline; // 审批截止时间

    @Column
    private Boolean isUrged = false; // 是否已催办
} 