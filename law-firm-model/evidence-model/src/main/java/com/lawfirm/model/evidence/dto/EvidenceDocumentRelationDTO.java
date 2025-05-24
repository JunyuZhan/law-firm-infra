package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据-文档关联DTO
 * 用于描述证据与文档的关联关系
 */
@Data
@Schema(description = "证据-文档关联DTO")
public class EvidenceDocumentRelationDTO {
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "文档ID")
    private Long documentId;
    @Schema(description = "关系类型")
    private String relationType;
} 