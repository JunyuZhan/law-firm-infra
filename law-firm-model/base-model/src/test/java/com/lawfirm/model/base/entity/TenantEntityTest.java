package com.lawfirm.model.base.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("租户实体测试")
class TenantEntityTest {

    @Test
    @DisplayName("测试租户基本属性")
    void testBasicProperties() {
        TenantEntity entity = new TenantEntity() {};
        
        // 设置基本属性
        Long tenantId = 1L;
        entity.setTenantId(tenantId);
        
        // 验证属性值
        assertEquals(tenantId, entity.getTenantId());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TenantEntity entity = new TenantEntity() {};
        assertNull(entity.getTenantId());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        TenantEntity entity = new TenantEntity() {}
            .setTenantId(1L);
        
        assertEquals(1L, entity.getTenantId());
    }

    @Test
    @DisplayName("测试继承关系")
    void testInheritance() {
        TenantEntity entity = new TenantEntity() {};
        assertTrue(entity instanceof ModelBaseEntity);
    }

    @Test
    @DisplayName("测试租户实体预处理方法")
    void testPreProcess() throws InterruptedException {
        TenantEntity entity = new TenantEntity() {};
        
        // 测试preInsert方法
        entity.preInsert();
        assertNotNull(entity.getCreateTime());
        assertNotNull(entity.getUpdateTime());
        
        // 测试preUpdate方法
        LocalDateTime previousUpdateTime = entity.getUpdateTime();
        TimeUnit.MILLISECONDS.sleep(10); // 添加10毫秒延时
        entity.preUpdate();
        assertNotEquals(previousUpdateTime, entity.getUpdateTime());
    }
} 