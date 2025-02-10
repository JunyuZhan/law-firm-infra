package com.lawfirm.model.contract.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.contract.constants.ContractConstants;
import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import com.lawfirm.model.contract.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT)
public class Contract extends ModelBaseEntity<Contract> {

    @NotBlank(message = "合同编号不能为空")
    @Size(max = ContractConstants.FieldLength.CONTRACT_NUMBER, message = "合同编号长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.CONTRACT_NUMBER, unique = true)
    private String contractNumber;  // 合同编号

    @NotBlank(message = "合同名称不能为空")
    @Size(max = ContractConstants.FieldLength.CONTRACT_NAME, message = "合同名称长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.CONTRACT_NAME)
    private String contractName;    // 合同名称

    @NotNull(message = "合同类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractTypeEnum contractType;  // 合同类型

    @NotNull(message = "合同状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractStatusEnum contractStatus;  // 合同状态

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ContractPriorityEnum priority;  // 优先级

    @NotNull(message = "合同金额不能为空")
    @Column(nullable = false, precision = ContractConstants.Amount.PRECISION, scale = ContractConstants.Amount.SCALE)
    private BigDecimal amount;  // 合同金额

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentTypeEnum paymentType;  // 付款方式

    // 关联信息
    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID
    private Long clientId;   // 客户ID
    private Long templateId; // 合同模板ID

    // 签约信息
    private LocalDateTime signDate;      // 签约日期
    private LocalDateTime effectiveDate; // 生效日期
    private LocalDateTime expiryDate;    // 到期日期

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注

    // 审批信息
    private Long currentApproverId;  // 当前审批人ID
    private Integer currentApprovalStep;  // 当前审批步骤

    // 统计信息
    private Integer clauseCount;     // 条款数量
    private Integer attachmentCount; // 附件数量
    private Integer partyCount;      // 相关方数量

    // 时间信息
    private LocalDateTime lastApprovalTime;  // 最后审批时间
    private LocalDateTime lastUpdateTime;    // 最后更新时间
    private LocalDateTime archiveTime;       // 归档时间
}

