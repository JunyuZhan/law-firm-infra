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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskService flowableTaskService;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private org.flowable.task.api.Task flowableTask;
    private HistoricTaskInstance historicTask;

    @BeforeEach
    void setUp() {
        // 模拟Flowable任务
        flowableTask = mock(org.flowable.task.api.Task.class);
        when(flowableTask.getId()).thenReturn("1001");
        when(flowableTask.getName()).thenReturn("测试任务");
        when(flowableTask.getDescription()).thenReturn("测试任务描述");
        when(flowableTask.getProcessInstanceId()).thenReturn("2001");
        when(flowableTask.getProcessDefinitionId()).thenReturn("test-process:1:1");
        when(flowableTask.getTaskDefinitionKey()).thenReturn("task1");
        when(flowableTask.getFormKey()).thenReturn("form1");
        when(flowableTask.getPriority()).thenReturn(50);
        when(flowableTask.getOwner()).thenReturn("owner");
        when(flowableTask.getAssignee()).thenReturn("assignee");
        when(flowableTask.getDelegationState()).thenReturn(org.flowable.task.api.DelegationState.PENDING);

        // 模拟历史任务
        historicTask = mock(HistoricTaskInstance.class);
        when(historicTask.getId()).thenReturn("1001");
        when(historicTask.getName()).thenReturn("测试任务");
        when(historicTask.getDescription()).thenReturn("测试任务描述");
        when(historicTask.getProcessInstanceId()).thenReturn("2001");
        when(historicTask.getProcessDefinitionId()).thenReturn("test-process:1:1");
        when(historicTask.getTaskDefinitionKey()).thenReturn("task1");
        when(historicTask.getFormKey()).thenReturn("form1");
        when(historicTask.getPriority()).thenReturn(50);
        when(historicTask.getOwner()).thenReturn("owner");
        when(historicTask.getAssignee()).thenReturn("assignee");
    }

    @Test
    void getTask() {
        when(flowableTaskService.createTaskQuery().taskId("1001").singleResult())
                .thenReturn(flowableTask);

        Task task = taskService.getTask("1001");

        assertNotNull(task);
        assertEquals("1001", task.getId());
        assertEquals("测试任务", task.getName());
        assertEquals("测试任务描述", task.getDescription());
        assertEquals("2001", task.getProcessInstanceId());
        assertEquals("test-process:1:1", task.getProcessDefinitionId());
        assertEquals("task1", task.getTaskDefinitionKey());
        assertEquals("form1", task.getFormKey());
        assertEquals(50, task.getPriority());
        assertEquals("owner", task.getOwner());
        assertEquals("assignee", task.getAssignee());
        assertEquals("PENDING", task.getDelegationState());
    }

    @Test
    void listTasks() {
        when(flowableTaskService.createTaskQuery()
                .processInstanceId("2001")
                .taskDefinitionKey("task1")
                .taskAssignee("assignee")
                .taskOwner("owner")
                .taskTenantId("default")
                .list())
                .thenReturn(Arrays.asList(flowableTask));

        List<Task> tasks = taskService.listTasks("2001", "task1",
                "assignee", "owner", "default");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Task task = tasks.get(0);
        assertEquals("1001", task.getId());
        assertEquals("测试任务", task.getName());
        assertEquals("测试任务描述", task.getDescription());
        assertEquals("2001", task.getProcessInstanceId());
        assertEquals("test-process:1:1", task.getProcessDefinitionId());
        assertEquals("task1", task.getTaskDefinitionKey());
        assertEquals("form1", task.getFormKey());
        assertEquals(50, task.getPriority());
        assertEquals("owner", task.getOwner());
        assertEquals("assignee", task.getAssignee());
        assertEquals("PENDING", task.getDelegationState());
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
        when(historyService.createHistoricTaskInstanceQuery().taskId("1001").singleResult())
                .thenReturn(historicTask);

        HistoricTask task = taskService.getHistoricTask("1001");

        assertNotNull(task);
        assertEquals("1001", task.getId());
        assertEquals("测试任务", task.getName());
        assertEquals("测试任务描述", task.getDescription());
        assertEquals("2001", task.getProcessInstanceId());
        assertEquals("test-process:1:1", task.getProcessDefinitionId());
        assertEquals("task1", task.getTaskDefinitionKey());
        assertEquals("form1", task.getFormKey());
        assertEquals(50, task.getPriority());
        assertEquals("owner", task.getOwner());
        assertEquals("assignee", task.getAssignee());
    }

    @Test
    void listHistoricTasks() {
        when(historyService.createHistoricTaskInstanceQuery()
                .processInstanceId("2001")
                .taskDefinitionKey("task1")
                .taskAssignee("assignee")
                .taskOwner("owner")
                .taskTenantId("default")
                .finished()
                .list())
                .thenReturn(Arrays.asList(historicTask));

        List<HistoricTask> tasks = taskService.listHistoricTasks("2001", "task1",
                "assignee", "owner", "default", true);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        HistoricTask task = tasks.get(0);
        assertEquals("1001", task.getId());
        assertEquals("测试任务", task.getName());
        assertEquals("测试任务描述", task.getDescription());
        assertEquals("2001", task.getProcessInstanceId());
        assertEquals("test-process:1:1", task.getProcessDefinitionId());
        assertEquals("task1", task.getTaskDefinitionKey());
        assertEquals("form1", task.getFormKey());
        assertEquals(50, task.getPriority());
        assertEquals("owner", task.getOwner());
        assertEquals("assignee", task.getAssignee());
    }
} 