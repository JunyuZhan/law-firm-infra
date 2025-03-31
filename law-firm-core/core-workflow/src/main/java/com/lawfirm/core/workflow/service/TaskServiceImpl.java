package com.lawfirm.core.workflow.service;

import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskQueryDTO;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务服务空实现
 * <p>
 * 当工作流模块被禁用时，提供所有任务相关操作的空实现
 * </p>
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "lawfirm", name = "workflow.enabled", havingValue = "false", matchIfMissing = true)
public class TaskServiceImpl implements TaskService {

    // =============== 基础任务操作 ===============

    @Override
    public TaskVO createTask(TaskCreateDTO createDTO) {
        log.warn("工作流已禁用，无法创建任务: {}", createDTO);
        return null;
    }

    @Override
    public void deleteTask(Long id) {
        log.warn("工作流已禁用，无法删除任务: {}", id);
    }

    @Override
    public TaskVO getTask(Long id) {
        log.warn("工作流已禁用，无法获取任务详情: {}", id);
        return null;
    }

    @Override
    public List<TaskVO> listTasks(TaskQueryDTO queryDTO) {
        log.warn("工作流已禁用，无法查询任务列表");
        return Collections.emptyList();
    }

    @Override
    public void startTask(Long id) {
        log.warn("工作流已禁用，无法开始处理任务: {}", id);
    }

    @Override
    public void completeTask(Long id, String result, String comment) {
        log.warn("工作流已禁用，无法完成任务: {}, 结果: {}", id, result);
    }

    @Override
    public void cancelTask(Long id) {
        log.warn("工作流已禁用，无法取消任务: {}", id);
    }

    @Override
    public void transferTask(Long id, Long handlerId, String handlerName) {
        log.warn("工作流已禁用，无法转办任务: {}, 处理人: {}", id, handlerName);
    }

    @Override
    public List<TaskVO> listProcessTasks(Long processId) {
        log.warn("工作流已禁用，无法获取流程任务列表: {}", processId);
        return Collections.emptyList();
    }

    @Override
    public List<TaskVO> listMyTodoTasks(Long handlerId) {
        log.warn("工作流已禁用，无法获取用户待办任务: {}", handlerId);
        return Collections.emptyList();
    }

    @Override
    public List<TaskVO> listMyDoneTasks(Long handlerId) {
        log.warn("工作流已禁用，无法获取用户已办任务: {}", handlerId);
        return Collections.emptyList();
    }

    // =============== 任务分配 ===============

    @Override
    public List<Long> getCandidateUsersByRole(Long roleId) {
        log.warn("工作流已禁用，无法根据角色获取候选用户: {}", roleId);
        return Collections.emptyList();
    }

    @Override
    public List<Long> getCandidateUsersByDept(Long deptId) {
        log.warn("工作流已禁用，无法根据部门获取候选用户: {}", deptId);
        return Collections.emptyList();
    }

    @Override
    public Long autoAssignTask(String taskId, List<Long> candidateUserIds) {
        log.warn("工作流已禁用，无法自动分配任务: {}", taskId);
        return null;
    }

    @Override
    public Long roundRobinAssignTask(String taskId, List<Long> candidateUserIds) {
        log.warn("工作流已禁用，无法按轮询策略分配任务: {}", taskId);
        return null;
    }

    @Override
    public Long loadBalanceAssignTask(String taskId, List<Long> candidateUserIds) {
        log.warn("工作流已禁用，无法按负载策略分配任务: {}", taskId);
        return null;
    }

    @Override
    public int getUserTaskCount(Long userId) {
        log.warn("工作流已禁用，无法获取用户任务数量: {}", userId);
        return 0;
    }

    @Override
    public double calculateUserTaskLoadScore(Long userId) {
        log.warn("工作流已禁用，无法计算用户任务负载分数: {}", userId);
        return 0.0;
    }

    // =============== 任务通知 ===============

    @Override
    public void sendTaskCreatedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法发送任务创建通知: {}, 受理人: {}", taskName, assigneeId);
    }

    @Override
    public void sendTaskAssignedNotification(String taskId, String taskName, String assigneeId, String oldAssigneeId, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法发送任务分配通知: {}, 受理人: {}", taskName, assigneeId);
    }

    @Override
    public void sendTaskCompletedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法发送任务完成通知: {}, 受理人: {}", taskName, assigneeId);
    }

    @Override
    public void sendTaskDueReminder(String taskId, String taskName, String assigneeId, Date dueDate) {
        log.warn("工作流已禁用，无法发送任务过期提醒: {}, 受理人: {}", taskName, assigneeId);
    }

    @Override
    public void sendTaskOverdueNotification(String taskId, String taskName, String assigneeId, Date dueDate) {
        log.warn("工作流已禁用，无法发送任务超时提醒: {}, 受理人: {}", taskName, assigneeId);
    }

    @Override
    public void sendBatchTaskNotification(List<String> recipientIds, String subject, String content, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法发送批量任务通知: {}, 接收人数: {}", subject, recipientIds.size());
    }
}
