package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 证据质证VO
 */
@Data
@Schema(description = "证据质证VO")
public class EvidenceChallengeVO {
    @Schema(description = "质证ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "质证人ID")
    private Long challengerId;
    @Schema(description = "质证意见")
    private String opinion;
    @Schema(description = "质证时间")
    private LocalDateTime challengeTime;
    @Schema(description = "质证结论")
    private String conclusion;
    @Schema(description = "附件ID")
    private Long attachmentId;
    @Schema(description = "质证人姓名")
    private String challengerName;
} 