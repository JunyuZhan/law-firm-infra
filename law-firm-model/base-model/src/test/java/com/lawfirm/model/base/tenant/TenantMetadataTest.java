package com.lawfirm.model.base.tenant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("租户元数据测试")
class TenantMetadataTest {

    @Test
    @DisplayName("测试租户元数据基本属性")
    void testBasicProperties() {
        TenantMetadata metadata = new TenantMetadata();
        
        // 设置基本属性
        Long tenantId = 1L;
        String tenantCode = "TEST001";
        String tenantName = "测试租户";
        Integer status = 0;
        Long expireTime = System.currentTimeMillis() + 3600000; // 1小时后过期
        Integer maxUsers = 100;
        Long maxStorage = 1024L; // 1GB
        
        metadata.setTenantId(tenantId)
                .setTenantCode(tenantCode)
                .setTenantName(tenantName)
                .setStatus(status)
                .setExpireTime(expireTime)
                .setMaxUsers(maxUsers)
                .setMaxStorage(maxStorage);
        
        // 验证属性值
        assertEquals(tenantId, metadata.getTenantId());
        assertEquals(tenantCode, metadata.getTenantCode());
        assertEquals(tenantName, metadata.getTenantName());
        assertEquals(status, metadata.getStatus());
        assertEquals(expireTime, metadata.getExpireTime());
        assertEquals(maxUsers, metadata.getMaxUsers());
        assertEquals(maxStorage, metadata.getMaxStorage());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        TenantMetadata metadata = new TenantMetadata()
                .setTenantId(1L)
                .setTenantCode("TEST001")
                .setTenantName("测试租户")
                .setStatus(0)
                .setExpireTime(System.currentTimeMillis())
                .setMaxUsers(100)
                .setMaxStorage(1024L);
        
        assertNotNull(metadata);
        assertEquals("TEST001", metadata.getTenantCode());
        assertEquals("测试租户", metadata.getTenantName());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TenantMetadata metadata = new TenantMetadata();
        
        assertNull(metadata.getTenantId());
        assertNull(metadata.getTenantCode());
        assertNull(metadata.getTenantName());
        assertNull(metadata.getStatus());
        assertNull(metadata.getExpireTime());
        assertNull(metadata.getMaxUsers());
        assertNull(metadata.getMaxStorage());
    }

    @Test
    @DisplayName("测试状态值")
    void testStatus() {
        TenantMetadata metadata = new TenantMetadata();
        
        metadata.setStatus(0);
        assertEquals(0, metadata.getStatus()); // 启用状态
        
        metadata.setStatus(1);
        assertEquals(1, metadata.getStatus()); // 禁用状态
    }

    @Test
    @DisplayName("测试租户元数据构建器")
    void testBuilder() {
        TenantMetadata metadata = TenantMetadata.builder()
                .tenantCode("TEST001")
                .tenantName("测试租户")
                .contactPerson("张三")
                .contactPhone("13800138000")
                .contactEmail("test@example.com")
                .address("北京市朝阳区")
                .description("测试租户描述")
                .build();
        
        assertNotNull(metadata);
        assertEquals("TEST001", metadata.getTenantCode());
        assertEquals("测试租户", metadata.getTenantName());
        assertEquals("张三", metadata.getContactPerson());
        assertEquals("13800138000", metadata.getContactPhone());
        assertEquals("test@example.com", metadata.getContactEmail());
        assertEquals("北京市朝阳区", metadata.getAddress());
        assertEquals("测试租户描述", metadata.getDescription());
    }

    @Test
    @DisplayName("测试租户元数据验证")
    void testValidation() {
        TenantMetadata metadata = new TenantMetadata();
        
        // 测试必填字段
        assertThrows(IllegalArgumentException.class, () -> {
            metadata.validate();
        });
        
        // 设置必填字段
        metadata.setTenantCode("TEST001");
        metadata.setTenantName("测试租户");
        
        // 验证通过
        assertDoesNotThrow(() -> {
            metadata.validate();
        });
    }

    @Test
    @DisplayName("测试租户元数据克隆")
    void testClone() {
        TenantMetadata original = TenantMetadata.builder()
                .tenantCode("TEST001")
                .tenantName("测试租户")
                .build();
        
        TenantMetadata cloned = original.clone();
        
        assertNotNull(cloned);
        assertEquals(original.getTenantCode(), cloned.getTenantCode());
        assertEquals(original.getTenantName(), cloned.getTenantName());
        assertNotSame(original, cloned);
    }
} 