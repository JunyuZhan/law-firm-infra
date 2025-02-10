package com.lawfirm.core.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.model.ProcessPermission;
import com.lawfirm.core.workflow.service.ProcessPermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProcessPermissionController.class)
class ProcessPermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProcessPermissionService processPermissionService;

    private ProcessPermission permission;

    @BeforeEach
    void setUp() {
        permission = new ProcessPermission()
                .setProcessKey("test-process")
                .setProcessName("测试流程")
                .setCategory("test")
                .setEnabled(true)
                .setTenantId("default")
                .setRemark("测试备注");
    }

    @Test
    void createPermission() throws Exception {
        when(processPermissionService.createPermission(any(ProcessPermission.class)))
                .thenReturn(permission);

        mockMvc.perform(post("/api/workflow/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processKey").value(permission.getProcessKey()))
                .andExpect(jsonPath("$.processName").value(permission.getProcessName()))
                .andExpect(jsonPath("$.category").value(permission.getCategory()))
                .andExpect(jsonPath("$.enabled").value(permission.isEnabled()))
                .andExpect(jsonPath("$.tenantId").value(permission.getTenantId()))
                .andExpect(jsonPath("$.remark").value(permission.getRemark()));
    }

    @Test
    void updatePermission() throws Exception {
        when(processPermissionService.updatePermission(any(ProcessPermission.class)))
                .thenReturn(permission);

        mockMvc.perform(put("/api/workflow/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processKey").value(permission.getProcessKey()))
                .andExpect(jsonPath("$.processName").value(permission.getProcessName()))
                .andExpect(jsonPath("$.category").value(permission.getCategory()))
                .andExpect(jsonPath("$.enabled").value(permission.isEnabled()))
                .andExpect(jsonPath("$.tenantId").value(permission.getTenantId()))
                .andExpect(jsonPath("$.remark").value(permission.getRemark()));
    }

    @Test
    void deletePermission() throws Exception {
        mockMvc.perform(delete("/api/workflow/permissions/{processKey}", permission.getProcessKey()))
                .andExpect(status().isOk());
    }

    @Test
    void getPermission() throws Exception {
        when(processPermissionService.getPermission(permission.getProcessKey()))
                .thenReturn(permission);

        mockMvc.perform(get("/api/workflow/permissions/{processKey}", permission.getProcessKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processKey").value(permission.getProcessKey()))
                .andExpect(jsonPath("$.processName").value(permission.getProcessName()))
                .andExpect(jsonPath("$.category").value(permission.getCategory()))
                .andExpect(jsonPath("$.enabled").value(permission.isEnabled()))
                .andExpect(jsonPath("$.tenantId").value(permission.getTenantId()))
                .andExpect(jsonPath("$.remark").value(permission.getRemark()));
    }

    @Test
    void listPermissions() throws Exception {
        List<ProcessPermission> permissions = Arrays.asList(permission);
        when(processPermissionService.listPermissions(eq("test"), eq(true)))
                .thenReturn(permissions);

        mockMvc.perform(get("/api/workflow/permissions")
                        .param("category", "test")
                        .param("enabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].processKey").value(permission.getProcessKey()))
                .andExpect(jsonPath("$[0].processName").value(permission.getProcessName()))
                .andExpect(jsonPath("$[0].category").value(permission.getCategory()))
                .andExpect(jsonPath("$[0].enabled").value(permission.isEnabled()))
                .andExpect(jsonPath("$[0].tenantId").value(permission.getTenantId()))
                .andExpect(jsonPath("$[0].remark").value(permission.getRemark()));
    }

    @Test
    void checkStartPermission() throws Exception {
        when(processPermissionService.checkStartPermission(eq(permission.getProcessKey()), eq("test-user")))
                .thenReturn(true);

        mockMvc.perform(get("/api/workflow/permissions/{processKey}/check-start", permission.getProcessKey())
                        .param("userId", "test-user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void checkTaskPermission() throws Exception {
        when(processPermissionService.checkTaskPermission(
                eq(permission.getProcessKey()), eq("task1"), eq("test-user")))
                .thenReturn(true);

        mockMvc.perform(get("/api/workflow/permissions/{processKey}/tasks/{taskKey}/check-permission",
                        permission.getProcessKey(), "task1")
                        .param("userId", "test-user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void getStartableProcesses() throws Exception {
        List<String> processes = Arrays.asList("process1", "process2");
        when(processPermissionService.getStartableProcesses(eq("test-user")))
                .thenReturn(processes);

        mockMvc.perform(get("/api/workflow/permissions/startable-processes")
                        .param("userId", "test-user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("process1"))
                .andExpect(jsonPath("$[1]").value("process2"));
    }

    @Test
    void getTaskCandidates() throws Exception {
        List<String> candidates = Arrays.asList("user1", "user2");
        when(processPermissionService.getTaskCandidates(eq(permission.getProcessKey()), eq("task1")))
                .thenReturn(candidates);

        mockMvc.perform(get("/api/workflow/permissions/{processKey}/tasks/{taskKey}/candidates",
                        permission.getProcessKey(), "task1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("user1"))
                .andExpect(jsonPath("$[1]").value("user2"));
    }
} 