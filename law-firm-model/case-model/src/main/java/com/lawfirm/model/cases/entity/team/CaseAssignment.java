package com.lawfirm.model.cases.entity.team;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件分配实体类
 * 
 * 案件分配记录了案件在律师之间的转办、协办、临时代理、工作交接等分配信息，
 * 包括分配信息、分配类型、分配状态、时间信息、审批信息、交接信息、通知信息等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_assignment")
public class CaseAssignment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 案件名称
     */
    @TableField("case_name")
    private String caseName;

    /**
     * 原律师ID
     */
    @TableField("original_lawyer_id")
    private Long originalLawyerId;

    /**
     * 原律师姓名
     */
    @TableField("original_lawyer_name")
    private String originalLawyerName;

    /**
     * 受理律师ID
     */
    @TableField("assigned_lawyer_id")
    private Long assignedLawyerId;

    /**
     * 受理律师姓名
     */
    @TableField("assigned_lawyer_name")
    private String assignedLawyerName;

    /**
     * 分配类型（1-转办，2-协办，3-临时代理，4-工作交接）
     */
    @TableField("assignment_type")
    private Integer assignmentType;

    /**
     * 分配状态（0-待处理，1-已接受，2-已拒绝，3-已完成）
     */
    @TableField("assignment_status")
    private Integer assignmentStatus;

    /**
     * 分配原因
     */
    @TableField("assignment_reason")
    private String assignmentReason;

    /**
     * 分配时间
     */
    @TableField("assignment_time")
    private transient LocalDateTime assignmentTime;

    /**
     * 预计交接时间
     */
    @TableField("expected_handover_time")
    private transient LocalDateTime expectedHandoverTime;

    /**
     * 实际交接时间
     */
    @TableField("actual_handover_time")
    private transient LocalDateTime actualHandoverTime;

    /**
     * 审批人ID
     */
    @TableField("approver_id")
    private Long approverId;

    /**
     * 审批人姓名
     */
    @TableField("approver_name")
    private String approverName;

    /**
     * 审批时间
     */
    @TableField("approval_time")
    private transient LocalDateTime approvalTime;

    /**
     * 审批意见
     */
    @TableField("approval_opinion")
    private String approvalOpinion;

    /**
     * 是否需要交接
     */
    @TableField("need_handover")
    private Boolean needHandover;

    /**
     * 交接说明
     */
    @TableField("handover_description")
    private String handoverDescription;

    /**
     * 是否通知当事人
     */
    @TableField("notify_client")
    private Boolean notifyClient;

    /**
     * 通知时间
     */
    @TableField("notification_time")
    private transient LocalDateTime notificationTime;

    /**
     * 通知内容
     */
    @TableField("notification_content")
    private String notificationContent;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 判断是否为转办
     */
    public boolean isTransfer() {
        return this.assignmentType != null && this.assignmentType == 1;
    }

    /**
     * 判断是否为协办
     */
    public boolean isCooperation() {
        return this.assignmentType != null && this.assignmentType == 2;
    }

    /**
     * 判断是否为临时代理
     */
    public boolean isTemporaryAgent() {
        return this.assignmentType != null && this.assignmentType == 3;
    }

    /**
     * 判断是否为工作交接
     */
    public boolean isWorkHandover() {
        return this.assignmentType != null && this.assignmentType == 4;
    }

    /**
     * 判断是否待处理
     */
    public boolean isPending() {
        return this.assignmentStatus != null && this.assignmentStatus == 0;
    }

    /**
     * 判断是否已接受
     */
    public boolean isAccepted() {
        return this.assignmentStatus != null && this.assignmentStatus == 1;
    }

    /**
     * 判断是否已拒绝
     */
    public boolean isRejected() {
        return this.assignmentStatus != null && this.assignmentStatus == 2;
    }

    /**
     * 判断是否已完成
     */
    public boolean isCompleted() {
        return this.assignmentStatus != null && this.assignmentStatus == 3;
    }

    /**
     * 获取分配类型名称
     */
    public String getAssignmentTypeName() {
        if (this.assignmentType == null) {
            return "未知";
        }
        
        switch (this.assignmentType) {
            case 1:
                return "转办";
            case 2:
                return "协办";
            case 3:
                return "临时代理";
            case 4:
                return "工作交接";
            default:
                return "其他";
        }
    }

    /**
     * 获取分配状态名称
     */
    public String getAssignmentStatusName() {
        if (this.assignmentStatus == null) {
            return "未知";
        }
        
        switch (this.assignmentStatus) {
            case 0:
                return "待处理";
            case 1:
                return "已接受";
            case 2:
                return "已拒绝";
            case 3:
                return "已完成";
            default:
                return "其他";
        }
    }

    /**
     * 计算分配到现在的天数
     */
    public long getDaysSinceAssignment() {
        LocalDateTime start = this.assignmentTime != null ? this.assignmentTime : 
                             (this.getCreateTime() != null ? this.getCreateTime() : LocalDateTime.now());
        return java.time.temporal.ChronoUnit.DAYS.between(start, LocalDateTime.now());
    }

    /**
     * 判断是否已逾期（超过预计交接时间）
     */
    public boolean isOverdue() {
        if (this.expectedHandoverTime == null || this.isCompleted()) {
            return false;
        }
        
        return LocalDateTime.now().isAfter(this.expectedHandoverTime);
    }

    /**
     * 获取逾期天数
     */
    public long getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        
        return java.time.temporal.ChronoUnit.DAYS.between(this.expectedHandoverTime, LocalDateTime.now());
    }
} 