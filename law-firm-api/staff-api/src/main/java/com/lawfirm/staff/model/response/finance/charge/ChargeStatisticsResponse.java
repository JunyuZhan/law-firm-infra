package com.lawfirm.staff.model.response.finance.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeStatisticsResponse {
    
    /**
     * 总收费金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 已确认金额
     */
    private BigDecimal confirmedAmount;
    
    /**
     * 待确认金额
     */
    private BigDecimal pendingAmount;
    
    /**
     * 已取消金额
     */
    private BigDecimal canceledAmount;
    
    /**
     * 总收费笔数
     */
    private Integer totalCount;
    
    /**
     * 已确认笔数
     */
    private Integer confirmedCount;
    
    /**
     * 待确认笔数
     */
    private Integer pendingCount;
    
    /**
     * 已取消笔数
     */
    private Integer canceledCount;
} 