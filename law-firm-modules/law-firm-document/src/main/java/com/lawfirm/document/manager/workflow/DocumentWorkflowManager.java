package com.lawfirm.document.manager.workflow;

import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.vo.ProcessVO;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文档工作流管理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentWorkflowManager {

    private final ProcessService processService;
    private final TaskService taskService;

    /**
     * 提交审批
     *
     * @param documentId 文档ID
     * @param processKey 流程定义键
     * @return 流程实例ID
     */
    public String submitForApproval(Long documentId, String processKey) {
        // 1. 构建流程创建参数
        ProcessCreateDTO processDTO = new ProcessCreateDTO();
        processDTO.setBusinessKey("DOCUMENT_" + documentId);
        processDTO.setProcessDefinitionKey(processKey);
        processDTO.setCreator(SecurityUtils.getCurrentUsername());

        // 2. 创建审批流程
        ProcessVO process = processService.createProcess(processDTO);
        return process.getInstanceId();
    }

    /**
     * 审批操作
     *
     * @param documentId 文档ID
     * @param taskId 任务ID
     * @param approved 是否同意
     * @param comment 审批意见
     */
    public void approve(Long documentId, String taskId, Boolean approved, String comment) {
        // 1. 获取当前用户
        String currentUser = SecurityUtils.getCurrentUsername();

        // 2. 创建任务完成参数
        TaskCreateDTO taskDTO = new TaskCreateDTO();
        taskDTO.setTaskId(taskId);
        taskDTO.setApproved(approved);
        taskDTO.setComment(comment);
        taskDTO.setOperator(currentUser);

        // 3. 完成审批任务
        taskService.completeTask(taskDTO);
    }

    /**
     * 获取审批历史
     *
     * @param documentId 文档ID
     * @return 审批历史列表
     */
    public List<Map<String, Object>> getApprovalHistory(Long documentId) {
        // 获取流程历史记录
        return processService.getProcessHistory("DOCUMENT_" + documentId);
    }

    /**
     * 获取待办任务
     *
     * @return 任务列表
     */
    public List<TaskVO> getPendingTasks() {
        String currentUser = SecurityUtils.getCurrentUsername();
        return taskService.getPendingTasks(currentUser);
    }

    /**
     * 获取已办任务
     *
     * @return 任务列表
     */
    public List<TaskVO> getCompletedTasks() {
        String currentUser = SecurityUtils.getCurrentUsername();
        return taskService.getCompletedTasks(currentUser);
    }

    /**
     * 撤回审批
     *
     * @param documentId 文档ID
     * @param taskId 任务ID
     * @param reason 撤回原因
     */
    public void withdrawApproval(Long documentId, String taskId, String reason) {
        // 1. 获取当前用户
        String currentUser = SecurityUtils.getCurrentUsername();

        // 2. 创建任务撤回参数
        TaskCreateDTO taskDTO = new TaskCreateDTO();
        taskDTO.setTaskId(taskId);
        taskDTO.setComment(reason);
        taskDTO.setOperator(currentUser);

        // 3. 撤回审批任务
        taskService.withdrawTask(taskDTO);
    }

    /**
     * 终止流程
     *
     * @param documentId 文档ID
     * @param reason 终止原因
     */
    public void terminateProcess(Long documentId, String reason) {
        // 1. 获取当前用户
        String currentUser = SecurityUtils.getCurrentUsername();

        // 2. 终止流程
        processService.terminateProcess("DOCUMENT_" + documentId, reason, currentUser);
    }
}
