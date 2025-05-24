package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据-事实关联DTO
 */
@Data
@Schema(description = "证据-事实关联DTO")
public class EvidenceFactRelationDTO {
    @Schema(description = "关联ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "事实ID")
    private Long factId;
    @Schema(description = "证明事项")
    private String proofMatter;
    @Schema(description = "证明力等级")
    private String proofLevel;
} 