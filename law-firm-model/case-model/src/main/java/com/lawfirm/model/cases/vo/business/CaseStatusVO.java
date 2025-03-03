package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.base.CaseStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件状态视图对象
 * 
 * 包含状态的基本信息，如当前状态、状态变更历史等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseStatusVO extends BaseVO {

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
     * 当前状态
     */
    private CaseStatusEnum currentStatus;

    /**
     * 当前状态名称
     */
    private String currentStatusName;

    /**
     * 当前状态描述
     */
    private String currentStatusDescription;

    /**
     * 当前状态开始时间
     */
    private transient LocalDateTime currentStatusStartTime;

    /**
     * 当前状态持续时间（小时）
     */
    private Long currentStatusDuration;

    /**
     * 上一个状态
     */
    private CaseStatusEnum previousStatus;

    /**
     * 上一个状态名称
     */
    private String previousStatusName;

    /**
     * 上一个状态结束时间
     */
    private transient LocalDateTime previousStatusEndTime;

    /**
     * 状态变更次数
     */
    private Integer statusChangeCount;

    /**
     * 最后变更时间
     */
    private transient LocalDateTime lastChangeTime;

    /**
     * 最后变更人ID
     */
    private Long lastChangerId;

    /**
     * 最后变更人姓名
     */
    private String lastChangerName;

    /**
     * 最后变更原因
     */
    private String lastChangeReason;

    /**
     * 是否处于审批中
     */
    private Boolean isInApproval;

    /**
     * 当前审批人ID
     */
    private Long currentApproverId;

    /**
     * 当前审批人姓名
     */
    private String currentApproverName;

    /**
     * 审批开始时间
     */
    private transient LocalDateTime approvalStartTime;

    /**
     * 是否可以变更状态
     */
    private Boolean canChangeStatus;

    /**
     * 可变更的目标状态列表（逗号分隔）
     */
    private String allowedTargetStatuses;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取当前状态持续时间（小时）
     */
    public Long getCurrentStatusDuration() {
        if (this.currentStatusStartTime == null) {
            return 0L;
        }
        
        LocalDateTime endTime = LocalDateTime.now();
        return java.time.Duration.between(this.currentStatusStartTime, endTime).toHours();
    }

    /**
     * 判断是否可以变更到指定状态
     */
    public boolean canChangeTo(CaseStatusEnum targetStatus) {
        if (!Boolean.TRUE.equals(this.canChangeStatus) || 
            this.allowedTargetStatuses == null || 
            this.allowedTargetStatuses.isEmpty()) {
            return false;
        }
        
        return this.allowedTargetStatuses.contains(targetStatus.name());
    }

    /**
     * 获取允许变更的目标状态数组
     */
    public String[] getAllowedTargetStatusArray() {
        if (this.allowedTargetStatuses == null || this.allowedTargetStatuses.isEmpty()) {
            return new String[0];
        }
        
        return this.allowedTargetStatuses.split(",");
    }

    /**
     * 判断是否处于初始状态
     */
    public boolean isInitialStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isInitialStatus();
    }

    /**
     * 判断是否处于终止状态
     */
    public boolean isTerminalStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isTerminalStatus();
    }

    /**
     * 判断是否处于活动状态
     */
    public boolean isActiveStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isActiveStatus();
    }

    /**
     * 判断是否处于暂停状态
     */
    public boolean isPausedStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isPausedStatus();
    }

    /**
     * 判断是否处于关闭状态
     */
    public boolean isClosedStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isClosedStatus();
    }

    /**
     * 判断是否处于异常状态
     */
    public boolean isAbnormalStatus() {
        return this.currentStatus != null && 
               this.currentStatus.isAbnormalStatus();
    }

    /**
     * 判断是否需要审批
     */
    public boolean needApproval() {
        return this.currentStatus != null && 
               this.currentStatus.needApproval();
    }

    /**
     * 判断是否允许回退
     */
    public boolean allowRollback() {
        return this.currentStatus != null && 
               this.currentStatus.allowRollback();
    }

    /**
     * 判断是否允许跳转
     */
    public boolean allowSkip() {
        return this.currentStatus != null && 
               this.currentStatus.allowSkip();
    }

    /**
     * 判断是否允许编辑
     */
    public boolean allowEdit() {
        return this.currentStatus != null && 
               this.currentStatus.allowEdit();
    }

    /**
     * 判断是否允许删除
     */
    public boolean allowDelete() {
        return this.currentStatus != null && 
               this.currentStatus.allowDelete();
    }
} 