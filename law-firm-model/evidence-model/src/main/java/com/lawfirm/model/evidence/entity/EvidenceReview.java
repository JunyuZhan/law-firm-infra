package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据审核/审批实体
 * 支持证据提交后的内部审核、审批流程
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据审核/审批实体")
public class EvidenceReview extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 审核人ID */
    @Schema(description = "审核人ID")
    private Long reviewerId;
    /** 审核人姓名 */
    @Schema(description = "审核人姓名")
    private String reviewerName;
    /** 审核时间 */
    @Schema(description = "审核时间")
    private LocalDateTime reviewTime;
    /** 审核意见 */
    @Schema(description = "审核意见")
    private String reviewOpinion;
    /** 审核状态（待审核、通过、驳回等） */
    @Schema(description = "审核状态")
    private String reviewStatus;
} 