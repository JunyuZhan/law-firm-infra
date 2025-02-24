package com.lawfirm.finance.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import com.lawfirm.model.base.dto.BaseDTO;

/**
 * 收费记录更新请求
 */
@Data
public class FeeRecordUpdateRequest extends BaseDTO {
    
    /**
     * 记录ID
     */
    @NotNull(message = "记录ID不能为空")
    private Long id;
    
    /**
     * 收费金额
     */
    @DecimalMin(value = "0.01", message = "收费金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 收费类型
     */
    private String feeType;
    
    /**
     * 关联案件ID
     */
    private Long caseId;
    
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