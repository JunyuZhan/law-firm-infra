package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import com.lawfirm.core.workflow.service.impl.TaskServiceImpl;
import org.flowable.engine.TaskService;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 任务服务测试
 */
class TaskServiceTest {
    
    @Mock
    private TaskService flowableTaskService;
    
    @Mock
    private HistoryService historyService;
    
    @Mock
    private TaskQuery taskQuery;
    
    @Mock
    private HistoricTaskInstanceQuery historicTaskInstanceQuery;
    
    @Mock
    private org.flowable.task.api.Task flowableTask;
    
    @Mock
    private org.flowable.task.api.history.HistoricTaskInstance historicTask;
    
    private com.lawfirm.core.workflow.service.TaskService taskService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(flowableTaskService, historyService);
        
        when(flowableTaskService.createTaskQuery()).thenReturn(taskQuery);
        when(historyService.createHistoricTaskInstanceQuery()).thenReturn(historicTaskInstanceQuery);
        
        when(taskQuery.taskId(anyString())).thenReturn(taskQuery);
        when(taskQuery.processInstanceId(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskDefinitionKey(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskAssignee(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskOwner(anyString())).thenReturn(taskQuery);
        when(taskQuery.taskTenantId(anyString())).thenReturn(taskQuery);
        when(taskQuery.orderByTaskCreateTime()).thenReturn(taskQuery);
        when(taskQuery.desc()).thenReturn(taskQuery);
        
        when(historicTaskInstanceQuery.taskId(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.processInstanceId(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.taskDefinitionKey(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.taskAssignee(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.taskOwner(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.taskTenantId(anyString())).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.finished()).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.unfinished()).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.includeProcessVariables()).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.includeTaskLocalVariables()).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.orderByHistoricTaskInstanceEndTime()).thenReturn(historicTaskInstanceQuery);
        when(historicTaskInstanceQuery.desc()).thenReturn(historicTaskInstanceQuery);
    }
    
    @Test
    void getTask() {
        String taskId = "test-task";
        when(taskQuery.singleResult()).thenReturn(flowableTask);
        
        Task task = taskService.getTask(taskId);
        
        verify(flowableTaskService).createTaskQuery();
        verify(taskQuery).taskId(taskId);
        verify(taskQuery).singleResult();
        assertNotNull(task);
    }
    
    @Test
    void listTasks() {
        when(taskQuery.list()).thenReturn(Collections.singletonList(flowableTask));
        
        List<Task> tasks = taskService.listTasks("test-instance", "test-key",
                "test-assignee", "test-owner", "test-tenant");
        
        verify(flowableTaskService).createTaskQuery();
        verify(taskQuery).processInstanceId("test-instance");
        verify(taskQuery).taskDefinitionKey("test-key");
        verify(taskQuery).taskAssignee("test-assignee");
        verify(taskQuery).taskOwner("test-owner");
        verify(taskQuery).taskTenantId("test-tenant");
        verify(taskQuery).list();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
    }
    
    @Test
    void completeTask() {
        String taskId = "test-task";
        Map<String, Object> variables = new HashMap<>();
        variables.put("test", "value");
        
        taskService.completeTask(taskId, variables);
        
        verify(flowableTaskService).complete(taskId, variables);
    }
    
    @Test
    void getHistoricTask() {
        String taskId = "test-task";
        when(historicTaskInstanceQuery.singleResult()).thenReturn(historicTask);
        
        HistoricTask historicTaskResult = taskService.getHistoricTask(taskId);
        
        verify(historyService).createHistoricTaskInstanceQuery();
        verify(historicTaskInstanceQuery).taskId(taskId);
        verify(historicTaskInstanceQuery).includeProcessVariables();
        verify(historicTaskInstanceQuery).includeTaskLocalVariables();
        verify(historicTaskInstanceQuery).singleResult();
        assertNotNull(historicTaskResult);
    }
    
    @Test
    void listHistoricTasks() {
        when(historicTaskInstanceQuery.list()).thenReturn(Collections.singletonList(historicTask));
        
        List<HistoricTask> historicTasks = taskService.listHistoricTasks("test-instance", "test-key",
                "test-assignee", "test-owner", "test-tenant", true);
        
        verify(historyService).createHistoricTaskInstanceQuery();
        verify(historicTaskInstanceQuery).processInstanceId("test-instance");
        verify(historicTaskInstanceQuery).taskDefinitionKey("test-key");
        verify(historicTaskInstanceQuery).taskAssignee("test-assignee");
        verify(historicTaskInstanceQuery).taskOwner("test-owner");
        verify(historicTaskInstanceQuery).taskTenantId("test-tenant");
        verify(historicTaskInstanceQuery).finished();
        verify(historicTaskInstanceQuery).list();
        assertNotNull(historicTasks);
        assertEquals(1, historicTasks.size());
    }
} 