package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据与文档多对多关联实体
 * 用于描述一份证据与多份文档（原件、复印件等）的关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据-文档关联实体")
public class EvidenceDocumentRelation extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 文档ID */
    @Schema(description = "文档ID")
    private Long documentId;
    /** 关系类型（如原件、复印件、翻译件等） */
    @Schema(description = "关系类型")
    private String relationType; // 例如原件/复印件/翻译件等
}