package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件审批实体
 */
@Data
@TableName("case_approval")
@EqualsAndHashCode(callSuper = true)
public class CaseApproval extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 审批类型(CASE_CREATE-立案审批/CASE_CLOSE-结案审批/FEE_ADJUSTMENT-收费调整)
     */
    @TableField("approval_type")
    private String approvalType;

    /**
     * 申请人
     */
    @TableField("applicant")
    private String applicant;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private LocalDateTime applyTime;

    /**
     * 申请原因
     */
    @TableField("apply_reason")
    private String applyReason;

    /**
     * 审批人
     */
    @TableField("approver")
    private String approver;

    /**
     * 审批状态(PENDING-待审批/APPROVED-已通过/REJECTED-已驳回)
     */
    @TableField("approval_status")
    private String approvalStatus;

    /**
     * 审批时间
     */
    @TableField("approval_time")
    private LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    @TableField("approval_opinion")
    private String approvalOpinion;

    /**
     * 是否需要上级审批
     */
    @TableField("need_superior_approval")
    private Boolean needSuperiorApproval = false;

    /**
     * 上级审批人
     */
    @TableField("superior_approver")
    private String superiorApprover;

    /**
     * 上级审批状态
     */
    @TableField("superior_approval_status")
    private String superiorApprovalStatus;

    /**
     * 上级审批时间
     */
    @TableField("superior_approval_time")
    private LocalDateTime superiorApprovalTime;

    /**
     * 上级审批意见
     */
    @TableField("superior_approval_opinion")
    private String superiorApprovalOpinion;

    /**
     * 关联的案件对象 - 使用exist=false表示非数据库字段，只用于关联查询
     */
    @TableField(exist = false)
    private Case lawCase;
} 