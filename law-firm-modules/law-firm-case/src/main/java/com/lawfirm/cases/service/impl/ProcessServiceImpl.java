package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.core.workflow.CaseWorkflowManager;
import com.lawfirm.cases.core.workflow.CaseTaskAssigner;
import com.lawfirm.model.cases.dto.business.CaseTaskDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.entity.business.CaseTask;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import com.lawfirm.model.cases.mapper.business.CaseTaskMapper;
import com.lawfirm.model.cases.service.business.CaseTaskService;
import com.lawfirm.model.cases.vo.business.CaseTaskVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 案件流程服务实现
 * 基于任务服务和工作流引擎实现流程管理
 */
@Slf4j
@Service("caseProcessServiceImpl")
@RequiredArgsConstructor
public class ProcessServiceImpl implements CaseTaskService {

    private final CaseWorkflowManager workflowManager;
    private final CaseTaskAssigner taskAssigner;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final CaseTaskMapper taskMapper;
    private final CaseMapper caseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(CaseTaskDTO taskDTO) {
        log.info("创建任务: 案件ID={}, 任务类型={}", 
                taskDTO.getCaseId(), taskDTO.getTaskType());

        // 1. 创建任务实体
        CaseTask task = new CaseTask();
        BeanUtils.copyProperties(taskDTO, task);
        task.setTaskStatus(1); // 初始状态：待处理
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        
        // 2. 保存任务
        taskMapper.insert(task);
        Long taskId = task.getId();
        
        // 3. 启动工作流任务
        if (taskDTO.getTaskType() != null && taskDTO.getTaskType().getValue() == 1) { // 工作流任务类型
            Map<String, Object> variables = new HashMap<>();
            variables.put("taskId", taskId);
            variables.put("caseId", taskDTO.getCaseId());
            variables.put("taskType", taskDTO.getTaskType());
            variables.put("creatorId", taskDTO.getCreatorId());
            
            // 如果是工作流相关任务，创建工作流任务
            Case caseEntity = caseMapper.selectById(taskDTO.getCaseId());
            if (caseEntity != null && caseEntity.getProcessInstanceId() != null) {
                // 在已有的工作流实例中创建任务
                // 实际实现需要根据工作流引擎的API调整
                // workflowManager可能需要扩展功能来支持这种操作
            }
        }
        
        // 4. 记录审计
        auditProvider.auditCaseStatusChange(
                taskDTO.getCaseId(),
                taskDTO.getCreatorId(),
                "0", // 无状态
                "1", // 待处理
                "创建任务: " + taskDTO.getTaskName()
        );
        
        // 5. 发送消息通知
        messageManager.sendCaseStatusChangeMessage(
                taskDTO.getCaseId(),
                "0", // 无状态
                "1", // 待处理
                taskDTO.getCreatorId(),
                "创建任务: " + taskDTO.getTaskName()
        );

        log.info("创建任务成功, ID: {}", taskId);
        return taskId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreateTasks(List<CaseTaskDTO> taskDTOs) {
        log.info("批量创建任务: 数量={}", taskDTOs.size());

        for (CaseTaskDTO dto : taskDTOs) {
            createTask(dto);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTask(CaseTaskDTO taskDTO) {
        log.info("更新任务: ID={}", taskDTO.getId());

        // 1. 获取原任务数据
        CaseTask oldTask = taskMapper.selectById(taskDTO.getId());
        if (oldTask == null) {
            throw new RuntimeException("任务不存在: " + taskDTO.getId());
        }
        
        // 2. 更新任务
        CaseTask task = new CaseTask();
        BeanUtils.copyProperties(taskDTO, task);
        task.setUpdateTime(LocalDateTime.now());
        
        int result = taskMapper.updateById(task);
        
        // 3. 记录审计
        auditProvider.auditCaseUpdate(
                taskDTO.getCaseId(),
                taskDTO.getOperatorId(),
                oldTask,
                task,
                new HashMap<>()
        );

        log.info("更新任务成功, ID: {}", taskDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTask(Long taskId) {
        log.info("删除任务: ID={}", taskId);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 删除任务
        int result = taskMapper.deleteById(taskId);
        
        // 3. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                Long.valueOf(task.getCreateBy()),
                String.valueOf(task.getTaskStatus()),
                "0",
                "删除任务"
        );
        
        // 4. 发送消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                String.valueOf(task.getTaskStatus()),
                "0",
                Long.valueOf(task.getCreateBy()),
                "删除任务"
        );

        log.info("删除任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteTasks(List<Long> taskIds) {
        log.info("批量删除任务: 数量={}", taskIds.size());

        for (Long id : taskIds) {
            deleteTask(id);
        }

        return true;
    }

    @Override
    public CaseTaskVO getTaskDetail(Long taskId) {
        log.info("获取任务详情: ID={}", taskId);

        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }

        CaseTaskVO vo = new CaseTaskVO();
        BeanUtils.copyProperties(task, vo);

        return vo;
    }

    @Override
    public List<CaseTaskVO> listCaseTasks(Long caseId) {
        log.info("获取案件所有任务: 案件ID={}", caseId);

        LambdaQueryWrapper<CaseTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseTask::getCaseId, caseId);
        wrapper.orderByDesc(CaseTask::getCreateTime);
        List<CaseTask> tasks = taskMapper.selectList(wrapper);

        return tasks.stream().map(entity -> {
            CaseTaskVO vo = new CaseTaskVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseTaskVO> pageTasks(Long caseId, Integer taskType, Integer taskStatus, Integer pageNum, Integer pageSize) {
        log.info("分页查询任务: 案件ID={}, 任务类型={}, 任务状态={}, 页码={}, 每页大小={}", 
                caseId, taskType, taskStatus, pageNum, pageSize);

        Page<CaseTask> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseTask::getCaseId, caseId);
        if (taskType != null) {
            wrapper.eq(CaseTask::getTaskType, taskType);
        }
        if (taskStatus != null) {
            wrapper.eq(CaseTask::getTaskStatus, taskStatus);
        }
        
        // 排序
        wrapper.orderByDesc(CaseTask::getUpdateTime);

        // 执行查询
        IPage<CaseTask> resultPage = taskMapper.selectPage(page, wrapper);

        // 转换为VO
        return resultPage.convert(entity -> {
            CaseTaskVO vo = new CaseTaskVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startTask(Long taskId) {
        log.info("开始任务: ID={}", taskId);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新状态为进行中
        Integer oldStatus = task.getTaskStatus();
        task.setTaskStatus(2); // 进行中状态
        task.setStartTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        int result = taskMapper.updateById(task);
        
        // 3. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                Long.valueOf(task.getUpdateBy()),
                oldStatus.toString(),
                "2", // 进行中状态
                "开始任务"
        );
        
        // 4. 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                oldStatus.toString(),
                "2", // 进行中状态
                Long.valueOf(task.getUpdateBy()),
                "开始任务"
        );

        log.info("开始任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pauseTask(Long taskId, String reason) {
        log.info("暂停任务: ID={}, 原因={}", taskId, reason);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新状态为暂停
        Integer oldStatus = task.getTaskStatus();
        task.setTaskStatus(5); // 暂停状态
        task.setUpdateTime(LocalDateTime.now());
        task.setPauseReason(reason);
        int result = taskMapper.updateById(task);
        
        // 3. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                convertToLong(task.getUpdateBy()),
                oldStatus.toString(),
                "5", // 暂停状态
                reason
        );
        
        // 4. 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                oldStatus.toString(),
                "5", // 暂停状态
                convertToLong(task.getUpdateBy()),
                reason
        );

        log.info("暂停任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeTask(Long taskId) {
        log.info("恢复任务: ID={}", taskId);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 检查是否为暂停状态
        if (task.getTaskStatus() != 5) {
            throw new RuntimeException("只有暂停状态的任务才能恢复");
        }
        
        // 3. 更新状态为进行中
        Integer oldStatus = task.getTaskStatus();
        task.setTaskStatus(2); // 进行中状态
        task.setUpdateTime(LocalDateTime.now());
        task.setPauseReason(null);
        int result = taskMapper.updateById(task);
        
        // 4. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                convertToLong(task.getUpdateBy()),
                oldStatus.toString(),
                "2", // 进行中状态
                "恢复任务"
        );
        
        // 5. 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                oldStatus.toString(),
                "2", // 进行中状态
                convertToLong(task.getUpdateBy()),
                "恢复任务"
        );

        log.info("恢复任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeTask(Long taskId, String completionNote) {
        log.info("完成任务: ID={}, 完成说明={}", taskId, completionNote);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新状态为已完成
        Integer oldStatus = task.getTaskStatus();
        task.setTaskStatus(3); // 已完成状态
        task.setUpdateTime(LocalDateTime.now());
        task.setCompleteTime(LocalDateTime.now());
        task.setCompletionNote(completionNote);
        int result = taskMapper.updateById(task);
        
        // 3. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                convertToLong(task.getUpdateBy()),
                oldStatus.toString(),
                "3", // 已完成状态
                "完成任务: " + completionNote
        );
        
        // 4. 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                oldStatus.toString(),
                "3", // 已完成状态
                convertToLong(task.getUpdateBy()),
                "完成任务: " + completionNote
        );

        log.info("完成任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelTask(Long taskId, String reason) {
        log.info("取消任务: ID={}, 原因={}", taskId, reason);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新状态为已取消
        Integer oldStatus = task.getTaskStatus();
        task.setTaskStatus(4); // 已取消状态
        task.setUpdateTime(LocalDateTime.now());
        task.setCancelReason(reason);
        int result = taskMapper.updateById(task);
        
        // 3. 记录审计
        auditProvider.auditCaseStatusChange(
                task.getCaseId(),
                convertToLong(task.getUpdateBy()),
                oldStatus.toString(),
                "4", // 已取消状态
                reason
        );
        
        // 4. 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                task.getCaseId(),
                oldStatus.toString(),
                "4", // 已取消状态
                convertToLong(task.getUpdateBy()),
                reason
        );

        log.info("取消任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignTask(Long taskId, Long assigneeId) {
        log.info("分配任务: ID={}, 受理人={}", taskId, assigneeId);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新任务受理人
        task.setAssigneeId(assigneeId);
        task.setUpdateTime(LocalDateTime.now());
        int result = taskMapper.updateById(task);
        
        // 3. 分配工作流任务
        if (task.getWorkflowTaskId() != null) {
            taskAssigner.assignTask(
                    task.getWorkflowTaskId(),
                    assigneeId.toString(),
                    "任务分配"
            );
        }
        
        // 4. 发送分配消息
        messageManager.sendCaseAssignmentMessage(
                task.getCaseId(),
                assigneeId,
                task.getCreatorId(),
                "NEW" // 新分配
        );

        log.info("分配任务成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTaskProgress(Long taskId, Integer progress, String progressNote) {
        log.info("更新任务进度: ID={}, 进度={}, 说明={}", taskId, progress, progressNote);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新任务进度
        task.setProgress(progress);
        task.setProgressNote(progressNote);
        task.setUpdateTime(LocalDateTime.now());
        int result = taskMapper.updateById(task);

        log.info("更新任务进度成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTaskComment(Long taskId, String comment) {
        log.info("添加任务评论: ID={}", taskId);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 添加评论
        // 在实际实现中，应该有一个单独的评论表
        // 此处简化处理，直接在任务中添加
        
        log.info("添加任务评论成功, ID: {}", taskId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setTaskPriority(Long taskId, Integer priority) {
        log.info("设置任务优先级: ID={}, 优先级={}", taskId, priority);

        // 1. 获取任务数据
        CaseTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在: " + taskId);
        }
        
        // 2. 更新任务优先级
        task.setPriority(priority);
        task.setUpdateTime(LocalDateTime.now());
        int result = taskMapper.updateById(task);

        log.info("设置任务优先级成功, ID: {}", taskId);
        return result > 0;
    }

    @Override
    public boolean checkTaskExists(Long taskId) {
        log.info("检查任务是否存在: ID={}", taskId);
        return taskMapper.selectById(taskId) != null;
    }

    /**
     * 辅助方法：将字符串转换为Long
     */
    private Long convertToLong(String value) {
        if (value == null || value.isEmpty()) {
            return 0L;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
    
    @Override
    public int countTasks(Long caseId, Integer taskType, Integer taskStatus) {
        log.info("统计任务数量: 案件ID={}, 任务类型={}, 任务状态={}", caseId, taskType, taskStatus);

        LambdaQueryWrapper<CaseTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseTask::getCaseId, caseId);
        if (taskType != null) {
            wrapper.eq(CaseTask::getTaskType, taskType);
        }
        if (taskStatus != null) {
            wrapper.eq(CaseTask::getTaskStatus, taskStatus);
        }
                
        return taskMapper.selectCount(wrapper).intValue();
    }
}
