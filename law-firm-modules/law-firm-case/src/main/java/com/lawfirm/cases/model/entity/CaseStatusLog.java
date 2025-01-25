package com.lawfirm.cases.model.entity;

import com.lawfirm.cases.model.enums.CaseStatus;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@Data
@Entity
@Table(name = "case_status_log")
@EqualsAndHashCode(callSuper = true)
public class CaseStatusLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("原状态")
    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private CaseStatus fromStatus;

    @Comment("新状态")
    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private CaseStatus toStatus;

    @Comment("操作人")
    @Column(nullable = false, length = 64)
    private String operator;

    @Comment("变更原因")
    @Column(length = 512)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 