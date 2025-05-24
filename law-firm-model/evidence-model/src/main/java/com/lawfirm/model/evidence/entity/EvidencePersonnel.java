package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据与人员/角色关联实体
 * 记录证据的提交人、保管人、质证人、鉴定人等多角色信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据人员实体")
public class EvidencePersonnel extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 人员ID */
    @Schema(description = "人员ID")
    private Long personnelId;
    /** 角色类型（提交人、保管人、质证人、鉴定人等） */
    @Schema(description = "角色类型")
    private String roleType;
} 