package com.lawfirm.model.organization.service;

import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.vo.DepartmentVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    private Department testDepartment;
    private DepartmentVO testDepartmentVO;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setName("测试部门");
        testDepartment.setCode("TEST_DEPT");
        testDepartment.setManager("测试经理");
        testDepartment.setPhone("13800138000");
        testDepartment.setParentId(0L);
        testDepartment.setSortOrder(1);
        testDepartment.setEnabled(true);
        testDepartment.setType("FUNCTIONAL");
        testDepartment.setLawFirmId(1L);
        testDepartment.setDescription("测试部门描述");

        testDepartmentVO = new DepartmentVO();
        testDepartmentVO.setName("测试部门");
        testDepartmentVO.setCode("TEST_DEPT");
        testDepartmentVO.setManager("测试经理");
        testDepartmentVO.setPhone("13800138000");
        testDepartmentVO.setParentId(0L);
        testDepartmentVO.setSortOrder(1);
        testDepartmentVO.setEnabled(true);
        testDepartmentVO.setType("FUNCTIONAL");
        testDepartmentVO.setLawFirmId(1L);
        testDepartmentVO.setDescription("测试部门描述");
    }

    @Test
    void testCreateDepartment() {
        DepartmentVO created = departmentService.create(testDepartmentVO);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(testDepartmentVO.getName(), created.getName());
    }

    @Test
    void testUpdateDepartment() {
        DepartmentVO created = departmentService.create(testDepartmentVO);
        created.setName("更新后的部门名称");
        DepartmentVO updated = departmentService.update(created);
        assertEquals("更新后的部门名称", updated.getName());
    }

    @Test
    void testDeleteDepartment() {
        DepartmentVO created = departmentService.create(testDepartmentVO);
        departmentService.delete(created.getId());
        assertThrows(RuntimeException.class, () -> departmentService.getById(created.getId()));
    }

    @Test
    void testGetDepartmentTree() {
        departmentService.create(testDepartmentVO);
        List<DepartmentVO> tree = departmentService.getTree();
        assertNotNull(tree);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testGetChildren() {
        DepartmentVO parent = departmentService.create(testDepartmentVO);
        
        DepartmentVO child = new DepartmentVO();
        child.setName("子部门");
        child.setParentId(parent.getId());
        child.setCode("CHILD_DEPT");
        child.setSortOrder(1);
        child.setEnabled(true);
        departmentService.create(child);

        List<DepartmentVO> children = departmentService.getChildren(parent.getId());
        assertNotNull(children);
        assertEquals(1, children.size());
    }
} 