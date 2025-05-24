package com.lawfirm.model.evidence.dto;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据质证DTO
 */
@Data
@Schema(description = "证据质证DTO")
public class EvidenceChallengeDTO {
    @Schema(description = "质证ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "质证人ID")
    private Long challengerId;
    @Schema(description = "质证人姓名")
    private String challengerName;
    @Schema(description = "质证时间")
    private LocalDateTime challengeTime;
    @Schema(description = "质证意见")
    private String opinion;
    @Schema(description = "质证结论")
    private String conclusion;
    @Schema(description = "附件ID")
    private Long attachmentId;
} 