package com.lawfirm.model.finance.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.PaymentMethodEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FinanceQueryDTO extends BaseDTO {
    
    private String transactionNumber;
    private TransactionTypeEnum transactionType;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    
    private Long lawFirmId;
    private Long caseId;
    private Long clientId;
    
    private LocalDateTime transactionTimeStart;
    private LocalDateTime transactionTimeEnd;
    
    private PaymentMethodEnum paymentMethod;
    private String createdBy;
} 