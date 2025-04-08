package com.lawfirm.model.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 合同冲突检查DTO
 */
@Data
@Schema(description = "合同冲突检查DTO")
public class ContractConflictCheckDTO {
    
    @NotNull(message = "合同ID不能为空")
    @Schema(description = "合同ID")
    private Long contractId;
    
    @Schema(description = "检查类型列表")
    private List<String> checkTypes;
    
    @Schema(description = "是否检查历史合同")
    private Boolean checkHistory = true;
    
    @Schema(description = "检查时间范围-开始时间")
    private String startTime;
    
    @Schema(description = "检查时间范围-结束时间")
    private String endTime;
    
    @Schema(description = "检查人ID")
    private Long checkerId;
} 