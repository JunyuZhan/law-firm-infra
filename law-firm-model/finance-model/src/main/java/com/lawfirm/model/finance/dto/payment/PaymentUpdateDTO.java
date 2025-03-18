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
 * 付款更新DTO
 */
@Data
public class PaymentUpdateDTO {
    
    /**
     * 付款记录ID
     */
    @NotNull(message = "付款记录ID不能为空")
    private Long id;
    
    /**
     * 付款金额
     */
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
     * 付款状态
     */
    private Integer paymentStatus;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 