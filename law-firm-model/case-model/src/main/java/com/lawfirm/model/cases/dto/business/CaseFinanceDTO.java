package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件财务数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseFinanceDTO extends BaseDTO {

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
     * 财务记录编号
     */
    private String financeNumber;

    /**
     * 记录类型（1-收入，2-支出）
     */
    private Integer recordType;

    /**
     * 交易类型
     */
    private Integer transactionType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 交易时间
     */
    private transient LocalDateTime transactionTime;

    /**
     * 交易状态
     */
    private Integer transactionStatus;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 收款人/付款人
     */
    private String counterparty;

    /**
     * 收款/付款账户
     */
    private String accountNumber;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 发票号码
     */
    private String invoiceNumber;

    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 发票状态
     */
    private Integer invoiceStatus;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 不含税金额
     */
    private BigDecimal excludingTaxAmount;

    /**
     * 费用类型
     */
    private Integer expenseType;

    /**
     * 费用说明
     */
    private String expenseDescription;

    /**
     * 预算科目
     */
    private String budgetCategory;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 关联合同ID
     */
    private Long contractId;

    /**
     * 关联合同编号
     */
    private String contractNumber;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 审核时间
     */
    private transient LocalDateTime reviewTime;

    /**
     * 记账人ID
     */
    private Long accountantId;

    /**
     * 记账人姓名
     */
    private String accountantName;

    /**
     * 记账时间
     */
    private transient LocalDateTime accountingTime;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 附件IDs（逗号分隔）
     */
    private String attachmentIds;

    /**
     * 备注
     */
    private String remarks;
} 