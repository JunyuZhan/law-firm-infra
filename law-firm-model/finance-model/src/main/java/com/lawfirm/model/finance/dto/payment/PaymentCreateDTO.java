package com.lawfirm.model.finance.dto.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款创建DTO
 */
@Data
public class PaymentCreateDTO {
    
    /**
     * 付款编号
     */
    @Size(max = 32, message = "付款编号长度不能超过32个字符")
    private String paymentNumber;
    
    /**
     * 付款金额
     */
    @NotNull(message = "付款金额不能为空")
    @DecimalMin(value = "0.01", message = "付款金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 付款日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
    
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
    @NotNull(message = "客户不能为空")
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
    @Size(max = 500, message = "付款说明长度不能超过500个字符")
    private String description;
    
    /**
     * 付款明细，JSON格式
     */
    private String details;
    
    /**
     * 付款方式
     */
    private String paymentMethod;
    
    /**
     * 付款计划ID
     */
    private Long paymentPlanId;
    
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
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 