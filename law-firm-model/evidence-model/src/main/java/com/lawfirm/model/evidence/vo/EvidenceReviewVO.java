package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 证据审核VO
 */
@Data
@Schema(description = "证据审核VO")
public class EvidenceReviewVO {
    @Schema(description = "复核ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "复核人ID")
    private Long reviewerId;
    @Schema(description = "复核人名称")
    private String reviewerName;
    @Schema(description = "复核时间")
    private LocalDateTime reviewTime;
    @Schema(description = "复核意见")
    private String reviewOpinion;
    @Schema(description = "复核状态")
    private String reviewStatus;
} 