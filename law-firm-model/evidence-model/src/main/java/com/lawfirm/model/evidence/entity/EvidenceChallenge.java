package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据质证过程实体
 * 记录证据在庭审、质证等环节的异议、意见、结论等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据质证实体")
public class EvidenceChallenge extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 质证人ID */
    @Schema(description = "质证人ID")
    private Long challengerId;
    /** 质证人姓名 */
    private String challengerName;
    /** 质证时间 */
    @Schema(description = "质证时间")
    private LocalDateTime challengeTime;
    /** 质证意见 */
    @Schema(description = "质证意见")
    private String opinion;
    /** 质证结论 */
    @Schema(description = "质证结论")
    private String conclusion;
    /** 相关附件ID */
    @Schema(description = "附件ID")
    private Long attachmentId;
}