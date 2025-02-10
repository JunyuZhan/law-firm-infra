package com.lawfirm.model.contract.dto.request;

import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同查询请求DTO
 */
@Data
public class ContractQueryRequest {

    private String contractNumber;  // 合同编号
    private String contractName;    // 合同名称
    private ContractTypeEnum contractType;  // 合同类型
    private ContractStatusEnum contractStatus;  // 合同状态
    private ContractPriorityEnum priority;  // 优先级

    // 金额范围
    private BigDecimal minAmount;  // 最小金额
    private BigDecimal maxAmount;  // 最大金额

    // 关联信息
    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID
    private Long clientId;   // 客户ID

    // 时间范围
    private LocalDateTime createTimeStart;  // 创建时间起始
    private LocalDateTime createTimeEnd;    // 创建时间结束
    private LocalDateTime signTimeStart;    // 签约时间起始
    private LocalDateTime signTimeEnd;      // 签约时间结束
    private LocalDateTime expiryTimeStart;  // 到期时间起始
    private LocalDateTime expiryTimeEnd;    // 到期时间结束

    // 审批相关
    private Long approverId;  // 审批人ID
    private Boolean isApproved;  // 是否已审批
    private Boolean isPending;   // 是否待审批

    // 分页参数
    private Integer pageNum = 1;     // 页码
    private Integer pageSize = 10;   // 每页大小
    private String sortField;        // 排序字段
    private String sortOrder;        // 排序方式

    // 高级查询
    private Boolean includeExpired;     // 包含已过期
    private Boolean includeTerminated;  // 包含已终止
    private Boolean onlyDrafts;         // 仅草稿
    private Boolean onlyTemplate;       // 仅模板
    private String keyword;             // 关键字搜索
}

