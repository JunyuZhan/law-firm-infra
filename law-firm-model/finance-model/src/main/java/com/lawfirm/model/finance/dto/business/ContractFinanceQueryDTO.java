package com.lawfirm.model.finance.dto.business;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 合同财务查询DTO
 */
@Data
public class ContractFinanceQueryDTO {
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 合同编号
     */
    private String contractNo;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 