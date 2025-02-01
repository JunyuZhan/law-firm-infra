package com.lawfirm.model.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.base.tenant.TenantAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模型基础实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ModelBaseEntity extends BaseEntity implements Serializable, TenantAware, StatusAware {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableLogic
    private Integer delFlag;

    @TableField
    private Long tenantId;  // 租户ID

    @TableField
    private StatusEnum status = StatusEnum.ENABLED;  // 状态

    @TableField
    private Integer sort = 0;  // 排序号

    @TableField
    private String remark;  // 备注
} 