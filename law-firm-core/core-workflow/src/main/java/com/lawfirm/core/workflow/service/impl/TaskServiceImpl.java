package com.lawfirm.core.workflow.service.impl;

import com.lawfirm.core.workflow.model.Task;
import com.lawfirm.core.workflow.model.HistoricTask;
import com.lawfirm.core.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final org.flowable.engine.TaskService flowableTaskService;
    private final HistoryService historyService;
    
    @Override
    public Task getTask(String taskId) {
        org.flowable.task.api.Task task = flowableTaskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        return convertToTask(task);
    }
    
    @Override
    public List<Task> listTasks(String processInstanceId, String taskDefinitionKey,
                               String assignee, String owner, String tenantId) {
        TaskQuery query = flowableTaskService.createTaskQuery();
        
        if (StringUtils.hasText(processInstanceId)) {
            query.processInstanceId(processInstanceId);
        }
        if (StringUtils.hasText(taskDefinitionKey)) {
            query.taskDefinitionKey(taskDefinitionKey);
        }
        if (StringUtils.hasText(assignee)) {
            query.taskAssignee(assignee);
        }
        if (StringUtils.hasText(owner)) {
            query.taskOwner(owner);
        }
        if (StringUtils.hasText(tenantId)) {
            query.taskTenantId(tenantId);
        }
        
        return query.orderByTaskCreateTime().desc()
                .list()
                .stream()
                .map(this::convertToTask)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claimTask(String taskId, String userId) {
        flowableTaskService.claim(taskId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unclaimTask(String taskId) {
        flowableTaskService.unclaim(taskId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(String taskId, Map<String, Object> variables) {
        if (variables != null && !variables.isEmpty()) {
            flowableTaskService.complete(taskId, variables);
        } else {
            flowableTaskService.complete(taskId);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(String taskId, String userId) {
        flowableTaskService.delegateTask(taskId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferTask(String taskId, String userId) {
        org.flowable.task.api.Task task = flowableTaskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task != null) {
            String originalAssignee = task.getAssignee();
            flowableTaskService.setAssignee(taskId, userId);
            log.info("Task transferred: taskId={}, from={}, to={}", taskId, originalAssignee, userId);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAssignee(String taskId, String userId) {
        flowableTaskService.setAssignee(taskId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCandidateUser(String taskId, String userId) {
        flowableTaskService.addCandidateUser(taskId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCandidateUser(String taskId, String userId) {
        flowableTaskService.deleteCandidateUser(taskId, userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCandidateGroup(String taskId, String groupId) {
        flowableTaskService.addCandidateGroup(taskId, groupId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCandidateGroup(String taskId, String groupId) {
        flowableTaskService.deleteCandidateGroup(taskId, groupId);
    }
    
    @Override
    public HistoricTask getHistoricTask(String taskId) {
        org.flowable.task.api.history.HistoricTaskInstance historicTask = historyService
                .createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .singleResult();
        return convertToHistoricTask(historicTask);
    }
    
    @Override
    public List<HistoricTask> listHistoricTasks(String processInstanceId, String taskDefinitionKey,
                                               String assignee, String owner, String tenantId, boolean finished) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .includeTaskLocalVariables();
        
        if (StringUtils.hasText(processInstanceId)) {
            query.processInstanceId(processInstanceId);
        }
        if (StringUtils.hasText(taskDefinitionKey)) {
            query.taskDefinitionKey(taskDefinitionKey);
        }
        if (StringUtils.hasText(assignee)) {
            query.taskAssignee(assignee);
        }
        if (StringUtils.hasText(owner)) {
            query.taskOwner(owner);
        }
        if (StringUtils.hasText(tenantId)) {
            query.taskTenantId(tenantId);
        }
        if (finished) {
            query.finished();
        } else {
            query.unfinished();
        }
        
        return query.orderByHistoricTaskInstanceEndTime().desc()
                .list()
                .stream()
                .map(this::convertToHistoricTask)
                .collect(Collectors.toList());
    }
    
    /**
     * 转换为任务模型
     */
    private Task convertToTask(org.flowable.task.api.Task task) {
        if (task == null) {
            return null;
        }
        
        return new Task()
                .setId(task.getId())
                .setName(task.getName())
                .setDescription(task.getDescription())
                .setProcessInstanceId(task.getProcessInstanceId())
                .setProcessDefinitionId(task.getProcessDefinitionId())
                .setTaskDefinitionKey(task.getTaskDefinitionKey())
                .setFormKey(task.getFormKey())
                .setPriority(task.getPriority())
                .setOwner(task.getOwner())
                .setAssignee(task.getAssignee())
                .setDelegationState(task.getDelegationState() != null ? task.getDelegationState().toString() : null)
                .setDueDate(task.getDueDate() != null ? 
                        LocalDateTime.ofInstant(task.getDueDate().toInstant(), ZoneId.systemDefault()) : null)
                .setCreateTime(task.getCreateTime() != null ?
                        LocalDateTime.ofInstant(task.getCreateTime().toInstant(), ZoneId.systemDefault()) : null)
                .setClaimTime(task.getClaimTime() != null ?
                        LocalDateTime.ofInstant(task.getClaimTime().toInstant(), ZoneId.systemDefault()) : null)
                .setTaskLocalVariables(task.getTaskLocalVariables())
                .setProcessVariables(task.getProcessVariables())
                .setTenantId(task.getTenantId());
    }
    
    /**
     * 转换为历史任务模型
     */
    private HistoricTask convertToHistoricTask(org.flowable.task.api.history.HistoricTaskInstance historicTask) {
        if (historicTask == null) {
            return null;
        }
        
        return new HistoricTask()
                .setId(historicTask.getId())
                .setName(historicTask.getName())
                .setDescription(historicTask.getDescription())
                .setProcessInstanceId(historicTask.getProcessInstanceId())
                .setProcessDefinitionId(historicTask.getProcessDefinitionId())
                .setTaskDefinitionKey(historicTask.getTaskDefinitionKey())
                .setFormKey(historicTask.getFormKey())
                .setPriority(historicTask.getPriority())
                .setOwner(historicTask.getOwner())
                .setAssignee(historicTask.getAssignee())
                .setStartTime(historicTask.getStartTime() != null ?
                        LocalDateTime.ofInstant(historicTask.getStartTime().toInstant(), ZoneId.systemDefault()) : null)
                .setClaimTime(historicTask.getClaimTime() != null ?
                        LocalDateTime.ofInstant(historicTask.getClaimTime().toInstant(), ZoneId.systemDefault()) : null)
                .setEndTime(historicTask.getEndTime() != null ?
                        LocalDateTime.ofInstant(historicTask.getEndTime().toInstant(), ZoneId.systemDefault()) : null)
                .setDurationInMillis(historicTask.getDurationInMillis())
                .setDeleteReason(historicTask.getDeleteReason())
                .setTaskLocalVariables(historicTask.getTaskLocalVariables())
                .setProcessVariables(historicTask.getProcessVariables())
                .setTenantId(historicTask.getTenantId());
    }
} 