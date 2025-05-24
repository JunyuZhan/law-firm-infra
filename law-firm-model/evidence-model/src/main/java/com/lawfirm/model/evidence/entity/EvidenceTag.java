package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据标签实体
 * 用于对证据进行多标签管理，如"关键证据""原件"等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据标签实体")
public class EvidenceTag extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 标签名称 */
    @Schema(description = "标签名称")
    private String name;
    /** 标签描述 */
    @Schema(description = "标签描述")
    private String description;
    @Schema(description = "标签颜色")
    private String color;
} 