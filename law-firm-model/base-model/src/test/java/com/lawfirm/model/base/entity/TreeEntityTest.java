package com.lawfirm.model.base.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("树形实体测试")
class TreeEntityTest {

    @Test
    @DisplayName("测试树形实体基本属性")
    void testBasicProperties() {
        TreeEntity entity = new TreeEntity() {};
        
        // 设置基本属性
        Long parentId = 1L;
        Integer level = 2;
        String path = "/root/node1";
        Boolean leaf = true;
        
        entity.setParentId(parentId);
        entity.setLevel(level);
        entity.setPath(path);
        entity.setLeaf(leaf);
        
        // 验证属性值
        assertEquals(parentId, entity.getParentId());
        assertEquals(level, entity.getLevel());
        assertEquals(path, entity.getPath());
        assertEquals(leaf, entity.getLeaf());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TreeEntity entity = new TreeEntity() {};
        
        assertNull(entity.getParentId());
        assertNull(entity.getLevel());
        assertNull(entity.getPath());
        assertNull(entity.getLeaf());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        TreeEntity entity = new TreeEntity() {}
            .setParentId(1L)
            .setLevel(1)
            .setPath("/root")
            .setLeaf(false);
        
        assertEquals(1L, entity.getParentId());
        assertEquals(1, entity.getLevel());
        assertEquals("/root", entity.getPath());
        assertFalse(entity.getLeaf());
    }

    @Test
    @DisplayName("测试继承关系")
    void testInheritance() {
        TreeEntity entity = new TreeEntity() {};
        assertTrue(entity instanceof ModelBaseEntity);
    }
} 