package com.lawfirm.model.finance.dto.business;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 合同财务更新DTO
 */
@Data
public class ContractFinanceUpdateDTO {
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 实际金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 备注
     */
    private String remark;
} 