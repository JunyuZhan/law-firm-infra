package com.lawfirm.model.finance.dto.paymentplan;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款计划创建DTO
 */
@Data
public class PaymentPlanCreateDTO {
    
    /**
     * 付款计划编号
     */
    private String planNumber;
    
    /**
     * 计划付款金额
     */
    @NotNull(message = "计划付款金额不能为空")
    private BigDecimal planAmount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 计划付款日期
     */
    @NotNull(message = "计划付款日期不能为空")
    private LocalDateTime planPaymentDate;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联合同ID
     */
    private Long contractId;
    
    /**
     * 关联客户ID
     */
    private Long clientId;
    
    /**
     * 关联律师ID
     */
    private Long lawyerId;
    
    /**
     * 关联部门ID
     */
    private Long departmentId;
    
    /**
     * 付款说明
     */
    private String description;
    
    /**
     * 付款方式
     */
    private String paymentMethod;
    
    /**
     * 关联账单ID
     */
    private Long billingId;
    
    /**
     * 关联发票ID
     */
    private Long invoiceId;
    
    /**
     * 备注
     */
    private String remark;
} 