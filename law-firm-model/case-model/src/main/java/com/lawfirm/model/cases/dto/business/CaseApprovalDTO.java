package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件审批数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseApprovalDTO extends BaseDTO {

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
     * 1-案件创建 2-结案 3-文档保密 4-费用 5-团队变更 6-出函审批
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
    private LocalDateTime startTime;

    /**
     * 截止时间
     */
    private LocalDateTime deadline;

    /**
     * 完成时间
     */
    private LocalDateTime completionTime;

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
} 