package com.lawfirm.model.cases.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件分配数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CaseAssignmentDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 原律师ID
     */
    private Long originalLawyerId;

    /**
     * 受理律师ID
     */
    private Long assignedLawyerId;

    /**
     * 分配类型（转办、协办、临时代理、工作交接）
     */
    private String assignmentType;

    /**
     * 分配状态（待处理、已接受、已拒绝、已完成）
     */
    private String assignmentStatus;

    /**
     * 预计交接时间
     */
    private transient LocalDateTime expectedTransferTime;

    /**
     * 实际交接时间
     */
    private transient LocalDateTime actualTransferTime;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批意见
     */
    private String approvalOpinion;

    /**
     * 交接说明
     */
    private String transferDescription;

    /**
     * 是否需要交接
     */
    private Boolean needTransfer;

    /**
     * 是否通知当事人
     */
    private Boolean notifyClient;

    /**
     * 通知内容
     */
    private String notificationContent;
} 