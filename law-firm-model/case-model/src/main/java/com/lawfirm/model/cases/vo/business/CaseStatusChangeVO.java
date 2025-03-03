package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.base.CaseStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件状态变更视图对象
 * 
 * 包含状态变更的基本信息，如变更前后状态、操作人、变更原因等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseStatusChangeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 变更前状态
     */
    private CaseStatusEnum oldStatus;

    /**
     * 变更后状态
     */
    private CaseStatusEnum newStatus;

    /**
     * 变更原因
     */
    private String changeReason;

    /**
     * 变更时间
     */
    private transient LocalDateTime changeTime;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 是否需要审批
     */
    private Boolean needApproval;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批人姓名
     */
    private String approverName;

    /**
     * 审批时间
     */
    private transient LocalDateTime approvalTime;

    /**
     * 审批状态（0-未审批，1-已通过，2-已拒绝）
     */
    private Integer approvalStatus;

    /**
     * 审批意见
     */
    private String approvalOpinion;

    /**
     * 是否自动变更
     */
    private Boolean isAutomatic;

    /**
     * 自动变更规则ID
     */
    private Long autoRuleId;

    /**
     * 自动变更规则名称
     */
    private String autoRuleName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取审批状态名称
     */
    public String getApprovalStatusName() {
        if (this.approvalStatus == null) {
            return "未审批";
        }
        
        switch (this.approvalStatus) {
            case 0:
                return "未审批";
            case 1:
                return "已通过";
            case 2:
                return "已拒绝";
            default:
                return "未知";
        }
    }

    /**
     * 判断是否已审批
     */
    public boolean isApproved() {
        return this.approvalStatus != null && this.approvalStatus == 1;
    }

    /**
     * 判断是否已拒绝
     */
    public boolean isRejected() {
        return this.approvalStatus != null && this.approvalStatus == 2;
    }

    /**
     * 判断是否待审批
     */
    public boolean isPendingApproval() {
        return Boolean.TRUE.equals(this.needApproval) && 
               (this.approvalStatus == null || this.approvalStatus == 0);
    }

    /**
     * 判断是否为系统自动变更
     */
    public boolean isSystemChange() {
        return Boolean.TRUE.equals(this.isAutomatic);
    }

    /**
     * 判断是否为手动变更
     */
    public boolean isManualChange() {
        return !Boolean.TRUE.equals(this.isAutomatic);
    }
} 