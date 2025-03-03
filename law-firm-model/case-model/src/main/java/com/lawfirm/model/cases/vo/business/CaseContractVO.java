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
 * 案件合同展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseContractVO extends BaseVO {

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
     * 合同编号
     */
    private String contractNumber;

    /**
     * 合同标题
     */
    private String contractTitle;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 合同金额
     */
    private BigDecimal contractAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 签约主体（我方）
     */
    private String partyA;

    /**
     * 签约主体（对方）
     */
    private String partyB;

    /**
     * 签约日期
     */
    private LocalDateTime signDate;

    /**
     * 生效日期
     */
    private LocalDateTime effectiveDate;

    /**
     * 到期日期
     */
    private LocalDateTime expiryDate;

    /**
     * 合同内容
     */
    private String contractContent;

    /**
     * 合同条款（JSON格式）
     */
    private String contractTerms;

    /**
     * 付款条件
     */
    private String paymentTerms;

    /**
     * 付款计划（JSON格式）
     */
    private String paymentSchedule;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 负责人姓名
     */
    private String managerName;

    /**
     * 签约人ID
     */
    private Long signerId;

    /**
     * 签约人姓名
     */
    private String signerName;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 附件IDs（逗号分隔）
     */
    private String attachmentIds;

    /**
     * 标签（逗号分隔）
     */
    private String tags;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 判断合同是否已生效
     */
    public boolean isEffective() {
        return effectiveDate != null && LocalDateTime.now().isAfter(effectiveDate);
    }

    /**
     * 判断合同是否已到期
     */
    public boolean isExpired() {
        return expiryDate != null && LocalDateTime.now().isAfter(expiryDate);
    }

    /**
     * 判断合同是否已签署
     */
    public boolean isSigned() {
        return contractStatus != null && contractStatus == 2;
    }

    /**
     * 判断合同是否已终止
     */
    public boolean isTerminated() {
        return contractStatus != null && contractStatus == 4;
    }

    /**
     * 获取合同剩余有效期（天）
     */
    public long getRemainingDays() {
        if (expiryDate == null || isTerminated()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDateTime.now(), expiryDate);
    }

    /**
     * 获取付款进度（百分比）
     */
    public int getPaymentProgress() {
        if (contractAmount == null || contractAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        if (paidAmount == null) {
            return 0;
        }
        return paidAmount.multiply(new BigDecimal("100")).divide(contractAmount, 2, java.math.RoundingMode.HALF_UP).intValue();
    }

    /**
     * 判断是否已付清
     */
    public boolean isFullyPaid() {
        if (contractAmount == null || paidAmount == null) {
            return false;
        }
        return paidAmount.compareTo(contractAmount) >= 0;
    }

    /**
     * 判断合同是否已审核通过
     */
    public boolean isReviewed() {
        return reviewStatus != null && reviewStatus == 2;
    }

    /**
     * 判断合同是否被驳回
     */
    public boolean isRejected() {
        return reviewStatus != null && reviewStatus == 3;
    }

    /**
     * 计算税率
     */
    public BigDecimal calculateTaxRate() {
        if (contractAmount == null || contractAmount.compareTo(BigDecimal.ZERO) == 0 
                || taxAmount == null || taxAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return taxAmount.divide(contractAmount, 4, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }
} 