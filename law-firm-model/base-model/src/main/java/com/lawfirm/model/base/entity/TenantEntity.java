package com.lawfirm.model.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 租户实体基类
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class TenantEntity extends ModelBaseEntity {

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 租户编码
     */
    @TableField("tenant_code")
    private String tenantCode;
} 