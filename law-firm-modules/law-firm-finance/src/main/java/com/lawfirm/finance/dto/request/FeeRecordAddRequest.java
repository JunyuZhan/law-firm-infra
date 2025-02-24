package com.lawfirm.finance.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import com.lawfirm.model.base.dto.BaseDTO;

/**
 * 收费记录添加请求
 */
@Data
public class FeeRecordAddRequest extends BaseDTO {
    
    /**
     * 收费金额
     */
    @NotNull(message = "收费金额不能为空")
    @DecimalMin(value = "0.01", message = "收费金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 收费类型
     */
    @NotNull(message = "收费类型不能为空")
    private String feeType;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
    /**
     * 关联客户ID
     */
    @NotNull(message = "客户ID不能为空")
    private Long clientId;
    
    /**
     * 关联律所ID
     */
    @NotNull(message = "律所ID不能为空")
    private Long lawFirmId;
    
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
} 