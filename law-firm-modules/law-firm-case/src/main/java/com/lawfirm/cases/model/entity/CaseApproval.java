package com.lawfirm.cases.model.entity;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "case_approval")
@EqualsAndHashCode(callSuper = true)
public class CaseApproval extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("审批类型(CASE_CREATE-立案审批/CASE_CLOSE-结案审批/FEE_ADJUSTMENT-收费调整)")
    @Column(nullable = false, length = 32)
    private String approvalType;

    @Comment("申请人")
    @Column(nullable = false, length = 64)
    private String applicant;

    @Comment("申请时间")
    @Column(nullable = false)
    private LocalDateTime applyTime;

    @Comment("申请原因")
    @Column(length = 512)
    private String applyReason;

    @Comment("审批人")
    @Column(nullable = false, length = 64)
    private String approver;

    @Comment("审批状态(PENDING-待审批/APPROVED-已通过/REJECTED-已驳回)")
    @Column(nullable = false, length = 32)
    private String approvalStatus;

    @Comment("审批时间")
    private LocalDateTime approvalTime;

    @Comment("审批意见")
    @Column(length = 512)
    private String approvalOpinion;

    @Comment("是否需要上级审批")
    @Column(nullable = false)
    private Boolean needSuperiorApproval = false;

    @Comment("上级审批人")
    @Column(length = 64)
    private String superiorApprover;

    @Comment("上级审批状态")
    @Column(length = 32)
    private String superiorApprovalStatus;

    @Comment("上级审批时间")
    private LocalDateTime superiorApprovalTime;

    @Comment("上级审批意见")
    @Column(length = 512)
    private String superiorApprovalOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 