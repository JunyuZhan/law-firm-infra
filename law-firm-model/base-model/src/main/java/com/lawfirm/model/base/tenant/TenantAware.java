package com.lawfirm.model.base.tenant;

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
    
    default boolean isGlobal() {  // 判断是否为全局数据
        return getTenantId() == null || getTenantId() == 0L;
    }
} 