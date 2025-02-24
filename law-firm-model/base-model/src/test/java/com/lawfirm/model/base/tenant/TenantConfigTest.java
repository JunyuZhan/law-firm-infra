package com.lawfirm.model.base.tenant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("租户配置测试")
class TenantConfigTest {

    @Test
    @DisplayName("测试租户配置基本属性")
    void testBasicProperties() {
        TenantConfig config = new TenantConfig();
        
        // 设置基本属性
        Long tenantId = 1L;
        
        // 设置数据源配置
        TenantConfig.DataSourceConfig dataSource = new TenantConfig.DataSourceConfig()
                .setUrl("jdbc:mysql://localhost:3306/test")
                .setUsername("root")
                .setPassword("123456")
                .setDriverClassName("com.mysql.cj.jdbc.Driver");
                
        // 设置缓存配置
        TenantConfig.CacheConfig cache = new TenantConfig.CacheConfig()
                .setType("redis")
                .setHost("localhost")
                .setPort(6379)
                .setPassword("123456")
                .setDatabase(0);
                
        // 设置存储配置
        TenantConfig.StorageConfig storage = new TenantConfig.StorageConfig()
                .setType("minio")
                .setEndpoint("http://localhost:9000")
                .setAccessKey("minioadmin")
                .setSecretKey("minioadmin")
                .setBucket("test");
        
        config.setTenantId(tenantId)
              .setDataSource(dataSource)
              .setCache(cache)
              .setStorage(storage);
        
        // 验证属性值
        assertEquals(tenantId, config.getTenantId());
        assertNotNull(config.getDataSource());
        assertNotNull(config.getCache());
        assertNotNull(config.getStorage());
        
        // 验证数据源配置
        assertEquals("jdbc:mysql://localhost:3306/test", config.getDataSource().getUrl());
        assertEquals("root", config.getDataSource().getUsername());
        assertEquals("123456", config.getDataSource().getPassword());
        assertEquals("com.mysql.cj.jdbc.Driver", config.getDataSource().getDriverClassName());
        
        // 验证缓存配置
        assertEquals("redis", config.getCache().getType());
        assertEquals("localhost", config.getCache().getHost());
        assertEquals(6379, config.getCache().getPort());
        assertEquals("123456", config.getCache().getPassword());
        assertEquals(0, config.getCache().getDatabase());
        
        // 验证存储配置
        assertEquals("minio", config.getStorage().getType());
        assertEquals("http://localhost:9000", config.getStorage().getEndpoint());
        assertEquals("minioadmin", config.getStorage().getAccessKey());
        assertEquals("minioadmin", config.getStorage().getSecretKey());
        assertEquals("test", config.getStorage().getBucket());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        TenantConfig config = new TenantConfig()
                .setTenantId(1L)
                .setDataSource(new TenantConfig.DataSourceConfig()
                        .setUrl("jdbc:mysql://localhost:3306/test"))
                .setCache(new TenantConfig.CacheConfig()
                        .setType("redis"))
                .setStorage(new TenantConfig.StorageConfig()
                        .setType("minio"));
        
        assertNotNull(config);
        assertEquals(1L, config.getTenantId());
        assertEquals("jdbc:mysql://localhost:3306/test", config.getDataSource().getUrl());
        assertEquals("redis", config.getCache().getType());
        assertEquals("minio", config.getStorage().getType());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TenantConfig config = new TenantConfig();
        
        assertNull(config.getTenantId());
        assertNull(config.getDataSource());
        assertNull(config.getCache());
        assertNull(config.getStorage());
    }

    @Test
    @DisplayName("测试内部配置类")
    void testInnerConfigs() {
        // 测试数据源配置
        TenantConfig.DataSourceConfig dataSource = new TenantConfig.DataSourceConfig();
        assertNull(dataSource.getUrl());
        assertNull(dataSource.getUsername());
        assertNull(dataSource.getPassword());
        assertNull(dataSource.getDriverClassName());
        
        // 测试缓存配置
        TenantConfig.CacheConfig cache = new TenantConfig.CacheConfig();
        assertNull(cache.getType());
        assertNull(cache.getHost());
        assertNull(cache.getPort());
        assertNull(cache.getPassword());
        assertNull(cache.getDatabase());
        
        // 测试存储配置
        TenantConfig.StorageConfig storage = new TenantConfig.StorageConfig();
        assertNull(storage.getType());
        assertNull(storage.getEndpoint());
        assertNull(storage.getAccessKey());
        assertNull(storage.getSecretKey());
        assertNull(storage.getBucket());
    }
} 