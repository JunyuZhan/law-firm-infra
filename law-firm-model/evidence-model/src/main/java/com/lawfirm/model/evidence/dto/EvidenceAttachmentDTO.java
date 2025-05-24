package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据附件DTO
 */
@Data
@Schema(description = "证据附件DTO")
public class EvidenceAttachmentDTO {
    @Schema(description = "附件ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "附件类型")
    private String attachmentType;
    @Schema(description = "文件ID")
    private Long fileId;
    @Schema(description = "描述")
    private String description;
} 