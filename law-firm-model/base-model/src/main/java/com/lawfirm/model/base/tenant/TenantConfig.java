package com.lawfirm.model.base.tenant;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户配置
 */
@Data
@Accessors(chain = true)
public class TenantConfig {

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;

    /**
     * 缓存配置
     */
    private CacheConfig cache;

    /**
     * 存储配置
     */
    private StorageConfig storage;

    @Data
    @Accessors(chain = true)
    public static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }

    @Data
    @Accessors(chain = true)
    public static class CacheConfig {
        private String type;
        private String host;
        private Integer port;
        private String password;
        private Integer database;
    }

    @Data
    @Accessors(chain = true)
    public static class StorageConfig {
        private String type;
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;
    }
} 