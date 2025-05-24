package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据-标签关联DTO
 */
@Data
@Schema(description = "证据-标签关联DTO")
public class EvidenceTagRelationDTO {
    @Schema(description = "关联ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "标签ID")
    private Long tagId;
} 