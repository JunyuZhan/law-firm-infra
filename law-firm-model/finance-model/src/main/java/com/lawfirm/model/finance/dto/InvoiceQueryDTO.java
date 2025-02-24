package com.lawfirm.model.finance.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceQueryDTO extends BaseDTO {
    
    private String invoiceNumber;
    private String title;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    
    private Long lawFirmId;
    private Long caseId;
    private Long clientId;
    
    private LocalDateTime issueTimeStart;
    private LocalDateTime issueTimeEnd;
    
    private String taxpayerNumber;
    private String issuedBy;
    private String createdBy;
} 