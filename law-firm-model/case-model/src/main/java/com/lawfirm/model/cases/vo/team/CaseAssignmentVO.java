package com.lawfirm.model.cases.vo.team;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件分配视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CaseAssignmentVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 原律师ID
     */
    private Long originalLawyerId;

    /**
     * 原律师姓名
     */
    private String originalLawyerName;

    /**
     * 受理律师ID
     */
    private Long assignedLawyerId;

    /**
     * 受理律师姓名
     */
    private String assignedLawyerName;

    /**
     * 分配类型（转办、协办、临时代理、工作交接）
     */
    private String assignmentType;

    /**
     * 分配类型名称
     */
    private String assignmentTypeName;

    /**
     * 分配状态（待处理、已接受、已拒绝、已完成）
     */
    private String assignmentStatus;

    /**
     * 分配状态名称
     */
    private String assignmentStatusName;

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
     * 审批人姓名
     */
    private String approverName;

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

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 更新人姓名
     */
    private String updaterName;
} 