package com.lawfirm.contract.service.impl;

import com.lawfirm.contract.config.WorkflowConfig;
import com.lawfirm.contract.service.ContractWorkflowService;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同工作流服务实现类
 * 集成core-workflow组件，负责合同审批流程的管理
 */
@Slf4j
@Service("contractWorkflowServiceImpl")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class ContractWorkflowServiceImpl implements ContractWorkflowService {

    /**
     * 工作流流程服务
     */
    private final ProcessService processService;
    
    /**
     * 工作流任务服务
     */
    private final TaskService taskService;

    @Override
    public String startApprovalProcess(Contract contract, Map<String, Object> variables) {
        log.info("启动合同审批流程，合同编号：{}", contract.getContractNo());
        
        // 准备流程变量
        if (variables == null) {
            variables = new HashMap<>();
        }
        
        // 设置基本变量
        variables.put("contractId", contract.getId());
        variables.put("contractNo", contract.getContractNo());
        variables.put("contractName", contract.getContractName());
        variables.put("amount", contract.getAmount());
        variables.put("createUserId", contract.getCreateBy());
        
        // 启动流程实例
        String processInstanceId = processService.startProcessInstance(
                WorkflowConfig.CONTRACT_APPROVAL_PROCESS_KEY,
                WorkflowConfig.CONTRACT_BUSINESS_KEY_PREFIX + contract.getId(),
                variables);
        
        log.info("合同审批流程已启动，流程实例ID：{}", processInstanceId);
        return processInstanceId;
    }

    @Override
    public boolean submitApproval(String taskId, boolean approved, String comment, Map<String, Object> variables) {
        log.info("提交合同审批，taskId：{}，审批结果：{}", taskId, approved ? "通过" : "拒绝");
        
        // 准备任务变量
        if (variables == null) {
            variables = new HashMap<>();
        }
        
        // 设置审批结果
        variables.put("approved", approved);
        variables.put("comment", comment);
        variables.put("approvalResult", approved ? WorkflowConfig.APPROVAL_RESULT_APPROVE : WorkflowConfig.APPROVAL_RESULT_REJECT);
        
        try {
            // 完成任务
            processService.completeTask(taskId, variables);
            log.info("合同审批任务已完成，taskId：{}", taskId);
            return true;
        } catch (Exception e) {
            log.error("提交合同审批失败", e);
            return false;
        }
    }

    @Override
    public boolean cancelApprovalProcess(Long contractId, String reason) {
        log.info("取消合同审批流程，合同ID：{}", contractId);
        
        try {
            // 获取流程实例ID
            String processInstanceId = getProcessInstanceIdByContractId(contractId);
            if (processInstanceId == null) {
                log.warn("未找到合同关联的流程实例，合同ID：{}", contractId);
                return false;
            }
            
            // 终止流程实例
            processService.terminateProcessInstance(processInstanceId, reason);
            log.info("合同审批流程已取消，流程实例ID：{}", processInstanceId);
            return true;
        } catch (Exception e) {
            log.error("取消合同审批流程失败", e);
            return false;
        }
    }

    @Override
    public List<TaskVO> getCurrentTasks(Long contractId) {
        // 获取流程实例ID
        String processInstanceId = getProcessInstanceIdByContractId(contractId);
        if (processInstanceId == null) {
            log.warn("未找到合同关联的流程实例，合同ID：{}", contractId);
            return List.of();
        }
        
        // 这里需要根据具体的TaskService接口实现查询当前任务
        // 由于接口可能不完全一致，下面是示例代码，可能需要根据实际情况调整
        return taskService.listProcessTasks(Long.valueOf(processInstanceId));
    }

    @Override
    public List<TaskVO> getUserPendingTasks(Long userId, int pageNum, int pageSize) {
        // 查询用户待办任务
        return taskService.listMyTodoTasks(userId);
    }

    @Override
    public List<TaskVO> getUserCompletedTasks(Long userId, int pageNum, int pageSize) {
        // 查询用户已办任务
        return taskService.listMyDoneTasks(userId);
    }

    @Override
    public boolean transferTask(String taskId, Long targetUserId, String reason) {
        log.info("转办合同审批任务，taskId：{}，目标用户：{}", taskId, targetUserId);
        
        try {
            // 转办任务
            taskService.transferTask(Long.valueOf(taskId), targetUserId, targetUserId.toString());
            log.info("合同审批任务已转办，taskId：{}", taskId);
            return true;
        } catch (Exception e) {
            log.error("转办合同审批任务失败", e);
            return false;
        }
    }

    @Override
    public boolean urgeTask(String taskId, String message) {
        log.info("催办合同审批任务，taskId：{}", taskId);
        
        // 获取任务信息
        TaskVO task = taskService.getTask(Long.valueOf(taskId));
        if (task == null) {
            log.warn("未找到任务信息，taskId：{}", taskId);
            return false;
        }
        
        // 发送催办通知
        // 这里需要调用通知服务发送消息，示例代码
        try {
            // 模拟发送通知
            log.info("发送催办通知，接收人：{}，消息：{}", task.getHandlerId(), message);
            return true;
        } catch (Exception e) {
            log.error("催办合同审批任务失败", e);
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getApprovalHistory(Long contractId) {
        // 获取流程实例ID
        String processInstanceId = getProcessInstanceIdByContractId(contractId);
        if (processInstanceId == null) {
            log.warn("未找到合同关联的流程实例，合同ID：{}", contractId);
            return List.of();
        }
        
        // 查询审批历史
        // 由于没有直接的审批历史查询接口，这里需要根据实际情况调整
        // 这里返回一个空列表作为示例
        return List.of();
    }
    
    /**
     * 根据合同ID获取流程实例ID
     * 
     * @param contractId 合同ID
     * @return 流程实例ID
     */
    private String getProcessInstanceIdByContractId(Long contractId) {
        // 实际项目中，可能需要从数据库查询流程实例ID
        // 这里简化处理，直接使用一个固定的业务键格式
        String businessKey = WorkflowConfig.CONTRACT_BUSINESS_KEY_PREFIX + contractId;
        
        // 这里需要根据业务键查询流程实例ID
        // 由于缺少直接的查询方法，这里返回null作为示例
        // 实际项目中需要调用合适的API查询
        return null;
    }
} 