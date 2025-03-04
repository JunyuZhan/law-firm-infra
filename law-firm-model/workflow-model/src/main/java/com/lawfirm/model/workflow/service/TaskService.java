package com.lawfirm.model.workflow.service;

import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskQueryDTO;
import com.lawfirm.model.workflow.vo.TaskVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务服务接口
 * 提供任务管理相关的所有业务功能，包括基础任务操作、任务分配和任务通知
 *
 * @author JunyuZhan
 */
public interface TaskService {

    // =============== 基础任务操作 ===============

    /**
     * 创建任务
     *
     * @param createDTO 创建参数
     * @return 任务ID
     */
    TaskVO createTask(TaskCreateDTO createDTO);

    /**
     * 删除任务
     *
     * @param id 任务ID
     */
    void deleteTask(Long id);

    /**
     * 获取任务详情
     *
     * @param id 任务ID
     * @return 任务详情
     */
    TaskVO getTask(Long id);

    /**
     * 查询任务列表
     *
     * @param queryDTO 查询参数
     * @return 任务列表
     */
    List<TaskVO> listTasks(TaskQueryDTO queryDTO);

    /**
     * 开始处理任�?     *
     * @param id 任务ID
     */
    void startTask(Long id);

    /**
     * 完成任务
     *
     * @param id 任务ID
     * @param result 处理结果
     * @param comment 处理意见
     */
    void completeTask(Long id, String result, String comment);

    /**
     * 取消任务
     *
     * @param id 任务ID
     */
    void cancelTask(Long id);

    /**
     * 转办任务
     *
     * @param id 任务ID
     * @param handlerId 处理人ID
     * @param handlerName 处理人名�?     */
    void transferTask(Long id, Long handlerId, String handlerName);

    /**
     * 获取流程的任务列�?     *
     * @param processId 流程ID
     * @return 任务列表
     */
    List<TaskVO> listProcessTasks(Long processId);

    /**
     * 获取我的待办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<TaskVO> listMyTodoTasks(Long handlerId);

    /**
     * 获取我的已办任务
     *
     * @param handlerId 处理人ID
     * @return 任务列表
     */
    List<TaskVO> listMyDoneTasks(Long handlerId);
    
    // =============== 任务分配 ===============
    
    /**
     * 根据角色获取候选用�?     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> getCandidateUsersByRole(Long roleId);

    /**
     * 根据部门获取候选用�?     *
     * @param deptId 部门ID
     * @return 用户ID列表
     */
    List<Long> getCandidateUsersByDept(Long deptId);

    /**
     * 自动分配任务给最合适的处理�?     * 
     * @param taskId 任务ID
     * @param candidateUserIds 候选用户ID列表
     * @return 处理人ID
     */
    Long autoAssignTask(String taskId, List<Long> candidateUserIds);

    /**
     * 按照轮询策略分配任务
     * 
     * @param taskId 任务ID
     * @param candidateUserIds 候选用户ID列表
     * @return 处理人ID
     */
    Long roundRobinAssignTask(String taskId, List<Long> candidateUserIds);

    /**
     * 按照负载策略分配任务
     * 
     * @param taskId 任务ID
     * @param candidateUserIds 候选用户ID列表
     * @return 处理人ID
     */
    Long loadBalanceAssignTask(String taskId, List<Long> candidateUserIds);

    /**
     * 获取用户当前任务数量
     * 
     * @param userId 用户ID
     * @return 任务数量
     */
    int getUserTaskCount(Long userId);

    /**
     * 计算用户的任务负载分�?     * 分数越低表示负载越轻
     * 
     * @param userId 用户ID
     * @return 负载分数
     */
    double calculateUserTaskLoadScore(Long userId);
    
    // =============== 任务通知 ===============
    
    /**
     * 发送任务创建通知
     *
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assigneeId 处理人ID
     * @param variables 通知变量
     */
    void sendTaskCreatedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables);

    /**
     * 发送任务分配通知
     *
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assigneeId 处理人ID
     * @param oldAssigneeId 原处理人ID
     * @param variables 通知变量
     */
    void sendTaskAssignedNotification(String taskId, String taskName, String assigneeId, String oldAssigneeId, Map<String, Object> variables);

    /**
     * 发送任务完成通知
     *
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assigneeId 处理人ID
     * @param variables 通知变量
     */
    void sendTaskCompletedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables);

    /**
     * 发送任务过期提�?     *
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assigneeId 处理人ID
     * @param dueDate 截止日期
     */
    void sendTaskDueReminder(String taskId, String taskName, String assigneeId, Date dueDate);

    /**
     * 发送任务超时提�?     *
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param assigneeId 处理人ID
     * @param dueDate 截止日期
     */
    void sendTaskOverdueNotification(String taskId, String taskName, String assigneeId, Date dueDate);

    /**
     * 发送批量任务通知
     *
     * @param recipientIds 接收人ID列表
     * @param subject 通知主题
     * @param content 通知内容
     * @param variables 通知变量
     */
    void sendBatchTaskNotification(List<String> recipientIds, String subject, String content, Map<String, Object> variables);
} 
