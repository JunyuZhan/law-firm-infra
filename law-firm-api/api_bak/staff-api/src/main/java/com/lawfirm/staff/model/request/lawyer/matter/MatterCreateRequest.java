package com.lawfirm.staff.model.request.lawyer.matter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "案件创建请求")
public class MatterCreateRequest {
    
    @NotBlank(message = "案件名称不能为空")
    @Schema(description = "案件名称")
    private String name;
    
    @NotNull(message = "案件类型不能为空")
    @Schema(description = "案件类型")
    private Integer type;
    
    @NotNull(message = "委托人不能为空")
    @Schema(description = "委托人ID")
    private Long clientId;
    
    @NotNull(message = "主办律师不能为空")
    @Schema(description = "主办律师ID")
    private Long lawyerId;
    
    @Schema(description = "协办律师ID列表")
    private String assistantLawyerIds;
    
    @Schema(description = "案件金额")
    private BigDecimal amount;
    
    @Schema(description = "案件描述")
    private String description;
    
    @Schema(description = "立案时间")
    private LocalDateTime filingTime;
} 