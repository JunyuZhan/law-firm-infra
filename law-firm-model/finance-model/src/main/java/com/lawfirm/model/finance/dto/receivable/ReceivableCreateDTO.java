package com.lawfirm.model.finance.dto.receivable;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 应收账款创建DTO
 */
@Data
public class ReceivableCreateDTO {
    
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
     * 创建人ID
     */
    private Long createBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 