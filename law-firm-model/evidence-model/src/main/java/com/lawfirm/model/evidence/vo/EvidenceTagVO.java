package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 证据标签VO
 * 用于前端展示证据标签信息
 */
@Data
@Schema(description = "证据标签VO")
public class EvidenceTagVO {
    @Schema(description = "标签ID")
    private Long id;
    @Schema(description = "标签名称")
    private String name;
    @Schema(description = "标签描述")
    private String description;
    @Schema(description = "标签颜色")
    private String color;
} 