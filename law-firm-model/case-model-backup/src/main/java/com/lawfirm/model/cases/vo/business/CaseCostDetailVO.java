package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件费用明细视图对象
 */
@Data
public class CaseCostDetailVO {
    
    /**
     * 基本费用信息
     */
    private Long caseId; // 案件ID
    private String caseNumber; // 案件编号
    private String caseName; // 案件名称
    private BigDecimal totalAmount; // 总金额
    private BigDecimal paidAmount; // 已付金额
    private BigDecimal unpaidAmount; // 未付金额
    private String feeType; // 收费类型
    private String paymentStatus; // 支付状态
    
    /**
     * 收费阶段明细
     */
    @Data
    public static class StageDetail {
        private String stageName; // 阶段名称
        private BigDecimal stageAmount; // 阶段金额
        private String stageStatus; // 阶段状态
        private LocalDateTime dueDate; // 应付日期
        private LocalDateTime paidDate; // 实付日期
        private String paymentMethod; // 支付方式
        private String remark; // 备注
    }
    private List<StageDetail> stageDetails; // 分阶段收费明细
    
    /**
     * 额外费用明细
     */
    @Data
    public static class ExtraFee {
        private String feeItem; // 费用项目
        private BigDecimal amount; // 金额
        private String purpose; // 用途
        private LocalDateTime occurDate; // 发生日期
        private String approver; // 审批人
        private String status; // 状态
    }
    private List<ExtraFee> extraFees; // 额外费用明细
    
    /**
     * 退费记录
     */
    @Data
    public static class Refund {
        private BigDecimal amount; // 退费金额
        private String reason; // 退费原因
        private LocalDateTime refundDate; // 退费日期
        private String processor; // 处理人
        private String status; // 状态
    }
    private List<Refund> refunds; // 退费记录
    
    /**
     * 发票信息
     */
    @Data
    public static class Invoice {
        private String invoiceNumber; // 发票号码
        private BigDecimal amount; // 发票金额
        private String type; // 发票类型
        private LocalDateTime issueDate; // 开具日期
        private String status; // 状态
    }
    private List<Invoice> invoices; // 发票信息
    
    /**
     * 统计信息
     */
    private BigDecimal extraFeesTotal; // 额外费用总额
    private BigDecimal refundsTotal; // 退费总额
    private BigDecimal invoicedAmount; // 已开票金额
    private BigDecimal uninvoicedAmount; // 未开票金额
    
    /**
     * 其他信息
     */
    private String paymentTerms; // 付款条件
    private String billingContact; // 账单联系人
    private String billingAddress; // 账单地址
    private String taxNumber; // 税号
    private String bankAccount; // 收款账户
    private String remark; // 备注
} 