package com.lawfirm.core.workflow.controller;

import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import com.lawfirm.core.workflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 任务控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workflow/tasks")
public class TaskController extends BaseController {
    
    private final TaskService taskService;
    
    /**
     * 获取任务
     */
    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }
    
    /**
     * 查询任务列表
     */
    @GetMapping
    public List<Task> listTasks(
            @RequestParam(required = false) String processInstanceId,
            @RequestParam(required = false) String taskDefinitionKey,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String tenantId) {
        return taskService.listTasks(processInstanceId, taskDefinitionKey, assignee, owner, tenantId);
    }
    
    /**
     * 认领任务
     */
    @PostMapping("/{taskId}/claim")
    public void claimTask(@PathVariable String taskId, @RequestParam String userId) {
        taskService.claimTask(taskId, userId);
    }
    
    /**
     * 取消认领任务
     */
    @PostMapping("/{taskId}/unclaim")
    public void unclaimTask(@PathVariable String taskId) {
        taskService.unclaimTask(taskId);
    }
    
    /**
     * 完成任务
     */
    @PostMapping("/{taskId}/complete")
    public void completeTask(@PathVariable String taskId,
                           @RequestBody(required = false) Map<String, Object> variables) {
        taskService.completeTask(taskId, variables);
    }

    /**
     * 委托任务
     */
    @PostMapping("/{taskId}/delegate")
    public void delegateTask(@PathVariable String taskId, @RequestParam String userId) {
        taskService.delegateTask(taskId, userId);
    }

    /**
     * 转办任务
     */
    @PostMapping("/{taskId}/transfer")
    public void transferTask(@PathVariable String taskId, @RequestParam String userId) {
        taskService.transferTask(taskId, userId);
    }

    /**
     * 设置任务处理人
     */
    @PostMapping("/{taskId}/assignee")
    public void setAssignee(@PathVariable String taskId, @RequestParam String userId) {
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 添加任务候选人
     */
    @PostMapping("/{taskId}/candidate-users")
    public void addCandidateUser(@PathVariable String taskId, @RequestParam String userId) {
        taskService.addCandidateUser(taskId, userId);
    }

    /**
     * 删除任务候选人
     */
    @DeleteMapping("/{taskId}/candidate-users/{userId}")
    public void deleteCandidateUser(@PathVariable String taskId, @PathVariable String userId) {
        taskService.deleteCandidateUser(taskId, userId);
    }

    /**
     * 添加任务候选组
     */
    @PostMapping("/{taskId}/candidate-groups")
    public void addCandidateGroup(@PathVariable String taskId, @RequestParam String groupId) {
        taskService.addCandidateGroup(taskId, groupId);
    }

    /**
     * 删除任务候选组
     */
    @DeleteMapping("/{taskId}/candidate-groups/{groupId}")
    public void deleteCandidateGroup(@PathVariable String taskId, @PathVariable String groupId) {
        taskService.deleteCandidateGroup(taskId, groupId);
    }

    /**
     * 获取历史任务
     */
    @GetMapping("/history/{taskId}")
    public HistoricTask getHistoricTask(@PathVariable String taskId) {
        return taskService.getHistoricTask(taskId);
    }

    /**
     * 查询历史任务列表
     */
    @GetMapping("/history")
    public List<HistoricTask> listHistoricTasks(
            @RequestParam(required = false) String processInstanceId,
            @RequestParam(required = false) String taskDefinitionKey,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String tenantId,
            @RequestParam(defaultValue = "false") boolean finished) {
        return taskService.listHistoricTasks(processInstanceId, taskDefinitionKey,
                assignee, owner, tenantId, finished);
    }
} 