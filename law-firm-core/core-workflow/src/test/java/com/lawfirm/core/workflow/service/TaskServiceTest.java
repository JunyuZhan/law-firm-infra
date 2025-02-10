package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import com.lawfirm.core.workflow.service.impl.TaskServiceImpl;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private org.flowable.engine.TaskService flowableTaskService;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private HistoricTask historicTask;
    private org.flowable.task.api.Task flowableTask;
    private org.flowable.task.api.history.HistoricTaskInstance flowableHistoricTask;

    @BeforeEach
    void setUp() {
        // 初始化基础数据
        task = new Task()
                .setId("1001")
                .setName("测试任务")
                .setProcessInstanceId("2001");

        // 初始化历史任务
        historicTask = new HistoricTask();
        historicTask.setId("1001");
        historicTask.setName("测试任务");
        historicTask.setProcessInstanceId("2001");
        historicTask.setEndTime(LocalDateTime.now());
        historicTask.setDurationInMillis(1000L);
        historicTask.setDeleteReason("completed");

        // 初始化Flowable任务
        flowableTask = mock(org.flowable.task.api.Task.class);
        when(flowableTask.getId()).thenReturn("1001");
        when(flowableTask.getName()).thenReturn("测试任务");
        when(flowableTask.getProcessInstanceId()).thenReturn("2001");

        // 初始化Flowable历史任务
        flowableHistoricTask = mock(org.flowable.task.api.history.HistoricTaskInstance.class);
        when(flowableHistoricTask.getId()).thenReturn("1001");
        when(flowableHistoricTask.getName()).thenReturn("测试任务");
        when(flowableHistoricTask.getProcessInstanceId()).thenReturn("2001");
        when(flowableHistoricTask.getEndTime()).thenReturn(new Date());
        when(flowableHistoricTask.getDurationInMillis()).thenReturn(1000L);
        when(flowableHistoricTask.getDeleteReason()).thenReturn("completed");
    }

    @Test
    void getTask() {
        // 准备测试数据
        org.flowable.task.api.TaskQuery taskQuery = mock(org.flowable.task.api.TaskQuery.class);
        when(flowableTaskService.createTaskQuery()).thenReturn(taskQuery);
        when(taskQuery.taskId(anyString())).thenReturn(taskQuery);
        when(taskQuery.singleResult()).thenReturn(flowableTask);

        // 执行测试
        Task result = taskService.getTask("1001");

        // 验证结果
        assertNotNull(result);
        assertEquals("1001", result.getId());
        assertEquals("测试任务", result.getName());
        assertEquals("2001", result.getProcessInstanceId());

        // 验证调用
        verify(flowableTaskService).createTaskQuery();
        verify(taskQuery).taskId("1001");
        verify(taskQuery).singleResult();
    }

    @Test
    void listTasks() {
        // 准备测试数据
        org.flowable.task.api.TaskQuery taskQuery = mock(org.flowable.task.api.TaskQuery.class);
        when(flowableTaskService.createTaskQuery()).thenReturn(taskQuery);
        when(taskQuery.processInstanceId(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskDefinitionKey(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskAssignee(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskOwner(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskTenantId(anyString())).thenReturn(taskQuery);
        when(taskQuery.orderByTaskCreateTime()).thenReturn(taskQuery);
        when(taskQuery.desc()).thenReturn(taskQuery);
        when(taskQuery.list()).thenReturn(Arrays.asList(flowableTask));

        // 执行测试
        List<Task> results = taskService.listTasks("2001", "task1", "assignee", "owner", "default");

        // 验证结果
        assertNotNull(results);
        assertEquals(1, results.size());
        Task result = results.get(0);
        assertEquals("1001", result.getId());
        assertEquals("测试任务", result.getName());
        assertEquals("2001", result.getProcessInstanceId());

        // 验证调用
        verify(flowableTaskService).createTaskQuery();
        verify(taskQuery).processInstanceId("2001");
        verify(taskQuery).taskDefinitionKey("task1");
        verify(taskQuery).taskAssignee("assignee");
        verify(taskQuery).taskOwner("owner");
        verify(taskQuery).taskTenantId("default");
        verify(taskQuery).orderByTaskCreateTime();
        verify(taskQuery).desc();
        verify(taskQuery).list();
    }

    @Test
    void claimTask() {
        taskService.claimTask("1001", "test-user");

        verify(flowableTaskService).claim("1001", "test-user");
    }

    @Test
    void unclaimTask() {
        taskService.unclaimTask("1001");

        verify(flowableTaskService).unclaim("1001");
    }

    @Test
    void completeTask() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("key1", "value1");
        variables.put("key2", "value2");

        taskService.completeTask("1001", variables);

        verify(flowableTaskService).complete("1001", variables);
    }

    @Test
    void delegateTask() {
        taskService.delegateTask("1001", "test-user");

        verify(flowableTaskService).delegateTask("1001", "test-user");
    }

    @Test
    void transferTask() {
        taskService.transferTask("1001", "test-user");

        verify(flowableTaskService).setAssignee("1001", "test-user");
    }

    @Test
    void setAssignee() {
        taskService.setAssignee("1001", "test-user");

        verify(flowableTaskService).setAssignee("1001", "test-user");
    }

    @Test
    void addCandidateUser() {
        taskService.addCandidateUser("1001", "test-user");

        verify(flowableTaskService).addCandidateUser("1001", "test-user");
    }

    @Test
    void deleteCandidateUser() {
        taskService.deleteCandidateUser("1001", "test-user");

        verify(flowableTaskService).deleteCandidateUser("1001", "test-user");
    }

    @Test
    void addCandidateGroup() {
        taskService.addCandidateGroup("1001", "test-group");

        verify(flowableTaskService).addCandidateGroup("1001", "test-group");
    }

    @Test
    void deleteCandidateGroup() {
        taskService.deleteCandidateGroup("1001", "test-group");

        verify(flowableTaskService).deleteCandidateGroup("1001", "test-group");
    }

    @Test
    void getHistoricTask() {
        // 准备测试数据
        org.flowable.task.api.history.HistoricTaskInstanceQuery historicTaskQuery = 
            mock(org.flowable.task.api.history.HistoricTaskInstanceQuery.class);
        when(historyService.createHistoricTaskInstanceQuery()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.taskId(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.includeProcessVariables()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.includeTaskLocalVariables()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.singleResult()).thenReturn(flowableHistoricTask);

        // 执行测试
        HistoricTask result = taskService.getHistoricTask("1001");

        // 验证结果
        assertNotNull(result);
        assertEquals("1001", result.getId());
        assertEquals("测试任务", result.getName());
        assertEquals("2001", result.getProcessInstanceId());

        // 验证调用
        verify(historyService).createHistoricTaskInstanceQuery();
        verify(historicTaskQuery).taskId("1001");
        verify(historicTaskQuery).includeProcessVariables();
        verify(historicTaskQuery).includeTaskLocalVariables();
        verify(historicTaskQuery).singleResult();
    }

    @Test
    void listHistoricTasks() {
        // 准备测试数据
        org.flowable.task.api.history.HistoricTaskInstanceQuery historicTaskQuery = 
            mock(org.flowable.task.api.history.HistoricTaskInstanceQuery.class);
        when(historyService.createHistoricTaskInstanceQuery()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.processInstanceId(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.taskDefinitionKey(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.taskAssignee(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.taskOwner(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.taskTenantId(anyString())).thenReturn(historicTaskQuery);
        when(historicTaskQuery.includeProcessVariables()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.includeTaskLocalVariables()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.finished()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.orderByHistoricTaskInstanceEndTime()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.desc()).thenReturn(historicTaskQuery);
        when(historicTaskQuery.list()).thenReturn(Arrays.asList(flowableHistoricTask));

        // 执行测试
        List<HistoricTask> results = taskService.listHistoricTasks(
            "2001", "task1", "assignee", "owner", "default", true);

        // 验证结果
        assertNotNull(results);
        assertEquals(1, results.size());
        HistoricTask result = results.get(0);
        assertEquals("1001", result.getId());
        assertEquals("测试任务", result.getName());
        assertEquals("2001", result.getProcessInstanceId());

        // 验证调用
        verify(historyService).createHistoricTaskInstanceQuery();
        verify(historicTaskQuery).processInstanceId("2001");
        verify(historicTaskQuery).taskDefinitionKey("task1");
        verify(historicTaskQuery).taskAssignee("assignee");
        verify(historicTaskQuery).taskOwner("owner");
        verify(historicTaskQuery).taskTenantId("default");
        verify(historicTaskQuery).includeProcessVariables();
        verify(historicTaskQuery).includeTaskLocalVariables();
        verify(historicTaskQuery).finished();
        verify(historicTaskQuery).orderByHistoricTaskInstanceEndTime();
        verify(historicTaskQuery).desc();
        verify(historicTaskQuery).list();
    }
} 