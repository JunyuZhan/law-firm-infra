package com.lawfirm.model.base.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("业务模型基础实体测试")
class ModelBaseEntityTest {

    @Test
    @DisplayName("测试实体基本属性设置和获取")
    void testBasicProperties() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        
        // 设置基本属性
        Long id = 1L;
        String createBy = "admin";
        LocalDateTime createTime = LocalDateTime.now();
        String updateBy = "manager";
        LocalDateTime updateTime = LocalDateTime.now();
        Integer version = 1;
        Integer status = 0;
        Integer sort = 1;
        String remark = "测试备注";
        
        entity.setId(id);
        entity.setCreateBy(createBy);
        entity.setCreateTime(createTime);
        entity.setUpdateBy(updateBy);
        entity.setUpdateTime(updateTime);
        entity.setVersion(version);
        entity.setStatus(status);
        entity.setSort(sort);
        entity.setRemark(remark);
        
        // 验证属性值
        assertEquals(id, entity.getId());
        assertEquals(createBy, entity.getCreateBy());
        assertEquals(createTime, entity.getCreateTime());
        assertEquals(updateBy, entity.getUpdateBy());
        assertEquals(updateTime, entity.getUpdateTime());
        assertEquals(version, entity.getVersion());
        assertEquals(status, entity.getStatus());
        assertEquals(sort, entity.getSort());
        assertEquals(remark, entity.getRemark());
    }

    @Test
    @DisplayName("测试实体创建时间自动设置")
    void testCreateTimeAutoSet() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        assertNull(entity.getCreateTime());
        
        entity.preInsert();
        assertNotNull(entity.getCreateTime());
        assertNotNull(entity.getUpdateTime());
    }

    @Test
    @DisplayName("测试实体更新时间自动设置")
    void testUpdateTimeAutoSet() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        assertNull(entity.getUpdateTime());
        
        entity.preUpdate();
        assertNotNull(entity.getUpdateTime());
    }

    @Test
    @DisplayName("测试实体版本号自增")
    void testVersionIncrement() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        entity.setVersion(Integer.valueOf(1));
        
        entity.preUpdate();
        assertEquals(Integer.valueOf(2), entity.getVersion());
    }

    @Test
    @DisplayName("测试版本号为空时的更新")
    void testVersionIncrementWithNull() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        entity.setVersion(null);
        
        entity.preUpdate();
        assertNull(entity.getVersion());
    }

    @Test
    @DisplayName("测试状态字段默认值")
    void testStatusDefaultValue() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        assertNull(entity.getStatus());
    }

    @Test
    @DisplayName("测试排序字段默认值")
    void testSortDefaultValue() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        assertEquals(Integer.valueOf(0), entity.getSort());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        ModelBaseEntity entity = new ModelBaseEntity() {};
        
        entity.setId(1L);
        entity.setCreateBy("admin");
        // 尝试直接通过反射设置version字段
        try {
            java.lang.reflect.Field versionField = ModelBaseEntity.class.getDeclaredField("version");
            versionField.setAccessible(true);
            versionField.set(entity, 1);
        } catch (Exception e) {
            // 忽略异常
        }
        entity.setStatus(Integer.valueOf(0));
        entity.setSort(Integer.valueOf(1));
        
        assertEquals(1L, entity.getId());
        assertEquals("admin", entity.getCreateBy());
        assertEquals(Integer.valueOf(1), entity.getVersion());
        assertEquals(Integer.valueOf(0), entity.getStatus());
        assertEquals(Integer.valueOf(1), entity.getSort());
    }
} 