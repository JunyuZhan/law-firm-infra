package com.lawfirm.model.evidence.dto;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据审核DTO
 */
@Data
@Schema(description = "证据审核DTO")
public class EvidenceReviewDTO {
    @Schema(description = "审核ID")
    private Long id;
    @Schema(description = "证据ID")
    private Long evidenceId;
    @Schema(description = "审核人ID")
    private Long reviewerId;
    @Schema(description = "审核人姓名")
    private String reviewerName;
    @Schema(description = "审核时间")
    private LocalDateTime reviewTime;
    @Schema(description = "审核意见")
    private String reviewOpinion;
    @Schema(description = "审核状态")
    private String reviewStatus;
} 