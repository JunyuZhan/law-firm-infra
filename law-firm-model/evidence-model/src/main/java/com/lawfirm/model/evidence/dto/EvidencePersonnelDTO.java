package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据人员DTO
 */
@Data
@Schema(description = "证据人员DTO")
public class EvidencePersonnelDTO {
    @Schema(description = "人员ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "人员ID")
    private Long personnelId;
    @Schema(description = "角色类型")
    private String roleType;
} 