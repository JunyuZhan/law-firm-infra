package com.lawfirm.finance.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.lawfirm.common.data.dto.BaseDTO;

@Data
public class FeeRecordResponse extends BaseDTO {
    
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 收费金额
     */
    private BigDecimal amount;

    /**
     * 已支付金额
     */
    private BigDecimal paidAmount;

    /**
     * 收费状态
     */
    private String feeStatus;

    /**
     * 收费类型
     */
    private String feeType;

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
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 收费说明
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
} 