package com.lawfirm.model.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 合同冲突检查结果DTO
 */
@Data
@Schema(description = "合同冲突检查结果")
public class ContractConflictResultDTO {
    
    @Schema(description = "合同ID")
    private Long contractId;
    
    @Schema(description = "检查状态")
    private Integer checkStatus;
    
    @Schema(description = "检查时间")
    private Date checkTime;
    
    @Schema(description = "检查人ID")
    private Long checkerId;
    
    @Schema(description = "冲突类型")
    private String conflictType;
    
    @Schema(description = "冲突级别")
    private Integer conflictLevel;
    
    @Schema(description = "冲突描述")
    private String conflictDesc;
    
    @Schema(description = "冲突详情")
    private String conflictDetails;
    
    @Schema(description = "关联合同ID")
    private Long relatedContractId;
    
    @Schema(description = "解决方案")
    private String resolution;
    
    @Schema(description = "是否已解决")
    private Boolean isResolved;
} 