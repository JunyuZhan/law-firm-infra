package com.lawfirm.model.finance.service;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同收入统计数据
 */
@Data
public class ContractIncomeStat {
    
    /**
     * 案件ID（替代合同ID）
     */
    private Long caseId;
    
    /**
     * 收入总金额
     */
    private BigDecimal amount;
    
    /**
     * 收入笔数
     */
    private Integer count;
} 