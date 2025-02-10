package com.lawfirm.core.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import com.lawfirm.core.workflow.service.TaskService;
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

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private Task task;
    private HistoricTask historicTask;

    @BeforeEach
    void setUp() {
        task = new Task()
                .setId("1001")
                .setName("测试任务")
                .setDescription("测试任务描述")
                .setProcessInstanceId("2001")
                .setProcessDefinitionId("test-process:1:1")
                .setTaskDefinitionKey("task1")
                .setFormKey("form1")
                .setPriority(50)
                .setOwner("owner")
                .setAssignee("assignee")
                .setDelegationState("PENDING")
                .setDueDate(LocalDateTime.now())
                .setCreateTime(LocalDateTime.now())
                .setClaimTime(LocalDateTime.now())
                .setStartTime(LocalDateTime.now())
                .setTaskLocalVariables(new HashMap<>())
                .setProcessVariables(new HashMap<>())
                .setTenantId("default")
                .setCategory("test");

        // 创建历史任务
        historicTask = new HistoricTask();
        // 设置基本任务属性
        historicTask.setId(task.getId());
        historicTask.setName(task.getName());
        historicTask.setDescription(task.getDescription());
        historicTask.setProcessInstanceId(task.getProcessInstanceId());
        historicTask.setProcessDefinitionId(task.getProcessDefinitionId());
        historicTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
        historicTask.setFormKey(task.getFormKey());
        historicTask.setPriority(task.getPriority());
        historicTask.setOwner(task.getOwner());
        historicTask.setAssignee(task.getAssignee());
        historicTask.setDelegationState(task.getDelegationState());
        historicTask.setDueDate(task.getDueDate());
        historicTask.setCreateTime(task.getCreateTime());
        historicTask.setClaimTime(task.getClaimTime());
        historicTask.setStartTime(task.getStartTime());
        historicTask.setTaskLocalVariables(task.getTaskLocalVariables());
        historicTask.setProcessVariables(task.getProcessVariables());
        historicTask.setTenantId(task.getTenantId());
        historicTask.setCategory(task.getCategory());
        // 设置历史任务特有属性
        historicTask.setEndTime(LocalDateTime.now());
        historicTask.setDurationInMillis(1000L);
        historicTask.setDeleteReason("completed");
    }

    @Test
    void getTask() throws Exception {
        when(taskService.getTask("1001")).thenReturn(task);

        mockMvc.perform(get("/api/workflow/tasks/{taskId}", "1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.processInstanceId").value(task.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(task.getProcessDefinitionId()))
                .andExpect(jsonPath("$.taskDefinitionKey").value(task.getTaskDefinitionKey()))
                .andExpect(jsonPath("$.formKey").value(task.getFormKey()))
                .andExpect(jsonPath("$.priority").value(task.getPriority()))
                .andExpect(jsonPath("$.owner").value(task.getOwner()))
                .andExpect(jsonPath("$.assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$.delegationState").value(task.getDelegationState()))
                .andExpect(jsonPath("$.tenantId").value(task.getTenantId()))
                .andExpect(jsonPath("$.category").value(task.getCategory()));
    }

    @Test
    void listTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.listTasks(eq("2001"), eq("task1"),
                eq("assignee"), eq("owner"), eq("default")))
                .thenReturn(tasks);

        mockMvc.perform(get("/api/workflow/tasks")
                        .param("processInstanceId", "2001")
                        .param("taskDefinitionKey", "task1")
                        .param("assignee", "assignee")
                        .param("owner", "owner")
                        .param("tenantId", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].name").value(task.getName()))
                .andExpect(jsonPath("$[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$[0].processInstanceId").value(task.getProcessInstanceId()))
                .andExpect(jsonPath("$[0].processDefinitionId").value(task.getProcessDefinitionId()))
                .andExpect(jsonPath("$[0].taskDefinitionKey").value(task.getTaskDefinitionKey()))
                .andExpect(jsonPath("$[0].formKey").value(task.getFormKey()))
                .andExpect(jsonPath("$[0].priority").value(task.getPriority()))
                .andExpect(jsonPath("$[0].owner").value(task.getOwner()))
                .andExpect(jsonPath("$[0].assignee").value(task.getAssignee()))
                .andExpect(jsonPath("$[0].delegationState").value(task.getDelegationState()))
                .andExpect(jsonPath("$[0].tenantId").value(task.getTenantId()))
                .andExpect(jsonPath("$[0].category").value(task.getCategory()));
    }

    @Test
    void claimTask() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/claim", "1001")
                        .param("userId", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void unclaimTask() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/unclaim", "1001"))
                .andExpect(status().isOk());
    }

    @Test
    void completeTask() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("key1", "value1");
        variables.put("key2", "value2");

        mockMvc.perform(post("/api/workflow/tasks/{taskId}/complete", "1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(variables)))
                .andExpect(status().isOk());
    }

    @Test
    void delegateTask() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/delegate", "1001")
                        .param("userId", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void transferTask() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/transfer", "1001")
                        .param("userId", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void setAssignee() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/assignee", "1001")
                        .param("userId", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void addCandidateUser() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/candidate-users", "1001")
                        .param("userId", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCandidateUser() throws Exception {
        mockMvc.perform(delete("/api/workflow/tasks/{taskId}/candidate-users/{userId}",
                        "1001", "test-user"))
                .andExpect(status().isOk());
    }

    @Test
    void addCandidateGroup() throws Exception {
        mockMvc.perform(post("/api/workflow/tasks/{taskId}/candidate-groups", "1001")
                        .param("groupId", "test-group"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCandidateGroup() throws Exception {
        mockMvc.perform(delete("/api/workflow/tasks/{taskId}/candidate-groups/{groupId}",
                        "1001", "test-group"))
                .andExpect(status().isOk());
    }

    @Test
    void getHistoricTask() throws Exception {
        when(taskService.getHistoricTask("1001")).thenReturn(historicTask);

        mockMvc.perform(get("/api/workflow/tasks/history/{taskId}", "1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historicTask.getId()))
                .andExpect(jsonPath("$.name").value(historicTask.getName()))
                .andExpect(jsonPath("$.description").value(historicTask.getDescription()))
                .andExpect(jsonPath("$.processInstanceId").value(historicTask.getProcessInstanceId()))
                .andExpect(jsonPath("$.processDefinitionId").value(historicTask.getProcessDefinitionId()))
                .andExpect(jsonPath("$.taskDefinitionKey").value(historicTask.getTaskDefinitionKey()))
                .andExpect(jsonPath("$.formKey").value(historicTask.getFormKey()))
                .andExpect(jsonPath("$.priority").value(historicTask.getPriority()))
                .andExpect(jsonPath("$.owner").value(historicTask.getOwner()))
                .andExpect(jsonPath("$.assignee").value(historicTask.getAssignee()))
                .andExpect(jsonPath("$.tenantId").value(historicTask.getTenantId()));
    }

    @Test
    void listHistoricTasks() throws Exception {
        List<HistoricTask> historicTasks = Arrays.asList(historicTask);
        when(taskService.listHistoricTasks(eq("2001"), eq("task1"),
                eq("assignee"), eq("owner"), eq("default"), eq(true)))
                .thenReturn(historicTasks);

        mockMvc.perform(get("/api/workflow/tasks/history")
                        .param("processInstanceId", "2001")
                        .param("taskDefinitionKey", "task1")
                        .param("assignee", "assignee")
                        .param("owner", "owner")
                        .param("tenantId", "default")
                        .param("finished", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(historicTask.getId()))
                .andExpect(jsonPath("$[0].name").value(historicTask.getName()))
                .andExpect(jsonPath("$[0].description").value(historicTask.getDescription()))
                .andExpect(jsonPath("$[0].processInstanceId").value(historicTask.getProcessInstanceId()))
                .andExpect(jsonPath("$[0].processDefinitionId").value(historicTask.getProcessDefinitionId()))
                .andExpect(jsonPath("$[0].taskDefinitionKey").value(historicTask.getTaskDefinitionKey()))
                .andExpect(jsonPath("$[0].formKey").value(historicTask.getFormKey()))
                .andExpect(jsonPath("$[0].priority").value(historicTask.getPriority()))
                .andExpect(jsonPath("$[0].owner").value(historicTask.getOwner()))
                .andExpect(jsonPath("$[0].assignee").value(historicTask.getAssignee()))
                .andExpect(jsonPath("$[0].tenantId").value(historicTask.getTenantId()));
    }
} 