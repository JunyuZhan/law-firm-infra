package com.lawfirm.core.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.model.BusinessProcess;
import com.lawfirm.core.workflow.service.BusinessProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusinessProcessController.class)
class BusinessProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BusinessProcessService businessProcessService;

    private BusinessProcess businessProcess;

    @BeforeEach
    void setUp() {
        businessProcess = new BusinessProcess()
                .setBusinessType("case")
                .setBusinessId("CASE-2024-001")
                .setBusinessTitle("测试案件")
                .setProcessInstanceId("1001")
                .setProcessDefinitionId("case-process:1:1")
                .setProcessDefinitionKey("case-process")
                .setStartUserId("test-user")
                .setStartTime(LocalDateTime.now())
                .setProcessStatus("running")
                .setCurrentTaskId("2001")
                .setCurrentTaskName("案件审核")
                .setCurrentAssignee("manager")
                .setFormData(new HashMap<>())
                .setProcessVariables(new HashMap<>())
                .setTenantId("default")
                .setRemark("测试备注");
    }

    @Test
    void createBusinessProcess() throws Exception {
        when(businessProcessService.createBusinessProcess(any(BusinessProcess.class)))
                .thenReturn(businessProcess);

        mockMvc.perform(post("/api/workflow/business-processes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(businessProcess)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessType").value(businessProcess.getBusinessType()))
                .andExpect(jsonPath("$.businessId").value(businessProcess.getBusinessId()))
                .andExpect(jsonPath("$.businessTitle").value(businessProcess.getBusinessTitle()))
                .andExpect(jsonPath("$.processInstanceId").value(businessProcess.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(businessProcess.getProcessDefinitionId()))
                .andExpect(jsonPath("$.processDefinitionKey").value(businessProcess.getProcessDefinitionKey()))
                .andExpect(jsonPath("$.startUserId").value(businessProcess.getStartUserId()))
                .andExpect(jsonPath("$.processStatus").value(businessProcess.getProcessStatus()))
                .andExpect(jsonPath("$.currentTaskId").value(businessProcess.getCurrentTaskId()))
                .andExpect(jsonPath("$.currentTaskName").value(businessProcess.getCurrentTaskName()))
                .andExpect(jsonPath("$.currentAssignee").value(businessProcess.getCurrentAssignee()))
                .andExpect(jsonPath("$.tenantId").value(businessProcess.getTenantId()))
                .andExpect(jsonPath("$.remark").value(businessProcess.getRemark()));
    }

    @Test
    void updateBusinessProcess() throws Exception {
        when(businessProcessService.updateBusinessProcess(any(BusinessProcess.class)))
                .thenReturn(businessProcess);

        mockMvc.perform(put("/api/workflow/business-processes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(businessProcess)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessType").value(businessProcess.getBusinessType()))
                .andExpect(jsonPath("$.businessId").value(businessProcess.getBusinessId()))
                .andExpect(jsonPath("$.businessTitle").value(businessProcess.getBusinessTitle()))
                .andExpect(jsonPath("$.processInstanceId").value(businessProcess.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(businessProcess.getProcessDefinitionId()))
                .andExpect(jsonPath("$.processDefinitionKey").value(businessProcess.getProcessDefinitionKey()))
                .andExpect(jsonPath("$.startUserId").value(businessProcess.getStartUserId()))
                .andExpect(jsonPath("$.processStatus").value(businessProcess.getProcessStatus()))
                .andExpect(jsonPath("$.currentTaskId").value(businessProcess.getCurrentTaskId()))
                .andExpect(jsonPath("$.currentTaskName").value(businessProcess.getCurrentTaskName()))
                .andExpect(jsonPath("$.currentAssignee").value(businessProcess.getCurrentAssignee()))
                .andExpect(jsonPath("$.tenantId").value(businessProcess.getTenantId()))
                .andExpect(jsonPath("$.remark").value(businessProcess.getRemark()));
    }

    @Test
    void deleteBusinessProcess() throws Exception {
        mockMvc.perform(delete("/api/workflow/business-processes/{businessType}/{businessId}",
                        businessProcess.getBusinessType(), businessProcess.getBusinessId()))
                .andExpect(status().isOk());
    }

    @Test
    void getBusinessProcess() throws Exception {
        when(businessProcessService.getBusinessProcess(
                eq(businessProcess.getBusinessType()), eq(businessProcess.getBusinessId())))
                .thenReturn(businessProcess);

        mockMvc.perform(get("/api/workflow/business-processes/{businessType}/{businessId}",
                        businessProcess.getBusinessType(), businessProcess.getBusinessId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessType").value(businessProcess.getBusinessType()))
                .andExpect(jsonPath("$.businessId").value(businessProcess.getBusinessId()))
                .andExpect(jsonPath("$.businessTitle").value(businessProcess.getBusinessTitle()))
                .andExpect(jsonPath("$.processInstanceId").value(businessProcess.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(businessProcess.getProcessDefinitionId()))
                .andExpect(jsonPath("$.processDefinitionKey").value(businessProcess.getProcessDefinitionKey()))
                .andExpect(jsonPath("$.startUserId").value(businessProcess.getStartUserId()))
                .andExpect(jsonPath("$.processStatus").value(businessProcess.getProcessStatus()))
                .andExpect(jsonPath("$.currentTaskId").value(businessProcess.getCurrentTaskId()))
                .andExpect(jsonPath("$.currentTaskName").value(businessProcess.getCurrentTaskName()))
                .andExpect(jsonPath("$.currentAssignee").value(businessProcess.getCurrentAssignee()))
                .andExpect(jsonPath("$.tenantId").value(businessProcess.getTenantId()))
                .andExpect(jsonPath("$.remark").value(businessProcess.getRemark()));
    }

    @Test
    void getByProcessInstanceId() throws Exception {
        when(businessProcessService.getByProcessInstanceId(eq(businessProcess.getProcessInstanceId())))
                .thenReturn(businessProcess);

        mockMvc.perform(get("/api/workflow/business-processes/process-instances/{processInstanceId}",
                        businessProcess.getProcessInstanceId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessType").value(businessProcess.getBusinessType()))
                .andExpect(jsonPath("$.businessId").value(businessProcess.getBusinessId()))
                .andExpect(jsonPath("$.businessTitle").value(businessProcess.getBusinessTitle()))
                .andExpect(jsonPath("$.processInstanceId").value(businessProcess.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(businessProcess.getProcessDefinitionId()))
                .andExpect(jsonPath("$.processDefinitionKey").value(businessProcess.getProcessDefinitionKey()))
                .andExpect(jsonPath("$.startUserId").value(businessProcess.getStartUserId()))
                .andExpect(jsonPath("$.processStatus").value(businessProcess.getProcessStatus()))
                .andExpect(jsonPath("$.currentTaskId").value(businessProcess.getCurrentTaskId()))
                .andExpect(jsonPath("$.currentTaskName").value(businessProcess.getCurrentTaskName()))
                .andExpect(jsonPath("$.currentAssignee").value(businessProcess.getCurrentAssignee()))
                .andExpect(jsonPath("$.tenantId").value(businessProcess.getTenantId()))
                .andExpect(jsonPath("$.remark").value(businessProcess.getRemark()));
    }

    @Test
    void listBusinessProcesses() throws Exception {
        List<BusinessProcess> processes = Arrays.asList(businessProcess);
        when(businessProcessService.listBusinessProcesses(
                eq(businessProcess.getBusinessType()),
                eq(businessProcess.getProcessStatus()),
                eq(businessProcess.getStartUserId())))
                .thenReturn(processes);

        mockMvc.perform(get("/api/workflow/business-processes")
                        .param("businessType", businessProcess.getBusinessType())
                        .param("processStatus", businessProcess.getProcessStatus())
                        .param("startUserId", businessProcess.getStartUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].businessType").value(businessProcess.getBusinessType()))
                .andExpect(jsonPath("$[0].businessId").value(businessProcess.getBusinessId()))
                .andExpect(jsonPath("$[0].businessTitle").value(businessProcess.getBusinessTitle()))
                .andExpect(jsonPath("$[0].processInstanceId").value(businessProcess.getProcessInstanceId()))
                .andExpect(jsonPath("$[0].processDefinitionId").value(businessProcess.getProcessDefinitionId()))
                .andExpect(jsonPath("$[0].processDefinitionKey").value(businessProcess.getProcessDefinitionKey()))
                .andExpect(jsonPath("$[0].startUserId").value(businessProcess.getStartUserId()))
                .andExpect(jsonPath("$[0].processStatus").value(businessProcess.getProcessStatus()))
                .andExpect(jsonPath("$[0].currentTaskId").value(businessProcess.getCurrentTaskId()))
                .andExpect(jsonPath("$[0].currentTaskName").value(businessProcess.getCurrentTaskName()))
                .andExpect(jsonPath("$[0].currentAssignee").value(businessProcess.getCurrentAssignee()))
                .andExpect(jsonPath("$[0].tenantId").value(businessProcess.getTenantId()))
                .andExpect(jsonPath("$[0].remark").value(businessProcess.getRemark()));
    }

    @Test
    void updateProcessStatus() throws Exception {
        mockMvc.perform(put("/api/workflow/business-processes/process-instances/{processInstanceId}/status",
                        businessProcess.getProcessInstanceId())
                        .param("processStatus", "completed"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCurrentTask() throws Exception {
        mockMvc.perform(put("/api/workflow/business-processes/process-instances/{processInstanceId}/current-task",
                        businessProcess.getProcessInstanceId())
                        .param("taskId", "3001")
                        .param("taskName", "案件审批")
                        .param("assignee", "director"))
                .andExpect(status().isOk());
    }

    @Test
    void saveFormData() throws Exception {
        Map<String, Object> formData = new HashMap<>();
        formData.put("field1", "value1");
        formData.put("field2", "value2");

        mockMvc.perform(put("/api/workflow/business-processes/process-instances/{processInstanceId}/form-data",
                        businessProcess.getProcessInstanceId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formData)))
                .andExpect(status().isOk());
    }

    @Test
    void saveProcessVariables() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("var1", "value1");
        variables.put("var2", "value2");

        mockMvc.perform(put("/api/workflow/business-processes/process-instances/{processInstanceId}/variables",
                        businessProcess.getProcessInstanceId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(variables)))
                .andExpect(status().isOk());
    }
} 