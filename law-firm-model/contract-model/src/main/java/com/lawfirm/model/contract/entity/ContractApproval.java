package com.lawfirm.model.contract.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.contract.constants.ContractConstants;
import com.lawfirm.model.contract.enums.ApprovalStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 合同审批实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT_APPROVAL)
public class ContractApproval extends ModelBaseEntity<ContractApproval> {

    @NotNull(message = "合同ID不能为空")
    private Long contractId;  // 合同ID

    private Integer approvalStep;  // 审批步骤
    private String approvalNode;   // 审批节点

    @NotNull(message = "审批状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatusEnum approvalStatus;  // 审批状态

    private Long approverId;      // 审批人ID
    private String approverName;  // 审批人姓名
    private String approverRole;  // 审批人角色

    @Size(max = ContractConstants.FieldLength.APPROVAL_OPINION, message = "审批意见长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.APPROVAL_OPINION)
    private String approvalOpinion;  // 审批意见

    private LocalDateTime approvalTime;     // 审批时间
    private LocalDateTime deadlineTime;     // 截止时间
    private LocalDateTime reminderTime;     // 提醒时间
    private Integer reminderCount;          // 提醒次数

    private Boolean isAutoApproval;         // 是否自动审批
    private Boolean isRequired;             // 是否必要审批
    private Boolean canBeSkipped;           // 是否可跳过

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注
}

