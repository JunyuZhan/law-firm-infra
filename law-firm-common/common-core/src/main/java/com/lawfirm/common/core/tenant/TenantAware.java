package com.lawfirm.common.core.tenant;

/**
 * 租户感知接口
 */
public interface TenantAware {
    /**
     * 获取租户ID
     */
    Long getTenantId();

    /**
     * 设置租户ID
     */
    void setTenantId(Long tenantId);
}