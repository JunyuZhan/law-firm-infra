package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.BaseIntegrationTest;
import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TaskService taskService;

    private String processInstanceId;
    private String taskId;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        // 部署测试流程
        deployProcessDefinition("test-process.bpmn20.xml", "test-process");

        // 启动流程实例
        processInstanceId = startProcessInstance("test-process");

        // 获取当前任务
        taskId = getCurrentTaskId(processInstanceId);
    }

    @Test
    void getTask() {
        Task task = taskService.getTask(taskId);

        assertNotNull(task);
        assertEquals(taskId, task.getId());
        assertEquals(processInstanceId, task.getProcessInstanceId());
        assertEquals("test-process", task.getTaskDefinitionKey());
    }

    @Test
    void listTasks() {
        List<Task> tasks = taskService.listTasks(processInstanceId, null, null, null, null);

        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        Task task = tasks.get(0);
        assertEquals(taskId, task.getId());
        assertEquals(processInstanceId, task.getProcessInstanceId());
        assertEquals("test-process", task.getTaskDefinitionKey());
    }

    @Test
    void claimTask() {
        taskService.claimTask(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertEquals("test-user", task.getAssignee());
    }

    @Test
    void unclaimTask() {
        taskService.claimTask(taskId, "test-user");
        taskService.unclaimTask(taskId);

        Task task = taskService.getTask(taskId);
        assertNull(task.getAssignee());
    }

    @Test
    void completeTask() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", true);

        taskService.completeTask(taskId, variables);

        List<Task> tasks = taskService.listTasks(processInstanceId, null, null, null, null);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void delegateTask() {
        taskService.delegateTask(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertEquals("test-user", task.getAssignee());
        assertEquals("PENDING", task.getDelegationState());
    }

    @Test
    void transferTask() {
        taskService.transferTask(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertEquals("test-user", task.getAssignee());
    }

    @Test
    void setAssignee() {
        taskService.setAssignee(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertEquals("test-user", task.getAssignee());
    }

    @Test
    void addCandidateUser() {
        taskService.addCandidateUser(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertNull(task.getAssignee());
    }

    @Test
    void deleteCandidateUser() {
        taskService.addCandidateUser(taskId, "test-user");
        taskService.deleteCandidateUser(taskId, "test-user");

        Task task = taskService.getTask(taskId);
        assertNull(task.getAssignee());
    }

    @Test
    void addCandidateGroup() {
        taskService.addCandidateGroup(taskId, "test-group");

        Task task = taskService.getTask(taskId);
        assertNull(task.getAssignee());
    }

    @Test
    void deleteCandidateGroup() {
        taskService.addCandidateGroup(taskId, "test-group");
        taskService.deleteCandidateGroup(taskId, "test-group");

        Task task = taskService.getTask(taskId);
        assertNull(task.getAssignee());
    }

    @Test
    void getHistoricTask() {
        taskService.completeTask(taskId, null);

        HistoricTask historicTask = taskService.getHistoricTask(taskId);
        assertNotNull(historicTask);
        assertEquals(taskId, historicTask.getId());
        assertEquals(processInstanceId, historicTask.getProcessInstanceId());
        assertEquals("test-process", historicTask.getTaskDefinitionKey());
    }

    @Test
    void listHistoricTasks() {
        taskService.completeTask(taskId, null);

        List<HistoricTask> historicTasks = taskService.listHistoricTasks(
                processInstanceId, null, null, null, null, true);

        assertNotNull(historicTasks);
        assertFalse(historicTasks.isEmpty());
        HistoricTask historicTask = historicTasks.get(0);
        assertEquals(taskId, historicTask.getId());
        assertEquals(processInstanceId, historicTask.getProcessInstanceId());
        assertEquals("test-process", historicTask.getTaskDefinitionKey());
    }
} 