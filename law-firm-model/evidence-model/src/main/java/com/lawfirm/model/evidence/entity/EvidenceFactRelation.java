package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据与案件事实/争议点关联实体
 * 支持证据与案件事实、争议焦点的关联
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据-事实关联实体")
public class EvidenceFactRelation extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 事实ID（或争议点ID） */
    @Schema(description = "事实ID")
    private Long factId;
    /** 证明事项 */
    @Schema(description = "证明事项")
    private String proofMatter;
    /** 证明力等级（如强、中、弱） */
    @Schema(description = "证明力等级")
    private String proofLevel;
} 