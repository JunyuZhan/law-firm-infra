package com.lawfirm.core.workflow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.flowable.task.api.Task;
import org.flowable.engine.runtime.ProcessInstance;

import com.lawfirm.model.workflow.service.WorkflowService;
import com.lawfirm.core.workflow.adapter.flowable.FlowableTaskAdapter;
import com.lawfirm.core.workflow.adapter.flowable.FlowableProcessAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 工作流服务实现
 * <p>
 * 当系统启用工作流功能时使用的实现
 * </p>
 */
@Slf4j
@Service("workflowService")
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true")
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private FlowableProcessAdapter processAdapter;
    
    @Autowired
    private FlowableTaskAdapter taskAdapter;

    @Override
    public String startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        log.info("启动流程: {}, 业务键: {}", processDefinitionKey, businessKey);
        
        try {
            // 使用Flowable适配器启动流程
            String processInstanceId = processAdapter.startProcess(processDefinitionKey, businessKey, variables);
            log.info("流程启动成功，实例ID: {}", processInstanceId);
            return processInstanceId;
        } catch (Exception e) {
            log.error("启动流程失败: {}", e.getMessage(), e);
            throw new RuntimeException("启动流程失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        log.info("完成任务: {}", taskId);
        
        try {
            // 使用Flowable适配器完成任务
            taskAdapter.completeTask(taskId, variables);
            log.info("任务完成成功: {}", taskId);
        } catch (Exception e) {
            log.error("完成任务失败: {}", e.getMessage(), e);
            throw new RuntimeException("完成任务失败: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> queryTasks(String assignee) {
        log.info("查询任务: {}", assignee);
        
        try {
            // 使用Flowable适配器查询任务
            List<Task> tasks = taskAdapter.listUserTodoTasks(assignee);
            if (tasks == null || tasks.isEmpty()) {
                return Collections.emptyList();
            }
            
            // 将任务转换为统一格式
            List<Map<String, Object>> results = new ArrayList<>();
            for (Task task : tasks) {
                Map<String, Object> taskMap = convertTaskToMap(task);
                results.add(taskMap);
            }
            
            return results;
        } catch (Exception e) {
            log.error("查询任务失败: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public String getProcessStatus(String processInstanceId) {
        log.info("获取流程状态: {}", processInstanceId);
        
        try {
            // 判断流程实例是否存在
            boolean exists = processAdapter.getProcessInstance(processInstanceId) != null;
            if (!exists) {
                return "not_found";
            }
            
            // 判断流程实例是否已完成
            boolean hasEnded = processAdapter.isProcessEnded(processInstanceId);
            if (hasEnded) {
                return "completed";
            }
            
            // 尝试获取流程实例，检查是否挂起状态
            ProcessInstance processInstance = processAdapter.getProcessInstance(processInstanceId);
            boolean isSuspended = processInstance != null && processInstance.isSuspended();
            if (isSuspended) {
                return "suspended";
            }
            
            return "running";
        } catch (Exception e) {
            log.error("获取流程状态失败: {}", e.getMessage(), e);
            return "error";
        }
    }

    @Override
    public boolean isWorkflowEnabled() {
        return true;
    }
    
    /**
     * 将任务对象转换为Map
     */
    private Map<String, Object> convertTaskToMap(Task task) {
        Map<String, Object> map = new HashMap<>();
        
        if (task != null) {
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("description", task.getDescription());
            map.put("assignee", task.getAssignee());
            map.put("owner", task.getOwner());
            map.put("createTime", task.getCreateTime());
            map.put("dueDate", task.getDueDate());
            map.put("priority", task.getPriority());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("formKey", task.getFormKey());
            map.put("category", task.getCategory());
        }
        
        return map;
    }
} 