package com.lawfirm.model.base.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("基础数据传输对象测试")
class BaseDTOTest {

    @Test
    @DisplayName("测试基本属性设置和获取")
    void testBasicProperties() {
        BaseDTO dto = new BaseDTO();
        
        // 设置基本属性
        Long id = 1L;
        Long tenantId = 2L;
        String remark = "测试备注";
        
        dto.setId(id);
        dto.setTenantId(tenantId);
        dto.setRemark(remark);
        
        // 验证属性值
        assertEquals(id, dto.getId());
        assertEquals(tenantId, dto.getTenantId());
        assertEquals(remark, dto.getRemark());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        BaseDTO dto = new BaseDTO()
            .setId(1L)
            .setTenantId(2L)
            .setRemark("测试备注");
        
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getTenantId());
        assertEquals("测试备注", dto.getRemark());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        BaseDTO dto = new BaseDTO();
        
        assertNull(dto.getId());
        assertNull(dto.getTenantId());
        assertNull(dto.getRemark());
    }
} 