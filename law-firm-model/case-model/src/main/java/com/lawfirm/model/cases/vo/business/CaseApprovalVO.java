package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 案件审批展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseApprovalVO extends BaseVO {

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
     * 审批标题
     */
    private String approvalTitle;

    /**
     * 审批类型
     */
    private Integer approvalType;

    /**
     * 审批状态
     */
    private Integer approvalStatus;

    /**
     * 审批内容
     */
    private String approvalContent;

    /**
     * 审批优先级
     */
    private Integer approvalPriority;

    /**
     * 发起人ID
     */
    private Long initiatorId;

    /**
     * 发起人姓名
     */
    private String initiatorName;

    /**
     * 当前审批人ID
     */
    private Long currentApproverId;

    /**
     * 当前审批人姓名
     */
    private String currentApproverName;

    /**
     * 审批人列表（按顺序）
     */
    private transient List<Long> approverIds;

    /**
     * 审批人姓名列表（按顺序）
     */
    private transient List<String> approverNames;

    /**
     * 抄送人ID列表
     */
    private transient List<Long> ccIds;

    /**
     * 抄送人姓名列表
     */
    private transient List<String> ccNames;

    /**
     * 开始时间
     */
    private transient LocalDateTime startTime;

    /**
     * 截止时间
     */
    private transient LocalDateTime deadline;

    /**
     * 完成时间
     */
    private transient LocalDateTime completionTime;

    /**
     * 审批意见
     */
    private String approvalOpinion;

    /**
     * 是否加急
     */
    private Boolean isUrgent;

    /**
     * 是否需要会签
     */
    private Boolean needCountersign;

    /**
     * 是否允许加签
     */
    private Boolean allowAddApprover;

    /**
     * 是否允许转交
     */
    private Boolean allowTransfer;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 判断审批是否已完成
     */
    public boolean isCompleted() {
        return approvalStatus != null && approvalStatus == 2;
    }

    /**
     * 判断审批是否已拒绝
     */
    public boolean isRejected() {
        return approvalStatus != null && approvalStatus == 3;
    }

    /**
     * 判断审批是否进行中
     */
    public boolean isInProgress() {
        return approvalStatus != null && approvalStatus == 1;
    }

    /**
     * 判断审批是否已取消
     */
    public boolean isCancelled() {
        return approvalStatus != null && approvalStatus == 4;
    }

    /**
     * 判断审批是否已过期
     */
    public boolean isOverdue() {
        return deadline != null && LocalDateTime.now().isAfter(deadline);
    }

    /**
     * 获取剩余天数
     */
    public long getRemainingDays() {
        if (deadline == null || isCompleted() || isRejected() || isCancelled()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDateTime.now(), deadline);
    }

    /**
     * 获取审批进度（根据已审批人数计算）
     */
    public int getApprovalProgress() {
        if (approverIds == null || approverIds.isEmpty()) {
            return 0;
        }
        int totalApprovers = approverIds.size();
        int currentIndex = approverIds.indexOf(currentApproverId);
        return (int) ((currentIndex + 1) * 100.0 / totalApprovers);
    }
} 