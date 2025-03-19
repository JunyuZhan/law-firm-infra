package com.lawfirm.model.finance.dto.business;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同财务DTO
 */
@Data
public class ContractFinanceDTO {
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 合同编号
     */
    private String contractNo;
    
    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    
    /**
     * 实际金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 已收款金额
     */
    private BigDecimal receivedAmount;
    
    /**
     * 未收款金额
     */
    private BigDecimal unreceivedAmount;
    
    /**
     * 已开票金额
     */
    private BigDecimal invoicedAmount;
    
    /**
     * 未开票金额
     */
    private BigDecimal uninvoicedAmount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
} 