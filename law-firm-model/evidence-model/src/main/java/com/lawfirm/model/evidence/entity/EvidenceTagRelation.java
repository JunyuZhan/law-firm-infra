package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据-标签多对多关联实体
 * 用于描述证据与标签的关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据-标签关联实体")
public class EvidenceTagRelation extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 标签ID */
    @Schema(description = "标签ID")
    private Long tagId;
} 