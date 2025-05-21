package com.lawfirm.core.workflow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.lawfirm.model.workflow.service.WorkflowService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 工作流服务空实现
 * <p>
 * 当系统禁用工作流功能时，提供默认的空操作实现
 * </p>
 */
@Slf4j
@Service("workflowService")
@Primary
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "false", matchIfMissing = true)
public class NoopWorkflowServiceImpl implements WorkflowService {

    @Override
    public String startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法启动流程: {}, 业务键: {}", processDefinitionKey, businessKey);
        return "workflow-disabled";
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法完成任务: {}", taskId);
    }

    @Override
    public List<Map<String, Object>> queryTasks(String assignee) {
        log.warn("工作流已禁用，无法查询任务: {}", assignee);
        return Collections.emptyList();
    }

    @Override
    public String getProcessStatus(String processInstanceId) {
        log.warn("工作流已禁用，无法获取流程状态: {}", processInstanceId);
        return "disabled";
    }

    @Override
    public boolean isWorkflowEnabled() {
        return false;
    }
}