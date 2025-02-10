package com.lawfirm.model.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.common.core.status.StatusAware;
import com.lawfirm.common.core.tenant.TenantAware;
import com.lawfirm.common.data.entity.DataBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 业务模型基础实体类，扩展数据库实体，添加业务字段
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class ModelBaseEntity<T extends ModelBaseEntity<T>> extends DataBaseEntity<T> implements TenantAware, StatusAware {

    /**
     * 版本号
     */
    @Version
    @TableField("version")
    private Integer version;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort = 0;

    @Override
    public StatusEnum getStatus() {
        if (status == null) {
            return StatusEnum.ENABLED;
        }
        return status == 0 ? StatusEnum.ENABLED : StatusEnum.DISABLED;
    }

    @Override
    public void setStatus(StatusEnum statusEnum) {
        if (statusEnum == null) {
            this.status = 0;
            return;
        }
        this.status = statusEnum == StatusEnum.ENABLED ? 0 : 1;
    }

    @Override
    public Long getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @SuppressWarnings("unchecked")
    public T setSort(Integer sort) {
        this.sort = sort;
        return (T) this;
    }
} 