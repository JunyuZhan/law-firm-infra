package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据附件实体
 * 支持证据下多种附件（如图片、音视频、鉴定报告等）的管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据附件实体")
public class EvidenceAttachment extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 附件类型（图片、音视频、报告等） */
    @Schema(description = "附件类型")
    private String attachmentType;
    /** 文件ID */
    @Schema(description = "文件ID")
    private Long fileId;
    /** 附件描述 */
    @Schema(description = "描述")
    private String description;
} 