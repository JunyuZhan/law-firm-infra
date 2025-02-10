package com.lawfirm.model.document.entity.base;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 可审计的文档基础实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class AuditableDocumentEntity<T extends AuditableDocumentEntity<T>> extends ModelBaseEntity<T> {

    @Column(length = 100)
    private String author;        // 作者/创建人

    @Column(length = 100)
    private String reviewer;      // 审核人

    @Column
    private LocalDateTime reviewTime;  // 审核时间

    @Column(length = 100)
    private String approver;      // 批准人

    @Column
    private LocalDateTime approveTime; // 批准时间

    @Column(length = 500)
    private String reviewComment;  // 审核意见

    @Column(length = 500)
    private String approveComment; // 批准意见
} 