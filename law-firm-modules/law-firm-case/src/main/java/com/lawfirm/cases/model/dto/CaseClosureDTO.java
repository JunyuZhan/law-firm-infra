package com.lawfirm.cases.model.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 案件结案DTO
 */
@Data
public class CaseClosureDTO {
    
    /**
     * 结案人ID
     */
    @NotNull(message = "结案人ID不能为空")
    private Long closerId;
    
    /**
     * 结案人姓名
     */
    @NotBlank(message = "结案人姓名不能为空")
    private String closerName;
    
    /**
     * 结案时间
     */
    @NotNull(message = "结案时间不能为空")
    private LocalDateTime closureTime;
    
    /**
     * 结案方式
     */
    @NotBlank(message = "结案方式不能为空")
    private String closureMethod;
    
    /**
     * 结案原因
     */
    @NotBlank(message = "结案原因不能为空")
    private String closureReason;
    
    /**
     * 实际收费金额
     */
    @NotNull(message = "实际收费金额不能为空")
    private BigDecimal actualFee;
    
    /**
     * 结案总结
     */
    @NotBlank(message = "结案总结不能为空")
    private String summary;
    
    /**
     * 备注
     */
    private String remarks;
} 