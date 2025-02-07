package com.lawfirm.system.controller;

import com.lawfirm.model.system.vo.SysRoleVO;
import com.lawfirm.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SysRoleControllerTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void createRole() {
        SysRoleVO role = new SysRoleVO();
        role.setRoleName("测试角色");
        role.setRoleCode("TEST_ROLE");
        role.setDescription("用于测试的角色");
        role.setDataScope(1);
        role.setIsDefault(false);
        
        SysRoleVO createdRole = sysRoleService.createRole(role);
        assertNotNull(createdRole);
        assertEquals("测试角色", createdRole.getRoleName());
    }

    @Test
    public void updateRole() {
        SysRoleVO role = new SysRoleVO();
        role.setId(1L);
        role.setRoleName("更新后的角色");
        role.setDescription("更新后的描述");
        
        SysRoleVO updatedRole = sysRoleService.updateRole(role);
        assertNotNull(updatedRole);
        assertEquals("更新后的角色", updatedRole.getRoleName());
    }

    @Test
    public void deleteRole() {
        sysRoleService.deleteRole(1L);
    }

    @Test
    public void getByCode() {
        SysRoleVO role = sysRoleService.getByCode("TEST_ROLE");
        assertNotNull(role);
        assertEquals("TEST_ROLE", role.getRoleCode());
    }

    @Test
    public void listByUserId() {
        List<SysRoleVO> roles = sysRoleService.listByUserId(1L);
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
    }

    @Test
    public void listDefaultRoles() {
        List<SysRoleVO> roles = sysRoleService.listDefaultRoles();
        assertNotNull(roles);
        assertTrue(roles.stream().allMatch(SysRoleVO::getIsDefault));
    }

    @Test
    public void assignMenus() {
        Long roleId = 1L;
        List<Long> menuIds = Arrays.asList(1L, 2L, 3L);
        sysRoleService.assignMenus(roleId, menuIds);
    }

    @Test
    public void assignDataScope() {
        Long roleId = 1L;
        Integer dataScope = 2;
        sysRoleService.assignDataScope(roleId, dataScope);
    }
}