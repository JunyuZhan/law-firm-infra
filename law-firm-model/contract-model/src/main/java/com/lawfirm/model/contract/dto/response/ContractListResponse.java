package com.lawfirm.model.contract.dto.response;

import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同列表响应DTO
 */
@Data
public class ContractListResponse {

    private Long id;  // 合同ID
    private String contractNumber;  // 合同编号
    private String contractName;    // 合同名称
    private ContractTypeEnum contractType;  // 合同类型
    private ContractStatusEnum contractStatus;  // 合同状态
    private ContractPriorityEnum priority;  // 优先级
    private BigDecimal amount;  // 合同金额
    private BigDecimal paidAmount;  // 已付金额

    // 关联信息
    private Long lawFirmId;      // 律所ID
    private String lawFirmName;  // 律所名称
    private Long caseId;         // 案件ID
    private String caseName;     // 案件名称
    private Long clientId;       // 客户ID
    private String clientName;   // 客户名称

    // 签约信息
    private LocalDateTime signDate;      // 签约日期
    private LocalDateTime effectiveDate; // 生效日期
    private LocalDateTime expiryDate;    // 到期日期

    // 审批信息
    private String currentApproverName;  // 当前审批人姓名
    private Integer currentApprovalStep; // 当前审批步骤
    private LocalDateTime lastApprovalTime;  // 最后审批时间

    // 统计信息
    private Integer clauseCount;     // 条款数量
    private Integer attachmentCount; // 附件数量
    private Integer partyCount;      // 相关方数量

    // 时间信息
    private LocalDateTime createTime;  // 创建时间
    private String creatorName;        // 创建人
    private LocalDateTime updateTime;  // 更新时间
    private String updaterName;        // 更新人

    // 状态标记
    private Boolean isExpired;      // 是否已过期
    private Boolean isTerminated;   // 是否已终止
    private Boolean isDraft;        // 是否草稿
    private Boolean needsAttention; // 是否需要关注
}

