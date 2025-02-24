package com.lawfirm.model.base.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("基础视图对象测试")
class BaseVOTest {

    @Test
    @DisplayName("测试基本属性设置和获取")
    void testBasicProperties() {
        BaseVO vo = new BaseVO();
        
        // 设置基本属性
        Long id = 1L;
        Long tenantId = 2L;
        LocalDateTime createTime = LocalDateTime.now();
        String createBy = "admin";
        LocalDateTime updateTime = LocalDateTime.now();
        String updateBy = "manager";
        Integer status = 0;
        String remark = "测试备注";
        
        vo.setId(id);
        vo.setTenantId(tenantId);
        vo.setCreateTime(createTime);
        vo.setCreateBy(createBy);
        vo.setUpdateTime(updateTime);
        vo.setUpdateBy(updateBy);
        vo.setStatus(status);
        vo.setRemark(remark);
        
        // 验证属性值
        assertEquals(id, vo.getId());
        assertEquals(tenantId, vo.getTenantId());
        assertEquals(createTime, vo.getCreateTime());
        assertEquals(createBy, vo.getCreateBy());
        assertEquals(updateTime, vo.getUpdateTime());
        assertEquals(updateBy, vo.getUpdateBy());
        assertEquals(status, vo.getStatus());
        assertEquals(remark, vo.getRemark());
    }

    @Test
    @DisplayName("测试链式调用")
    void testChainedCalls() {
        LocalDateTime now = LocalDateTime.now();
        BaseVO vo = new BaseVO()
            .setId(1L)
            .setTenantId(2L)
            .setCreateTime(now)
            .setCreateBy("admin")
            .setUpdateTime(now)
            .setUpdateBy("manager")
            .setStatus(0)
            .setRemark("测试备注");
        
        assertEquals(1L, vo.getId());
        assertEquals(2L, vo.getTenantId());
        assertEquals(now, vo.getCreateTime());
        assertEquals("admin", vo.getCreateBy());
        assertEquals(now, vo.getUpdateTime());
        assertEquals("manager", vo.getUpdateBy());
        assertEquals(0, vo.getStatus());
        assertEquals("测试备注", vo.getRemark());
    }

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        BaseVO vo = new BaseVO();
        
        assertNull(vo.getId());
        assertNull(vo.getTenantId());
        assertNull(vo.getCreateTime());
        assertNull(vo.getCreateBy());
        assertNull(vo.getUpdateTime());
        assertNull(vo.getUpdateBy());
        assertNull(vo.getStatus());
        assertNull(vo.getRemark());
    }
} 