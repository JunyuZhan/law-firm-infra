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
            flowableTaskService.setAssignee(taskId, userId);
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
    private Task convertToTask(org.flowable.task.api.Task flowableTask) {
        if (flowableTask == null) {
            return null;
        }
        
        Task task = new Task();
        task.setId(flowableTask.getId());
        task.setName(flowableTask.getName());
        task.setDescription(flowableTask.getDescription());
        task.setProcessInstanceId(flowableTask.getProcessInstanceId());
        task.setProcessDefinitionId(flowableTask.getProcessDefinitionId());
        task.setTaskDefinitionKey(flowableTask.getTaskDefinitionKey());
        task.setAssignee(flowableTask.getAssignee());
        task.setOwner(flowableTask.getOwner());
        task.setDelegationState(flowableTask.getDelegationState() != null ? 
            flowableTask.getDelegationState().toString() : null);
        task.setDueDate(flowableTask.getDueDate() != null ? 
            flowableTask.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
        task.setCreateTime(flowableTask.getCreateTime() != null ? 
            flowableTask.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
        task.setTenantId(flowableTask.getTenantId());
        task.setFormKey(flowableTask.getFormKey());
        task.setPriority(flowableTask.getPriority());
        task.setCategory(flowableTask.getCategory());
        
        return task;
    }
    
    /**
     * 转换为历史任务模型
     */
    private HistoricTask convertToHistoricTask(org.flowable.task.api.history.HistoricTaskInstance historicTask) {
        if (historicTask == null) {
            return null;
        }
        
        HistoricTask task = new HistoricTask();
        task.setId(historicTask.getId());
        task.setName(historicTask.getName());
        task.setDescription(historicTask.getDescription());
        task.setProcessInstanceId(historicTask.getProcessInstanceId());
        task.setProcessDefinitionId(historicTask.getProcessDefinitionId());
        task.setTaskDefinitionKey(historicTask.getTaskDefinitionKey());
        task.setFormKey(historicTask.getFormKey());
        task.setPriority(historicTask.getPriority());
        task.setOwner(historicTask.getOwner());
        task.setAssignee(historicTask.getAssignee());
        task.setCreateTime(historicTask.getStartTime() != null ?
                LocalDateTime.ofInstant(historicTask.getStartTime().toInstant(), ZoneId.systemDefault()) : null);
        task.setClaimTime(historicTask.getClaimTime() != null ?
                LocalDateTime.ofInstant(historicTask.getClaimTime().toInstant(), ZoneId.systemDefault()) : null);
        task.setDueDate(historicTask.getDueDate() != null ?
                LocalDateTime.ofInstant(historicTask.getDueDate().toInstant(), ZoneId.systemDefault()) : null);
        task.setDurationInMillis(historicTask.getDurationInMillis());
        task.setDeleteReason(historicTask.getDeleteReason());
        task.setTaskLocalVariables(historicTask.getTaskLocalVariables());
        task.setProcessVariables(historicTask.getProcessVariables());
        task.setTenantId(historicTask.getTenantId());
        task.setCategory(historicTask.getCategory());
        
        return task;
    }
} 