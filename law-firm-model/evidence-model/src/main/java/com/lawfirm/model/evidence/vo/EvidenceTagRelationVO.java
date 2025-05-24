package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 证据标签关联VO
 */
@Data
@Schema(description = "证据标签关联VO")
public class EvidenceTagRelationVO {
    @Schema(description = "关联ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "标签ID")
    private Long tagId;
    @Schema(description = "标签名称")
    private String tagName;
} 