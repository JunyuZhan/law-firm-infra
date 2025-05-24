package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 证据附件VO
 */
@Data
@Schema(description = "证据附件VO")
public class EvidenceAttachmentVO {
    @Schema(description = "附件ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "文件名")
    private String fileName;
    @Schema(description = "文件URL")
    private String fileUrl;
    @Schema(description = "上传时间")
    private String uploadTime;
    @Schema(description = "附件类型")
    private String attachmentType;
    @Schema(description = "文件ID")
    private Long fileId;
    @Schema(description = "描述")
    private String description;
} 