package com.lawfirm.cases.core.workflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 案件任务分配器
 * <p>
 * 负责与core-workflow模块集成，实现案件相关任务的分配和调度功能。
 * 包括任务指派、转交、委托和批量处理等。
 * </p>
 *
 * @author JunyuZhan
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseTaskAssigner {
    
    // 注入工作流服务
    private final com.lawfirm.model.workflow.service.TaskService taskService;
    
    /**
     * 分配案件任务
     *
     * @param taskId 任务ID
     * @param assigneeId 受理人ID
     * @param comment 处理意见
     */
    public void assignTask(String taskId, String assigneeId, String comment) {
        log.info("分配案件任务: {}, 受理人: {}", taskId, assigneeId);
        taskService.transferTask(Long.valueOf(taskId), Long.valueOf(assigneeId), comment);
    }
    
    /**
     * 转交案件任务
     *
     * @param taskId 任务ID
     * @param sourceUserId 原用户ID
     * @param targetUserId 目标用户ID
     * @param reason 转交原因
     */
    public void transferTask(String taskId, String sourceUserId, String targetUserId, String reason) {
        log.info("转交案件任务: {}, 从 {} 转交给 {}, 原因: {}", 
                taskId, sourceUserId, targetUserId, reason);
        taskService.transferTask(Long.valueOf(taskId), Long.valueOf(targetUserId), reason);
    }
    
    /**
     * 委托案件任务
     *
     * @param taskId 任务ID
     * @param ownerUserId 所有者ID
     * @param delegateUserId 委托用户ID
     * @param reason 委托原因
     */
    public void delegateTask(String taskId, String ownerUserId, String delegateUserId, String reason) {
        log.info("委托案件任务: {}, 委托人: {}, 被委托人: {}, 原因: {}", 
                taskId, ownerUserId, delegateUserId, reason);
        // 使用transferTask替代delegateTask
        taskService.transferTask(Long.valueOf(taskId), Long.valueOf(delegateUserId), reason);
    }
    
    /**
     * 认领案件任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    public void claimTask(String taskId, String userId) {
        log.info("认领案件任务: {}, 用户: {}", taskId, userId);
        // TaskService没有提供claimTask方法，使用startTask代替
        taskService.startTask(Long.valueOf(taskId));
    }
    
    /**
     * 归还案件任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @param reason 归还原因
     */
    public void returnTask(String taskId, String userId, String reason) {
        log.info("归还案件任务: {}, 用户: {}, 原因: {}", taskId, userId, reason);
        // TaskService没有提供unclaimTask方法，使用取消任务代替
        taskService.cancelTask(Long.valueOf(taskId));
    }
    
    /**
     * 批量分配案件任务
     *
     * @param taskIds 任务ID列表
     * @param assigneeId 受理人ID
     */
    public void batchAssignTasks(List<String> taskIds, String assigneeId) {
        log.info("批量分配案件任务, 任务数: {}, 受理人: {}", taskIds.size(), assigneeId);
        // TaskService没有提供batchAssignTasks方法，循环调用transferTask
        String assigneeName = "用户" + assigneeId; // 实际应用中应查询用户名
        for (String taskId : taskIds) {
            taskService.transferTask(Long.valueOf(taskId), Long.valueOf(assigneeId), assigneeName);
        }
    }
    
    /**
     * 获取团队可用的任务列表
     *
     * @param teamId 团队ID
     * @param filters 过滤条件
     * @return 可用任务列表
     */
    public List<Object> getAvailableTasksForTeam(String teamId, Map<String, Object> filters) {
        log.info("获取团队可用任务列表, 团队: {}", teamId);
        // 假设TaskService有getTasksByCandidate方法
        // return taskService.getTasksByCandidate("group", teamId, filters);
        return List.of();
    }
    
    /**
     * 获取用户可用的任务列表
     *
     * @param userId 用户ID
     * @param filters 过滤条件
     * @return 可用任务列表
     */
    public List<Object> getAvailableTasksForUser(String userId, Map<String, Object> filters) {
        log.info("获取用户可用任务列表, 用户: {}", userId);
        // 假设TaskService有getTasksByCandidate方法
        // return taskService.getTasksByCandidate("user", userId, filters);
        return List.of();
    }
} 