package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 证据事实关联VO
 */
@Data
@Schema(description = "证据事实关联VO")
public class EvidenceFactRelationVO {
    @Schema(description = "关联ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "事实ID")
    private Long factId;
    @Schema(description = "事实描述")
    private String factDescription;
    @Schema(description = "证明事项")
    private String proofMatter;
    @Schema(description = "证明等级")
    private String proofLevel;
} 