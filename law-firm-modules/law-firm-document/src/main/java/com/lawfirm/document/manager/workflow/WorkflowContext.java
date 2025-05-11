package com.lawfirm.document.manager.workflow;

import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 文档工作流上下文，提供工作流相关的配置和上下文信息
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class WorkflowContext {
    
    private final ProcessService processService;
    private final TaskService taskService;
    
    /**
     * 获取文档审批流程定义ID
     * 
     * @return 流程定义ID
     */
    public String getDocumentApprovalProcessId() {
        return "document_approval_process";
    }
    
    /**
     * 获取文档传阅流程定义ID
     * 
     * @return 流程定义ID
     */
    public String getDocumentCirculationProcessId() {
        return "document_circulation_process";
    }
    
    /**
     * 获取文档归档流程定义ID
     * 
     * @return 流程定义ID
     */
    public String getDocumentArchiveProcessId() {
        return "document_archive_process";
    }
    
    /**
     * 获取流程服务
     * 
     * @return 流程服务
     */
    public ProcessService getProcessService() {
        return processService;
    }
    
    /**
     * 获取任务服务
     * 
     * @return 任务服务
     */
    public TaskService getTaskService() {
        return taskService;
    }
}
