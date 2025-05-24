package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 证据流转/溯源VO
 */
@Data
@Schema(description = "证据流转VO")
public class EvidenceTraceVO {
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
    @Schema(description = "备注")
    private String remark;
} 