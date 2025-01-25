package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysRole;
import com.lawfirm.system.service.SysRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SysRoleControllerTest {

    @Mock
    private SysRoleService roleService;

    @InjectMocks
    private SysRoleController roleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void createRole() throws Exception {
        SysRole role = new SysRole();
        role.setName("测试角色");
        role.setCode("TEST_ROLE");
        
        doNothing().when(roleService).createRole(any(SysRole.class));

        mockMvc.perform(post("/system/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"测试角色\",\"code\":\"TEST_ROLE\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(roleService).createRole(any(SysRole.class));
    }

    @Test
    void updateRole() throws Exception {
        SysRole role = new SysRole();
        role.setId(1L);
        role.setName("测试角色");
        
        doNothing().when(roleService).updateRole(any(SysRole.class));

        mockMvc.perform(put("/system/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"测试角色\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(roleService).updateRole(any(SysRole.class));
    }

    @Test
    void deleteRole() throws Exception {
        doNothing().when(roleService).deleteRole(anyLong());

        mockMvc.perform(delete("/system/role/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(roleService).deleteRole(1L);
    }

    @Test
    void getByCode() throws Exception {
        SysRole role = new SysRole();
        role.setId(1L);
        role.setCode("TEST_ROLE");
        
        when(roleService.getByCode(anyString())).thenReturn(role);

        mockMvc.perform(get("/system/role/code/TEST_ROLE")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("TEST_ROLE"));

        verify(roleService).getByCode("TEST_ROLE");
    }

    @Test
    void listByUserId() throws Exception {
        List<SysRole> roles = Arrays.asList(
            new SysRole(){{setId(1L); setName("角色1"); setCode("ROLE1");}},
            new SysRole(){{setId(2L); setName("角色2"); setCode("ROLE2");}}
        );
        
        when(roleService.listByUserId(anyLong())).thenReturn(roles);

        mockMvc.perform(get("/system/role/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("角色1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("角色2"));

        verify(roleService).listByUserId(1L);
    }

    @Test
    void listDefaultRoles() throws Exception {
        List<SysRole> roles = Arrays.asList(
            new SysRole(){{setId(1L); setName("默认角色1");}},
            new SysRole(){{setId(2L); setName("默认角色2");}}
        );
        
        when(roleService.listDefaultRoles()).thenReturn(roles);

        mockMvc.perform(get("/system/role/default")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("默认角色1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("默认角色2"));

        verify(roleService).listDefaultRoles();
    }

    @Test
    void assignMenus() throws Exception {
        List<Long> menuIds = Arrays.asList(1L, 2L);
        
        doNothing().when(roleService).assignMenus(anyLong(), anyList());

        mockMvc.perform(put("/system/role/1/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2]")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(roleService).assignMenus(1L, menuIds);
    }

    @Test
    void assignDataScope() throws Exception {
        doNothing().when(roleService).assignDataScope(anyLong(), anyInt());

        mockMvc.perform(put("/system/role/1/dataScope")
                .param("dataScope", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(roleService).assignDataScope(1L, 1);
    }
} 