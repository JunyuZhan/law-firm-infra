package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据目录DTO
 * 用于证据目录的增删改查数据传输
 */
@Data
@Schema(description = "证据目录DTO")
public class EvidenceCatalogDTO {
    @Schema(description = "目录ID")
    private Long id;
    @Schema(description = "案件ID")
    private Long caseId;
    @Schema(description = "父级目录ID")
    private Long parentId;
    @Schema(description = "目录名称")
    private String name;
    @Schema(description = "备注说明")
    private String remark;
} 