package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据目录实体
 * 支持树形结构，用于对案件证据进行分组、归类
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据目录实体")
public class EvidenceCatalog extends TreeEntity {
    private static final long serialVersionUID = 1L;
    /** 关联案件ID */
    private Long caseId;
    @Schema(description = "目录名称")
    private String name;
} 