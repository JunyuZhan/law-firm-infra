package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 案件财务展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseFinanceVO extends BaseVO {

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

    /**
     * 判断是否为收入
     */
    public boolean isIncome() {
        return recordType != null && recordType == 1;
    }

    /**
     * 判断是否为支出
     */
    public boolean isExpense() {
        return recordType != null && recordType == 2;
    }

    /**
     * 判断交易是否已完成
     */
    public boolean isTransactionCompleted() {
        return transactionStatus != null && transactionStatus == 2;
    }

    /**
     * 判断交易是否已取消
     */
    public boolean isTransactionCancelled() {
        return transactionStatus != null && transactionStatus == 4;
    }

    /**
     * 判断是否已开具发票
     */
    public boolean hasInvoice() {
        return invoiceNumber != null && !invoiceNumber.trim().isEmpty();
    }

    /**
     * 判断发票是否已认证
     */
    public boolean isInvoiceVerified() {
        return invoiceStatus != null && invoiceStatus == 2;
    }

    /**
     * 判断是否已审核通过
     */
    public boolean isReviewed() {
        return reviewStatus != null && reviewStatus == 2;
    }

    /**
     * 判断是否已记账
     */
    public boolean isAccounted() {
        return accountingTime != null;
    }

    /**
     * 获取交易距今天数
     */
    public long getDaysSinceTransaction() {
        if (transactionTime == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(transactionTime, LocalDateTime.now());
    }

    /**
     * 获取含税金额
     */
    public BigDecimal getIncludingTaxAmount() {
        if (excludingTaxAmount == null || taxAmount == null) {
            return amount;
        }
        return excludingTaxAmount.add(taxAmount);
    }

    /**
     * 判断发票金额是否匹配
     */
    public boolean isInvoiceAmountMatched() {
        if (invoiceAmount == null || amount == null) {
            return false;
        }
        return invoiceAmount.compareTo(amount) == 0;
    }
} 