package com.lawfirm.model.base.support.cache;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 缓存键
 */
@Data
@Accessors(chain = true)
public class CacheKey {

    /**
     * 模块名称
     */
    private String module;

    /**
     * 业务类型
     */
    private String business;

    /**
     * 键名
     */
    private String key;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 构建缓存键
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        if (tenantId != null) {
            sb.append(tenantId).append(":");
        }
        if (module != null) {
            sb.append(module).append(":");
        }
        if (business != null) {
            sb.append(business).append(":");
        }
        sb.append(key);
        return sb.toString();
    }

    /**
     * 创建缓存键构建器
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CacheKey cacheKey = new CacheKey();

        public Builder module(String module) {
            cacheKey.setModule(module);
            return this;
        }

        public Builder business(String business) {
            cacheKey.setBusiness(business);
            return this;
        }

        public Builder key(String key) {
            cacheKey.setKey(key);
            return this;
        }

        public Builder tenantId(Long tenantId) {
            cacheKey.setTenantId(tenantId);
            return this;
        }

        public CacheKey build() {
            return cacheKey;
        }
    }
} 