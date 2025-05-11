package com.lawfirm.document.manager.workflow;

import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.vo.ProcessVO;
import com.lawfirm.model.workflow.vo.TaskVO;
import com.lawfirm.model.workflow.enums.ProcessStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文档工作流管理器，处理文档的工作流相关操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class WorkflowManager {

    private final ProcessService processService;
    private final TaskService taskService;
    private final WorkflowContext workflowContext;

    /**
     * 启动文档审批流程
     *
     * @param documentId 文档ID
     * @param variables 流程变量
     * @return 流程实例ID
     */
    public String startDocumentApprovalProcess(String documentId, Map<String, Object> variables) {
        String processId = workflowContext.getDocumentApprovalProcessId();
        return processService.startProcessInstance(processId, documentId, variables);
    }

    /**
     * 启动文档传阅流程
     *
     * @param documentId 文档ID
     * @param variables 流程变量
     * @return 流程实例ID
     */
    public String startDocumentCirculationProcess(String documentId, Map<String, Object> variables) {
        String processId = workflowContext.getDocumentCirculationProcessId();
        return processService.startProcessInstance(processId, documentId, variables);
    }

    /**
     * 启动文档归档流程
     *
     * @param documentId 文档ID
     * @param variables 流程变量
     * @return 流程实例ID
     */
    public String startDocumentArchiveProcess(String documentId, Map<String, Object> variables) {
        String processId = workflowContext.getDocumentArchiveProcessId();
        return processService.startProcessInstance(processId, documentId, variables);
    }

    /**
     * 获取用户的待办任务
     *
     * @param userId 用户ID
     * @return 任务列表
     */
    public List<TaskVO> getUserTasks(Long userId) {
        return taskService.listMyTodoTasks(userId);
    }

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param result 处理结果
     * @param comment 处理意见
     */
    public void completeTask(Long taskId, String result, String comment) {
        taskService.completeTask(taskId, result, comment);
    }

    /**
     * 获取流程实例状态
     *
     * @param processInstanceId 流程实例ID
     * @return 流程状态
     */
    public ProcessStatusEnum getProcessStatus(String processInstanceId) {
        ProcessVO process = processService.getProcess(Long.parseLong(processInstanceId));
        return process.getStatus();
    }

    /**
     * 终止流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param reason 终止原因
     */
    public void terminateProcess(String processInstanceId, String reason) {
        processService.terminateProcessInstance(processInstanceId, reason);
    }
}
