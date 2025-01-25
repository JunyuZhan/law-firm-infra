package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysUser;
import com.lawfirm.system.service.SysUserService;
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

class SysUserControllerTest {

    @Mock
    private SysUserService userService;

    @InjectMocks
    private SysUserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createUser() throws Exception {
        SysUser user = new SysUser();
        user.setUsername("test");
        user.setPassword("123456");
        
        doNothing().when(userService).createUser(any(SysUser.class));

        mockMvc.perform(post("/system/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"123456\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).createUser(any(SysUser.class));
    }

    @Test
    void updateUser() throws Exception {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("test");
        
        doNothing().when(userService).updateUser(any(SysUser.class));

        mockMvc.perform(put("/system/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"username\":\"test\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).updateUser(any(SysUser.class));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/system/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).deleteUser(1L);
    }

    @Test
    void resetPassword() throws Exception {
        doNothing().when(userService).resetPassword(anyLong(), anyString());

        mockMvc.perform(put("/system/user/1/password")
                .param("password", "123456")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).resetPassword(1L, "123456");
    }

    @Test
    void changePassword() throws Exception {
        doNothing().when(userService).changePassword(anyLong(), anyString(), anyString());

        mockMvc.perform(put("/system/user/password")
                .param("id", "1")
                .param("oldPassword", "123456")
                .param("newPassword", "654321")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).changePassword(1L, "123456", "654321");
    }

    @Test
    void getByUsername() throws Exception {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("test");
        
        when(userService.getByUsername(anyString())).thenReturn(user);

        mockMvc.perform(get("/system/user/username/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("test"));

        verify(userService).getByUsername("test");
    }

    @Test
    void listByDeptId() throws Exception {
        List<SysUser> users = Arrays.asList(
            new SysUser(){{setId(1L); setUsername("test1");}},
            new SysUser(){{setId(2L); setUsername("test2");}}
        );
        
        when(userService.listByDeptId(anyLong())).thenReturn(users);

        mockMvc.perform(get("/system/user/dept/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("test1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].username").value("test2"));

        verify(userService).listByDeptId(1L);
    }

    @Test
    void listByRoleId() throws Exception {
        List<SysUser> users = Arrays.asList(
            new SysUser(){{setId(1L); setUsername("test1");}},
            new SysUser(){{setId(2L); setUsername("test2");}}
        );
        
        when(userService.listByRoleId(anyLong())).thenReturn(users);

        mockMvc.perform(get("/system/user/role/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].username").value("test1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].username").value("test2"));

        verify(userService).listByRoleId(1L);
    }

    @Test
    void assignRoles() throws Exception {
        List<Long> roleIds = Arrays.asList(1L, 2L);
        
        doNothing().when(userService).assignRoles(anyLong(), anyList());

        mockMvc.perform(put("/system/user/1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2]")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(userService).assignRoles(1L, roleIds);
    }
} 