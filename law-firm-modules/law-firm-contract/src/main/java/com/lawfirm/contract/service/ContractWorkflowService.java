package com.lawfirm.contract.service;

import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.workflow.vo.TaskVO;

import java.util.List;
import java.util.Map;

/**
 * 合同工作流服务接口
 * 集成core-workflow组件，负责合同审批流程的管理
 */
public interface ContractWorkflowService {
    
    /**
     * 启动合同审批流程
     *
     * @param contract 合同信息
     * @param variables 流程变量
     * @return 流程实例ID
     */
    String startApprovalProcess(Contract contract, Map<String, Object> variables);
    
    /**
     * 提交合同审批
     *
     * @param taskId 任务ID
     * @param approved 是否通过
     * @param comment 审批意见
     * @param variables 流程变量
     * @return 是否处理成功
     */
    boolean submitApproval(String taskId, boolean approved, String comment, Map<String, Object> variables);
    
    /**
     * 取消合同审批流程
     *
     * @param contractId 合同ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelApprovalProcess(Long contractId, String reason);
    
    /**
     * 获取合同当前审批任务
     *
     * @param contractId 合同ID
     * @return 当前任务列表
     */
    List<TaskVO> getCurrentTasks(Long contractId);
    
    /**
     * 获取当前用户的待办合同审批任务
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 任务列表
     */
    List<TaskVO> getUserPendingTasks(Long userId, int pageNum, int pageSize);
    
    /**
     * 获取当前用户的已办合同审批任务
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 任务列表
     */
    List<TaskVO> getUserCompletedTasks(Long userId, int pageNum, int pageSize);
    
    /**
     * 转办合同审批任务
     *
     * @param taskId 任务ID
     * @param targetUserId 目标用户ID
     * @param reason 转办原因
     * @return 是否转办成功
     */
    boolean transferTask(String taskId, Long targetUserId, String reason);
    
    /**
     * 催办合同审批任务
     *
     * @param taskId 任务ID
     * @param message 催办消息
     * @return 是否催办成功
     */
    boolean urgeTask(String taskId, String message);
    
    /**
     * 获取合同审批历史
     *
     * @param contractId 合同ID
     * @return 审批历史记录
     */
    List<Map<String, Object>> getApprovalHistory(Long contractId);
} 