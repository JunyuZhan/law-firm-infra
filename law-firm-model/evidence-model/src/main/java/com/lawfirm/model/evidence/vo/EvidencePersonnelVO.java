package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 证据人员VO
 */
@Data
@Schema(description = "证据人员VO")
public class EvidencePersonnelVO {
    @Schema(description = "人员ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "人员姓名")
    private String name;
    @Schema(description = "人员角色")
    private String role;
    @Schema(description = "人员唯一ID")
    private Long personnelId;
    @Schema(description = "角色类型")
    private String roleType;
} 