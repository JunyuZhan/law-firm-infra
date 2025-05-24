package com.lawfirm.model.evidence.dto;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据流转/溯源DTO
 */
@Data
@Schema(description = "证据流转DTO")
public class EvidenceTraceDTO {
    @Schema(description = "流转ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "操作类型")
    private String operationType;
    @Schema(description = "操作人ID")
    private Long operatorId;
    @Schema(description = "操作人名称")
    private String operatorName;
    @Schema(description = "操作时间")
    private LocalDateTime operationTime;
    @Schema(description = "节点")
    private String node;
    @Schema(description = "保存编号")
    private String preservationNo;
} 