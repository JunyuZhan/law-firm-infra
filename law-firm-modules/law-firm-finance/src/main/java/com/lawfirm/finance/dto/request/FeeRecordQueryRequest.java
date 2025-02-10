package com.lawfirm.finance.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.lawfirm.common.data.dto.BaseDTO;

@Data
public class FeeRecordQueryRequest extends BaseDTO {
    
    /**
     * 收费类型
     */
    private String feeType;

    /**
     * 收费状态
     */
    private String feeStatus;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联律所ID
     */
    private Long lawFirmId;

    /**
     * 最小金额
     */
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

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