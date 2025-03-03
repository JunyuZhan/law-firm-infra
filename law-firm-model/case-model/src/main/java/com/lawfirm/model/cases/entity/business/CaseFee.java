package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件费用实体类
 * 
 * 案件费用记录了与案件相关的各类费用信息，包括费用基本信息、
 * 金额信息、支付信息、审核信息等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_fee")
public class CaseFee extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 费用名称
     */
    @TableField("fee_name")
    private String feeName;

    /**
     * 费用类型（1:律师费, 2:诉讼费, 3:仲裁费, 4:调解费, 5:公证费, 6:鉴定费, 7:差旅费, 8:其他费用）
     */
    @TableField("fee_type")
    private Integer feeType;

    /**
     * 费用金额
     */
    @TableField("fee_amount")
    private BigDecimal feeAmount;

    /**
     * 币种（CNY:人民币, USD:美元, EUR:欧元, GBP:英镑, JPY:日元, HKD:港币）
     */
    @TableField("currency")
    private String currency;

    /**
     * 费用状态（1:待支付, 2:部分支付, 3:已支付, 4:已取消, 5:已退款）
     */
    @TableField("fee_status")
    private Integer feeStatus;

    /**
     * 费用描述
     */
    @TableField("fee_description")
    private String feeDescription;

    /**
     * 已支付金额
     */
    @TableField("paid_amount")
    private BigDecimal paidAmount;

    /**
     * 支付方式（1:现金, 2:银行转账, 3:支票, 4:信用卡, 5:微信, 6:支付宝, 7:其他）
     */
    @TableField("payment_method")
    private Integer paymentMethod;

    /**
     * 付款时间
     */
    @TableField("payment_time")
    private transient LocalDateTime paymentTime;

    /**
     * 支付人
     */
    @TableField("payer")
    private String payer;

    /**
     * 收款人
     */
    @TableField("payee")
    private String payee;

    /**
     * 交易编号
     */
    @TableField("transaction_number")
    private String transactionNumber;

    /**
     * 发票号码
     */
    @TableField("invoice_number")
    private String invoiceNumber;

    /**
     * 是否已开票
     */
    @TableField("is_invoiced")
    private Boolean isInvoiced;

    /**
     * 开票时间
     */
    @TableField("invoice_time")
    private transient LocalDateTime invoiceTime;

    /**
     * 是否需要审核
     */
    @TableField("need_review")
    private Boolean needReview;

    /**
     * 审核人ID
     */
    @TableField("reviewer_id")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    @TableField("reviewer_name")
    private String reviewerName;

    /**
     * 审核时间
     */
    @TableField("review_time")
    private transient LocalDateTime reviewTime;

    /**
     * 审核状态（0:未审核, 1:审核通过, 2:审核不通过）
     */
    @TableField("review_status")
    private Integer reviewStatus;

    /**
     * 审核意见
     */
    @TableField("review_opinion")
    private String reviewOpinion;

    /**
     * 创建人ID
     */
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 创建人姓名
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 关联文档IDs（逗号分隔）
     */
    @TableField("document_ids")
    private String documentIds;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 审核人角色
     */
    @TableField("reviewer_role")
    private String reviewerRole;

    /**
     * 支付人角色
     */
    @TableField("payer_role")
    private String payerRole;

    /**
     * 支付状态（1:待支付, 2:部分支付, 3:已支付, 4:已取消, 5:已退款）
     */
    @TableField("payment_status")
    private Integer paymentStatus;

    /**
     * 判断费用是否已支付
     */
    public boolean isPaid() {
        return this.feeStatus != null && this.feeStatus == 3;
    }

    /**
     * 判断费用是否部分支付
     */
    public boolean isPartiallyPaid() {
        return this.feeStatus != null && this.feeStatus == 2;
    }

    /**
     * 判断费用是否待支付
     */
    public boolean isPending() {
        return this.feeStatus != null && this.feeStatus == 1;
    }

    /**
     * 判断费用是否已取消
     */
    public boolean isCancelled() {
        return this.feeStatus != null && this.feeStatus == 4;
    }

    /**
     * 判断费用是否已退款
     */
    public boolean isRefunded() {
        return this.feeStatus != null && this.feeStatus == 5;
    }

    /**
     * 判断是否已审核
     */
    public boolean isReviewed() {
        return this.reviewStatus != null && this.reviewStatus > 0;
    }

    /**
     * 判断审核是否通过
     */
    public boolean isReviewPassed() {
        return this.reviewStatus != null && this.reviewStatus == 1;
    }

    /**
     * 判断审核是否不通过
     */
    public boolean isReviewRejected() {
        return this.reviewStatus != null && this.reviewStatus == 2;
    }

    /**
     * 获取未支付金额
     */
    public BigDecimal getUnpaidAmount() {
        if (this.feeAmount == null) {
            return BigDecimal.ZERO;
        }
        
        if (this.paidAmount == null) {
            return this.feeAmount;
        }
        
        return this.feeAmount.subtract(this.paidAmount);
    }

    /**
     * 获取支付比例
     */
    public BigDecimal getPaymentRatio() {
        if (this.feeAmount == null || this.feeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        if (this.paidAmount == null) {
            return BigDecimal.ZERO;
        }
        
        return this.paidAmount.divide(this.feeAmount, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * 更新支付状态
     */
    public void updatePaymentStatus() {
        if (this.feeAmount == null || this.paidAmount == null) {
            this.feeStatus = 1; // 待支付
            return;
        }
        
        int comparison = this.paidAmount.compareTo(this.feeAmount);
        
        if (comparison >= 0) {
            this.feeStatus = 3; // 已支付
        } else if (comparison > 0 && this.paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            this.feeStatus = 2; // 部分支付
        } else {
            this.feeStatus = 1; // 待支付
        }
    }

    /**
     * 添加支付记录
     */
    public CaseFee addPayment(BigDecimal amount, Integer paymentMethod, String payer, String transactionNumber) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return this;
        }
        
        if (this.paidAmount == null) {
            this.paidAmount = amount;
        } else {
            this.paidAmount = this.paidAmount.add(amount);
        }
        
        this.paymentMethod = paymentMethod;
        this.payer = payer;
        this.transactionNumber = transactionNumber;
        this.paymentTime = LocalDateTime.now();
        
        updatePaymentStatus();
        
        return this;
    }

    /**
     * 取消费用
     */
    public CaseFee cancel() {
        this.feeStatus = 4; // 已取消
        return this;
    }

    /**
     * 退款
     */
    public CaseFee refund() {
        this.feeStatus = 5; // 已退款
        return this;
    }

    /**
     * 审核通过
     */
    public CaseFee approveReview(Long reviewerId, String reviewerName, String reviewOpinion) {
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewStatus = 1; // 审核通过
        this.reviewOpinion = reviewOpinion;
        this.reviewTime = LocalDateTime.now();
        return this;
    }

    /**
     * 审核不通过
     */
    public CaseFee rejectReview(Long reviewerId, String reviewerName, String reviewOpinion) {
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewStatus = 2; // 审核不通过
        this.reviewOpinion = reviewOpinion;
        this.reviewTime = LocalDateTime.now();
        return this;
    }

    /**
     * 获取费用类型名称
     */
    public String getFeeTypeName() {
        if (this.feeType == null) {
            return "";
        }
        
        switch (this.feeType) {
            case 1: return "律师费";
            case 2: return "诉讼费";
            case 3: return "仲裁费";
            case 4: return "调解费";
            case 5: return "公证费";
            case 6: return "鉴定费";
            case 7: return "差旅费";
            case 8: return "其他费用";
            default: return "未知费用";
        }
    }

    /**
     * 获取支付方式名称
     */
    public String getPaymentMethodName() {
        if (this.paymentMethod == null) {
            return "";
        }
        
        switch (this.paymentMethod) {
            case 1: return "现金";
            case 2: return "银行转账";
            case 3: return "支票";
            case 4: return "信用卡";
            case 5: return "微信";
            case 6: return "支付宝";
            case 7: return "其他";
            default: return "未知方式";
        }
    }

    /**
     * 获取费用状态名称
     */
    public String getFeeStatusName() {
        if (this.feeStatus == null) {
            return "";
        }
        
        switch (this.feeStatus) {
            case 1: return "待支付";
            case 2: return "部分支付";
            case 3: return "已支付";
            case 4: return "已取消";
            case 5: return "已退款";
            default: return "未知状态";
        }
    }

    /**
     * 获取审核状态名称
     */
    public String getReviewStatusName() {
        if (this.reviewStatus == null) {
            return "未审核";
        }
        
        switch (this.reviewStatus) {
            case 0: return "未审核";
            case 1: return "审核通过";
            case 2: return "审核不通过";
            default: return "未知状态";
        }
    }

    /**
     * 获取格式化的金额
     */
    public String getFormattedAmount() {
        if (this.feeAmount == null) {
            return "0.00";
        }
        
        return this.feeAmount.setScale(2, java.math.RoundingMode.HALF_UP).toString();
    }

    /**
     * 获取格式化的已支付金额
     */
    public String getFormattedPaidAmount() {
        if (this.paidAmount == null) {
            return "0.00";
        }
        
        return this.paidAmount.setScale(2, java.math.RoundingMode.HALF_UP).toString();
    }

    /**
     * 获取格式化的未支付金额
     */
    public String getFormattedUnpaidAmount() {
        return getUnpaidAmount().setScale(2, java.math.RoundingMode.HALF_UP).toString();
    }
} 