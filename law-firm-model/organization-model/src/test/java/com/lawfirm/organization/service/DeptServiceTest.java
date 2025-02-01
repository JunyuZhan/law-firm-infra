package com.lawfirm.organization.service;

import com.lawfirm.organization.model.entity.Dept;
import com.lawfirm.organization.model.dto.DeptDTO;
import com.lawfirm.organization.service.impl.DeptServiceImpl;
import com.lawfirm.organization.mapper.DeptMapper;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DeptServiceTest.TestConfig.class)
@ActiveProfiles("test")
@Transactional
class DeptServiceTest {

    @SpringBootApplication
    @ComponentScan(basePackages = {"com.lawfirm.organization.service", "com.lawfirm.organization.mapper"})
    static class TestConfig {
    }

    @Autowired
    private DeptService deptService;

    @MockBean
    private DeptMapper deptMapper;

    private Dept testDept;
    private DeptDTO testDeptDTO;

    @BeforeEach
    void setUp() {
        testDept = new Dept();
        testDept.setId(1L);
        testDept.setDeptName("测试部门");
        testDept.setParentId(0L);
        testDept.setOrderNum(1);
        testDept.setLeader("测试领导");
        testDept.setPhone("13800138000");
        testDept.setEmail("test@example.com");
        testDept.setAncestors("0");

        testDeptDTO = new DeptDTO();
        testDeptDTO.setId(1L);
        testDeptDTO.setDeptName("测试部门");
        testDeptDTO.setParentId(0L);
        testDeptDTO.setOrderNum(1);
        testDeptDTO.setLeader("测试领导");
        testDeptDTO.setPhone("13800138000");
        testDeptDTO.setEmail("test@example.com");
    }

    @Test
    void testCreateDept() {
        when(deptMapper.insert(any(Dept.class))).thenReturn(1);
        
        deptService.create(testDept);
        
        verify(deptMapper).insert(any(Dept.class));
    }

    @Test
    void testCreateDeptWithDuplicateName() {
        when(deptMapper.selectCount(any())).thenReturn(1L);
        
        assertThrows(BusinessException.class, () -> deptService.create(testDept));
    }

    @Test
    void testUpdateDept() {
        when(deptMapper.selectById(1L)).thenReturn(testDept);
        when(deptMapper.updateById(any(Dept.class))).thenReturn(1);
        
        deptService.update(testDept);
        
        verify(deptMapper).updateById(any(Dept.class));
    }

    @Test
    void testDeleteDept() {
        when(deptMapper.selectCount(any())).thenReturn(0L);
        when(deptMapper.deleteById(1L)).thenReturn(1);
        
        deptService.delete(1L);
        
        verify(deptMapper).deleteById(1L);
    }

    @Test
    void testDeleteDeptWithChildren() {
        when(deptMapper.selectCount(any())).thenReturn(1L);
        
        assertThrows(BusinessException.class, () -> deptService.delete(1L));
    }

    @Test
    void testGetDeptTree() {
        List<Dept> depts = Arrays.asList(testDept);
        when(deptMapper.selectList(any())).thenReturn(depts);
        
        List<DeptDTO> tree = deptService.getDeptTree();
        
        assertNotNull(tree);
        assertFalse(tree.isEmpty());
        assertEquals(testDept.getId(), tree.get(0).getId());
    }

    @Test
    void testMoveDept() {
        Dept parentDept = new Dept();
        parentDept.setId(2L);
        parentDept.setAncestors("0");
        
        when(deptMapper.selectById(2L)).thenReturn(parentDept);
        when(deptMapper.selectById(1L)).thenReturn(testDept);
        when(deptMapper.updateById(any(Dept.class))).thenReturn(1);
        
        deptService.moveDept(1L, 2L);
        
        verify(deptMapper, times(2)).updateById(any(Dept.class));
    }

    @Test
    void testReorderDept() {
        when(deptMapper.selectById(1L)).thenReturn(testDept);
        when(deptMapper.updateById(any(Dept.class))).thenReturn(1);
        
        deptService.reorderDept(1L, 2);
        
        verify(deptMapper).updateById(any(Dept.class));
    }
} 