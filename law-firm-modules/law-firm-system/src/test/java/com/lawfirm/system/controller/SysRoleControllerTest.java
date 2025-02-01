package com.lawfirm.system.controller;

import com.lawfirm.system.model.dto.SysRoleDTO;
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
        SysRoleDTO role = new SysRoleDTO();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        role.setDescription("用于测试的角色");
        role.setDataScope(1);
        role.setIsDefault(false);
        
        SysRoleDTO createdRole = sysRoleService.createRole(role);
        assertNotNull(createdRole);
        assertEquals("测试角色", createdRole.getName());
    }

    @Test
    public void updateRole() {
        SysRoleDTO role = new SysRoleDTO();
        role.setId(1L);
        role.setName("更新后的角色");
        role.setDescription("更新后的描述");
        
        SysRoleDTO updatedRole = sysRoleService.updateRole(role);
        assertNotNull(updatedRole);
        assertEquals("更新后的角色", updatedRole.getName());
    }

    @Test
    public void deleteRole() {
        sysRoleService.deleteRole(1L);
    }

    @Test
    public void getByCode() {
        SysRoleDTO role = sysRoleService.getByCode("TEST_ROLE");
        assertNotNull(role);
        assertEquals("TEST_ROLE", role.getCode());
    }

    @Test
    public void listByUserId() {
        List<SysRoleDTO> roles = sysRoleService.listByUserId(1L);
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
    }

    @Test
    public void listDefaultRoles() {
        List<SysRoleDTO> roles = sysRoleService.listDefaultRoles();
        assertNotNull(roles);
        assertTrue(roles.stream().allMatch(SysRoleDTO::getIsDefault));
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