package com.lawfirm.model.finance.dto.receivable;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 应收账款更新DTO
 */
@Data
public class ReceivableUpdateDTO {
    
    /**
     * 应收账款ID
     */
    private Long id;
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 应收金额
     */
    private BigDecimal amount;
    
    /**
     * 应收日期
     */
    private LocalDateTime dueDate;
    
    /**
     * 应收状态
     */
    private String status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 更新人ID
     */
    private Long updateBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 