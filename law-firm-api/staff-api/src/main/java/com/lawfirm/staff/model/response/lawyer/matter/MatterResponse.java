package com.lawfirm.staff.model.response.lawyer.matter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "案件响应")
public class MatterResponse {
    
    @Schema(description = "案件ID")
    private Long id;
    
    @Schema(description = "案件编号")
    private String code;
    
    @Schema(description = "案件名称")
    private String name;
    
    @Schema(description = "案件类型")
    private Integer type;
    
    @Schema(description = "案件状态")
    private Integer status;
    
    @Schema(description = "委托人ID")
    private Long clientId;
    
    @Schema(description = "委托人名称")
    private String clientName;
    
    @Schema(description = "主办律师ID")
    private Long lawyerId;
    
    @Schema(description = "主办律师姓名")
    private String lawyerName;
    
    @Schema(description = "协办律师ID列表")
    private String assistantLawyerIds;
    
    @Schema(description = "协办律师姓名列表")
    private String assistantLawyerNames;
    
    @Schema(description = "案件金额")
    private BigDecimal amount;
    
    @Schema(description = "案件描述")
    private String description;
    
    @Schema(description = "立案时间")
    private LocalDateTime filingTime;
    
    @Schema(description = "结案时间")
    private LocalDateTime closingTime;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 