package com.lawfirm.core.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.core.workflow.adapter.converter.TaskConverter;
import com.lawfirm.model.workflow.mapper.TaskMapper;
import com.lawfirm.model.workflow.dto.task.TaskCreateDTO;
import com.lawfirm.model.workflow.dto.task.TaskQueryDTO;
import com.lawfirm.model.workflow.entity.base.ProcessTask;
import com.lawfirm.model.workflow.service.NotificationService;
import com.lawfirm.model.workflow.service.TaskService;
import com.lawfirm.model.workflow.service.UserService;
import com.lawfirm.model.workflow.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.TaskInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务服务实现类
 * 提供任务管理相关的所有业务功能实现
 *
 * @author JunyuZhan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends ServiceImpl<TaskMapper, ProcessTask> implements TaskService {

    private final org.flowable.engine.TaskService flowableTaskService;
    private final RuntimeService runtimeService;
    private final TaskMapper taskMapper;
    private final TaskConverter taskConverter;
    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskVO createTask(TaskCreateDTO createDTO) {
        log.info("创建任务: {}", createDTO);
        Assert.notNull(createDTO, "创建参数不能为空");
        Assert.hasText(createDTO.getTaskName(), "任务名称不能为空");
        Assert.notNull(createDTO.getTaskType(), "任务类型不能为空");
        
        // 转换为实体
        ProcessTask task = taskConverter.toEntity(createDTO);
        
        // 设置初始状态
        task.setStatus(0); // 0-待处理
        
        // 保存任务
        save(task);
        
        // 发送任务创建通知
        if (task.getHandlerId() != null) {
            sendTaskCreatedNotification(
                String.valueOf(task.getId()),
                task.getTaskName(),
                String.valueOf(task.getHandlerId()),
                null
            );
        }
        
        return taskConverter.toVO(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long id) {
        log.info("删除任务: {}", id);
        Assert.notNull(id, "任务ID不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            return;
        }
        
        // 删除任务
        taskMapper.deleteById(id);
        
        // 如果是流程任务，同时删除Flowable中的任务
        if (task.getProcessId() != null) {
            org.flowable.task.api.Task flowableTask = flowableTaskService.createTaskQuery()
                .processInstanceId(task.getProcessNo())
                .singleResult();
            if (flowableTask != null) {
                flowableTaskService.deleteTask(flowableTask.getId(), "手动删除任务");
            }
        }
    }

    @Override
    public TaskVO getTask(Long id) {
        log.info("获取任务详情: {}", id);
        Assert.notNull(id, "任务ID不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            return null;
        }
        
        // 转换为视图对象
        return taskConverter.toVO(task);
    }

    @Override
    public List<TaskVO> listTasks(TaskQueryDTO queryDTO) {
        log.info("查询任务列表: {}", queryDTO);
        Assert.notNull(queryDTO, "查询参数不能为空");
        
        LambdaQueryWrapper<ProcessTask> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        if (queryDTO.getTaskName() != null) {
            wrapper.like(ProcessTask::getTaskName, queryDTO.getTaskName());
        }
        if (queryDTO.getTaskType() != null) {
            wrapper.eq(ProcessTask::getTaskType, queryDTO.getTaskType());
        }
        if (queryDTO.getHandlerId() != null) {
            wrapper.eq(ProcessTask::getHandlerId, queryDTO.getHandlerId());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(ProcessTask::getStatus, queryDTO.getStatus());
        }
        
        return list(wrapper).stream()
                .map(taskConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startTask(Long id) {
        log.info("开始处理任务: {}", id);
        Assert.notNull(id, "任务ID不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        
        // 检查任务状态
        if (task.getStatus() != 0) {
            throw new IllegalStateException("任务状态不正确");
        }
        
        // 更新任务状态
        task.setStatus(1); // 1-处理中
        taskMapper.updateById(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(Long id, String result, String comment) {
        log.info("完成任务: {}, 处理结果: {}, 处理意见: {}", id, result, comment);
        Assert.notNull(id, "任务ID不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        
        // 检查任务状态
        if (task.getStatus() != 1) {
            throw new IllegalStateException("任务状态不正确");
        }
        
        // 更新任务
        task.setStatus(2); // 2-已完成
        task.setResult(result);
        task.setComment(comment);
        taskMapper.updateById(task);
        
        // 如果是流程任务，同时完成Flowable中的任务
        if (task.getProcessId() != null) {
            org.flowable.task.api.Task flowableTask = flowableTaskService.createTaskQuery()
                .processInstanceId(task.getProcessNo())
                .singleResult();
            if (flowableTask != null) {
                flowableTaskService.complete(flowableTask.getId());
            }
        }
        
        // 发送任务完成通知
        sendTaskCompletedNotification(
            String.valueOf(task.getId()),
            task.getTaskName(),
            String.valueOf(task.getHandlerId()),
            null
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(Long id) {
        log.info("取消任务: {}", id);
        Assert.notNull(id, "任务ID不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        
        // 检查任务状态
        if (task.getStatus() == 2 || task.getStatus() == 3) {
            throw new IllegalStateException("任务已完成或已取消");
        }
        
        // 更新任务状态
        task.setStatus(3); // 3-已取消
        taskMapper.updateById(task);
        
        // 如果是流程任务，同时取消Flowable中的任务
        if (task.getProcessId() != null) {
            org.flowable.task.api.Task flowableTask = flowableTaskService.createTaskQuery()
                .processInstanceId(task.getProcessNo())
                .singleResult();
            if (flowableTask != null) {
                flowableTaskService.deleteTask(flowableTask.getId(), "手动取消任务");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferTask(Long id, Long handlerId, String handlerName) {
        log.info("转办任务: {}, 新处理人: {}({})", id, handlerName, handlerId);
        Assert.notNull(id, "任务ID不能为空");
        Assert.notNull(handlerId, "处理人ID不能为空");
        Assert.hasText(handlerName, "处理人名称不能为空");
        
        // 查询任务
        ProcessTask task = taskMapper.selectById(id);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        
        // 更新处理人
        String oldHandlerName = task.getHandlerName();
        Long oldHandlerId = task.getHandlerId();
        
        task.setHandlerId(handlerId);
        task.setHandlerName(handlerName);
        taskMapper.updateById(task);
        
        // 如果是流程任务，同时更新Flowable中的任务
        if (task.getProcessId() != null) {
            org.flowable.task.api.Task flowableTask = flowableTaskService.createTaskQuery()
                .processInstanceId(task.getProcessNo())
                .singleResult();
            if (flowableTask != null) {
                flowableTaskService.setAssignee(flowableTask.getId(), String.valueOf(handlerId));
            }
        }
        
        // 发送任务分配通知
        sendTaskAssignedNotification(
            String.valueOf(task.getId()),
            task.getTaskName(),
            String.valueOf(handlerId),
            String.valueOf(oldHandlerId),
            null
        );
    }

    @Override
    public List<TaskVO> listProcessTasks(Long processId) {
        log.info("查询流程任务列表: {}", processId);
        Assert.notNull(processId, "流程ID不能为空");
        
        LambdaQueryWrapper<ProcessTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessTask::getProcessId, processId);
        return list(wrapper).stream()
                .map(taskConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskVO> listMyTodoTasks(Long userId) {
        log.info("获取我的待办任务: {}", userId);
        Assert.notNull(userId, "处理人ID不能为空");
        
        LambdaQueryWrapper<ProcessTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessTask::getHandlerId, userId)
               .eq(ProcessTask::getStatus, 0); // 待处理状态
        return list(wrapper).stream()
                .map(taskConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskVO> listMyDoneTasks(Long userId) {
        log.info("获取我的已办任务: {}", userId);
        Assert.notNull(userId, "处理人ID不能为空");
        
        LambdaQueryWrapper<ProcessTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessTask::getHandlerId, userId)
               .eq(ProcessTask::getStatus, 2); // 已完成状态
        return list(wrapper).stream()
                .map(taskConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getCandidateUsersByRole(Long roleId) {
        log.info("根据角色获取候选用户列表: {}", roleId);
        Assert.notNull(roleId, "角色ID不能为空");
        
        // 调用用户服务获取角色下的用户列表
        return userService.getUsersByRole(roleId);
    }

    @Override
    public List<Long> getCandidateUsersByDept(Long deptId) {
        log.info("根据部门获取候选用户列表: {}", deptId);
        Assert.notNull(deptId, "部门ID不能为空");
        
        // 调用用户服务获取部门下的用户列表
        return userService.getUsersByDept(deptId);
    }

    @Override
    public Long autoAssignTask(String taskId, List<Long> candidateUserIds) {
        log.info("自动分配任务: {}, 候选用户列表: {}", taskId, candidateUserIds);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.notEmpty(candidateUserIds, "候选用户列表不能为空");
        
        // 首先尝试负载均衡分配
        Long assigneeId = loadBalanceAssignTask(taskId, candidateUserIds);
        if (assigneeId != null) {
            return assigneeId;
        }
        
        // 如果负载均衡分配失败，则使用轮询分配
        return roundRobinAssignTask(taskId, candidateUserIds);
    }

    @Override
    public Long roundRobinAssignTask(String taskId, List<Long> candidateUserIds) {
        log.info("轮询分配任务: {}, 候选用户列表: {}", taskId, candidateUserIds);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.notEmpty(candidateUserIds, "候选用户列表不能为空");
        
        // 获取上一次分配的用户索引
        int lastIndex = getLastAssignedIndex(taskId);
        
        // 计算下一个用户索引
        int nextIndex = (lastIndex + 1) % candidateUserIds.size();
        
        // 获取分配的用户ID
        Long assigneeId = candidateUserIds.get(nextIndex);
        
        // 保存分配记录
        saveAssignRecord(taskId, assigneeId, nextIndex);
        
        return assigneeId;
    }

    @Override
    public Long loadBalanceAssignTask(String taskId, List<Long> candidateUserIds) {
        log.info("负载均衡分配任务: {}, 候选用户列表: {}", taskId, candidateUserIds);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.notEmpty(candidateUserIds, "候选用户列表不能为空");
        
        Long minLoadUserId = null;
        double minLoadScore = Double.MAX_VALUE;
        
        // 遍历候选用户，找出负载最小的用户
        for (Long userId : candidateUserIds) {
            double loadScore = calculateUserTaskLoadScore(userId);
            if (loadScore < minLoadScore) {
                minLoadScore = loadScore;
                minLoadUserId = userId;
            }
        }
        
        return minLoadUserId;
    }

    @Override
    public int getUserTaskCount(Long userId) {
        log.info("获取用户当前任务数量: {}", userId);
        Assert.notNull(userId, "用户ID不能为空");
        
        // 查询用户当前任务数量
        return taskMapper.selectUserTaskCount(userId);
    }

    @Override
    public double calculateUserTaskLoadScore(Long userId) {
        log.info("计算用户任务负载分数: {}", userId);
        Assert.notNull(userId, "用户ID不能为空");
        
        // 获取用户当前任务数量
        int taskCount = getUserTaskCount(userId);
        
        // 计算基础负载分数
        double baseScore = taskCount * 1.0;
        
        // TODO: 可以根据任务优先级、截止时间等因素调整负载分数
        
        return baseScore;
    }

    /**
     * 获取上一次分配的用户索引
     * 
     * @param taskId 任务ID
     * @return 上一次分配的用户索引
     */
    private int getLastAssignedIndex(String taskId) {
        // TODO: 从缓存或数据库中获取上一次分配的用户索引
        return 0;
    }
    
    /**
     * 保存分配记录
     * 
     * @param taskId 任务ID
     * @param assigneeId 处理人ID
     * @param assignedIndex 分配的用户索引
     */
    private void saveAssignRecord(String taskId, Long assigneeId, int assignedIndex) {
        // TODO: 保存分配记录到缓存或数据库中
    }

    @Override
    public void sendTaskCreatedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables) {
        log.info("发送任务创建通知: {}, {}, {}", taskId, taskName, assigneeId);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.hasText(taskName, "任务名称不能为空");
        Assert.hasText(assigneeId, "处理人ID不能为空");
        
        String subject = "新任务通知";
        String content = String.format("您有一个新的任务需要处理：%s（任务ID：%s）", taskName, taskId);
        
        notificationService.sendNotification(assigneeId, subject, content, variables);
    }

    @Override
    public void sendTaskAssignedNotification(String taskId, String taskName, String newAssigneeId, String oldAssigneeId, Map<String, Object> variables) {
        log.info("发送任务分配通知: {}, {}, {}, {}", taskId, taskName, newAssigneeId, oldAssigneeId);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.hasText(taskName, "任务名称不能为空");
        Assert.hasText(newAssigneeId, "新处理人ID不能为空");
        
        // 发送给新处理人
        String newSubject = "任务分配通知";
        String newContent = String.format("任务\"%s\"（任务ID：%s）已分配给您处理", taskName, taskId);
        notificationService.sendNotification(newAssigneeId, newSubject, newContent, variables);
        
        // 如果存在原处理人，也发送通知
        if (oldAssigneeId != null) {
            String oldSubject = "任务转办通知";
            String oldContent = String.format("任务\"%s\"（任务ID：%s）已转办给其他人处理", taskName, taskId);
            notificationService.sendNotification(oldAssigneeId, oldSubject, oldContent, variables);
        }
    }

    @Override
    public void sendTaskCompletedNotification(String taskId, String taskName, String assigneeId, Map<String, Object> variables) {
        log.info("发送任务完成通知: {}, {}, {}", taskId, taskName, assigneeId);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.hasText(taskName, "任务名称不能为空");
        Assert.hasText(assigneeId, "处理人ID不能为空");
        
        String subject = "任务完成通知";
        String content = String.format("任务\"%s\"（任务ID：%s）已完成处理", taskName, taskId);
        
        notificationService.sendNotification(assigneeId, subject, content, variables);
    }

    @Override
    public void sendTaskDueReminder(String taskId, String taskName, String assigneeId, Date dueDate) {
        log.info("发送任务过期提醒: {}, {}, {}, {}", taskId, taskName, assigneeId, dueDate);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.hasText(taskName, "任务名称不能为空");
        Assert.hasText(assigneeId, "处理人ID不能为空");
        Assert.notNull(dueDate, "截止时间不能为空");
        
        String subject = "任务即将过期提醒";
        String content = String.format("您的任务\"%s\"（任务ID：%s）即将于%tF %tT到期，请及时处理", 
            taskName, taskId, dueDate, dueDate);
        
        notificationService.sendNotification(assigneeId, subject, content, null);
    }

    @Override
    public void sendTaskOverdueNotification(String taskId, String taskName, String assigneeId, Date dueDate) {
        log.info("发送任务超时提醒: {}, {}, {}, {}", taskId, taskName, assigneeId, dueDate);
        Assert.hasText(taskId, "任务ID不能为空");
        Assert.hasText(taskName, "任务名称不能为空");
        Assert.hasText(assigneeId, "处理人ID不能为空");
        Assert.notNull(dueDate, "截止时间不能为空");
        
        String subject = "任务已超时提醒";
        String content = String.format("您的任务\"%s\"（任务ID：%s）已%tF %tT超时，请尽快处理", 
            taskName, taskId, dueDate, dueDate);
        
        notificationService.sendNotification(assigneeId, subject, content, null);
    }

    @Override
    public void sendBatchTaskNotification(List<String> recipientIds, String subject, String content, Map<String, Object> variables) {
        log.info("发送批量任务通知: {}, {}, {}", recipientIds, subject, content);
        Assert.notEmpty(recipientIds, "接收人列表不能为空");
        Assert.hasText(subject, "通知主题不能为空");
        Assert.hasText(content, "通知内容不能为空");
        
        notificationService.sendBatchNotification(recipientIds, subject, content, variables);
    }
}
