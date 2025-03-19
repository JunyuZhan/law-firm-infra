package com.lawfirm.model.finance.dto.receivable;

import lombok.Data;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import java.time.LocalDateTime;

/**
 * 应收账款查询DTO
 */
@Data
public class ReceivableQueryDTO {
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 客户ID
     */
    private Long clientId;
    
    /**
     * 应收状态
     */
    private ReceivableStatusEnum status;
    
    /**
     * 逾期天数
     */
    private Integer overdueDays;
    
    /**
     * 应收日期范围-开始
     */
    private LocalDateTime dueDateStart;
    
    /**
     * 应收日期范围-结束
     */
    private LocalDateTime dueDateEnd;
    
    /**
     * 创建日期范围-开始
     */
    private LocalDateTime createTimeStart;
    
    /**
     * 创建日期范围-结束
     */
    private LocalDateTime createTimeEnd;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 