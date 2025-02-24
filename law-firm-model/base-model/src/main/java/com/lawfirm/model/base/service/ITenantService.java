package com.lawfirm.model.base.service;

import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.base.tenant.TenantConfig;
import com.lawfirm.model.base.tenant.TenantMetadata;

/**
 * 租户服务接口
 */
public interface ITenantService<T extends TenantEntity> extends BaseService<T> {

    /**
     * 获取租户元数据
     */
    TenantMetadata getMetadata(Long tenantId);

    /**
     * 获取租户配置
     */
    TenantConfig getConfig(Long tenantId);

    /**
     * 更新租户配置
     */
    void updateConfig(Long tenantId, TenantConfig config);

    /**
     * 初始化租户
     */
    void initialize(Long tenantId);

    /**
     * 清理租户数据
     */
    void cleanup(Long tenantId);

    /**
     * 检查租户是否有效
     */
    boolean isValid(Long tenantId);

    /**
     * 获取当前租户ID
     */
    Long getCurrentTenantId();

    /**
     * 切换租户
     */
    void switchTenant(Long tenantId);
} 