package com.lawfirm.model.contract.dto.request;

import com.lawfirm.model.contract.constants.ContractConstants;
import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import com.lawfirm.model.contract.enums.PaymentTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同更新请求DTO
 */
@Data
public class ContractUpdateRequest {

    @NotNull(message = "合同ID不能为空")
    private Long id;  // 合同ID

    @Size(max = ContractConstants.FieldLength.CONTRACT_NAME, message = "合同名称长度不能超过{max}个字符")
    private String contractName;    // 合同名称

    private ContractTypeEnum contractType;  // 合同类型
    private ContractPriorityEnum priority;  // 优先级
    private BigDecimal amount;              // 合同金额
    private PaymentTypeEnum paymentType;    // 付款方式

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
    private String remark;  // 备注

    // 审批信息
    private Long currentApproverId;     // 当前审批人ID
    private Integer currentApprovalStep; // 当前审批步骤

    // 版本信息
    private Integer version;  // 版本号
}

