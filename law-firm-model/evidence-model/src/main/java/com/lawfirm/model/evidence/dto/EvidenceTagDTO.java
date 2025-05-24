package com.lawfirm.model.evidence.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据标签DTO
 * 用于证据标签的数据传输
 */
@Data
@Schema(description = "证据标签DTO")
public class EvidenceTagDTO {
    @Schema(description = "标签ID")
    private Long id;
    @Schema(description = "标签名称")
    private String name;
    @Schema(description = "标签描述")
    private String description;
} 