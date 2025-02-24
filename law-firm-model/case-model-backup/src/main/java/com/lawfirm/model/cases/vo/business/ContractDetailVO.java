package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同详情VO
 */
@Data
public class ContractDetailVO {
    
    private Long id;
    
    private Long caseId;
    
    private String contractNumber;
    
    private String contractName;
    
    private String contractType;
    
    private String status;
    
    private String partyA;
    
    private String partyB;
    
    private BigDecimal amount;
    
    private String currency;
    
    private LocalDateTime signDate;
    
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expiryDate;
    
    private String content;
    
    private String attachments;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 