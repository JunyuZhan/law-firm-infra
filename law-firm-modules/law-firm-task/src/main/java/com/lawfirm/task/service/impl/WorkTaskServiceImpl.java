package com.lawfirm.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.model.task.dto.WorkTaskDTO;
import com.lawfirm.model.task.dto.WorkTaskTagDTO;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.entity.WorkTaskTag;
import com.lawfirm.model.task.entity.WorkTaskTagRelation;
import com.lawfirm.model.task.enums.WorkTaskStatusEnum;
import com.lawfirm.model.task.mapper.WorkTaskMapper;
import com.lawfirm.model.task.query.WorkTaskQuery;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.model.task.service.WorkTaskTagRelationService;
import com.lawfirm.model.task.service.WorkTaskTagService;
import com.lawfirm.model.task.vo.WorkTaskVO;
import com.lawfirm.task.converter.WorkTaskConverter;
import com.lawfirm.task.exception.TaskErrorCode;
import com.lawfirm.task.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * 工作任务服务实现类
 */
@Slf4j
@Service
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTaskMapper, WorkTask> implements WorkTaskService {

    @Autowired
    private WorkTaskTagService taskTagService;
    
    @Autowired
    private WorkTaskTagRelationServiceImpl taskTagRelationService;
    
    @Autowired
    private WorkTaskConverter taskConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(WorkTaskDTO dto) {
        // 1. 校验参数
        validateTaskParams(dto);
        
        // 2. 转换DTO为实体
        WorkTask task = taskConverter.dtoToEntity(dto);
        
        // 3. 设置默认值
        if (task.getStatus() == null) {
            task.setStatus(WorkTaskStatusEnum.TODO.getCode());
        }
        
        // 4. 保存任务
        save(task);
        
        // 5. 保存标签关联
        if (dto.getTags() != null) {
            List<Long> tagIds = dto.getTags().stream()
                .map(WorkTaskTagDTO::getId)
                .collect(Collectors.toList());
            saveTaskTags(task.getId(), tagIds);
        }
        
        return task.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTask(WorkTaskDTO dto) {
        // 1. 校验参数
        validateTaskParams(dto);
        
        // 2. 检查任务是否存在
        WorkTask existingTask = getById(dto.getId());
        if (existingTask == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 3. 转换DTO为实体
        WorkTask task = taskConverter.dtoToEntity(dto);
        
        // 4. 更新任务
        updateById(task);
        
        // 5. 更新标签关联
        if (dto.getTags() != null) {
            List<Long> tagIds = dto.getTags().stream()
                .map(WorkTaskTagDTO::getId)
                .collect(Collectors.toList());
            updateTaskTags(task.getId(), tagIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long taskId) {
        // 1. 检查任务是否存在
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 检查是否有子任务
        LambdaQueryWrapper<WorkTask> subTaskWrapper = new LambdaQueryWrapper<>();
        subTaskWrapper.eq(WorkTask::getParentId, taskId);
        long subTaskCount = count(subTaskWrapper);
        if (subTaskCount > 0) {
            throw new TaskException("无法删除含有子任务的任务");
        }
        
        // 3. 删除任务
        removeById(taskId);
        
        // 4. 删除标签关联
        LambdaQueryWrapper<WorkTaskTagRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkTaskTagRelation::getTaskId, taskId);
        taskTagRelationService.remove(queryWrapper);
    }

    @Override
    public WorkTaskDTO getTaskDetail(Long taskId) {
        // 1. 获取任务
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 获取任务标签
        List<Long> tagIds = taskTagRelationService.getTagIdsByTaskId(taskId);
        List<WorkTaskTag> tags = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tagIds)) {
            tags = taskTagService.listByIds(tagIds);
        }
        
        // 3. 转换为DTO
        WorkTaskDTO dto = taskConverter.entityToDto(task);
        if (!CollectionUtils.isEmpty(tags)) {
            dto.setTags(tags.stream()
                .map(taskConverter::tagToTagDto)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    @Override
    public List<WorkTaskDTO> queryTaskList(WorkTaskQuery query) {
        // 1. 构建查询条件
        LambdaQueryWrapper<WorkTask> queryWrapper = buildTaskQueryWrapper(query);
        
        // 2. 执行查询
        List<WorkTask> taskList;
        if (query.getPageSize() != null && query.getPageNum() != null) {
            Page<WorkTask> page = new Page<>(query.getPageNum(), query.getPageSize());
            IPage<WorkTask> pageResult = page(page, queryWrapper);
            taskList = pageResult.getRecords();
        } else {
            taskList = list(queryWrapper);
        }
        
        // 3. 转换为DTO
        if (CollectionUtils.isEmpty(taskList)) {
            return new ArrayList<>();
        }
        
        return taskList.stream()
            .map(taskConverter::entityToDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskStatus(Long taskId, Integer status) {
        // 1. 检查任务是否存在
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 检查状态是否有效
        if (WorkTaskStatusEnum.getByCode(status) == null) {
            throw new TaskException(TaskErrorCode.TASK_STATUS_ERROR);
        }
        
        // 3. 更新状态
        task.setStatus(status);
        
        // 4. 根据状态设置相关时间
        if (Objects.equals(status, WorkTaskStatusEnum.COMPLETED.getCode())) {
            task.setStatus(WorkTaskStatusEnum.COMPLETED.getCode());
        } else if (Objects.equals(status, WorkTaskStatusEnum.CANCELLED.getCode())) {
            task.setStatus(WorkTaskStatusEnum.CANCELLED.getCode());
        }
        
        updateById(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskPriority(Long taskId, Integer priority) {
        // 1. 检查任务是否存在
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 更新优先级
        task.setPriority(priority);
        updateById(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignTask(Long taskId, Long assigneeId) {
        // 1. 检查任务是否存在
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 分配任务
        task.setAssigneeId(assigneeId);
        updateById(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(Long taskId) {
        updateTaskStatus(taskId, WorkTaskStatusEnum.COMPLETED.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(Long taskId) {
        updateTaskStatus(taskId, WorkTaskStatusEnum.CANCELLED.getCode());
    }

    /**
     * 校验任务参数
     */
    private void validateTaskParams(WorkTaskDTO dto) {
        // 1. 校验标题
        if (!StringUtils.hasText(dto.getTitle())) {
            throw new TaskException("任务标题不能为空");
        }
        
        // 2. 校验时间
        if (dto.getStartTime() != null && dto.getEndTime() != null 
                && dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new TaskException(TaskErrorCode.TASK_TIME_CONFLICT);
        }
        
        // 3. 校验父任务
        if (dto.getParentId() != null) {
            WorkTask parentTask = getById(dto.getParentId());
            if (parentTask == null) {
                throw new TaskException(TaskErrorCode.TASK_PARENT_ERROR);
            }
        }
    }

    /**
     * 保存任务标签
     */
    private void saveTaskTags(Long taskId, List<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        
        taskTagRelationService.saveTaskTagRelations(taskId, tagIds);
    }

    /**
     * 更新任务标签
     */
    private void updateTaskTags(Long taskId, List<Long> tagIds) {
        // 1. 删除现有关联
        taskTagRelationService.deleteByTaskId(taskId);
        
        // 2. 保存新关联
        saveTaskTags(taskId, tagIds);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<WorkTask> buildTaskQueryWrapper(WorkTaskQuery query) {
        LambdaQueryWrapper<WorkTask> wrapper = new LambdaQueryWrapper<>();
        
        // 基本查询条件
        if (StringUtils.hasText(query.getTitle())) {
            wrapper.like(WorkTask::getTitle, query.getTitle());
        }
        
        if (query.getStatus() != null) {
            wrapper.eq(WorkTask::getStatus, query.getStatus());
        }
        
        if (query.getPriority() != null) {
            wrapper.eq(WorkTask::getPriority, query.getPriority());
        }
        
        if (query.getAssigneeId() != null) {
            wrapper.eq(WorkTask::getAssigneeId, query.getAssigneeId());
        }
        
        if (query.getCreatorId() != null) {
            wrapper.eq(WorkTask::getCreateBy, query.getCreatorId().toString());
        }
        
        // 时间范围查询条件
        if (query.getStartTimeBegin() != null) {
            wrapper.ge(WorkTask::getStartTime, query.getStartTimeBegin());
        }
        
        if (query.getStartTimeEnd() != null) {
            wrapper.le(WorkTask::getStartTime, query.getStartTimeEnd());
        }
        
        if (query.getEndTimeBegin() != null) {
            wrapper.ge(WorkTask::getEndTime, query.getEndTimeBegin());
        }
        
        if (query.getEndTimeEnd() != null) {
            wrapper.le(WorkTask::getEndTime, query.getEndTimeEnd());
        }
        
        // 父任务查询条件
        if (query.getParentId() != null) {
            wrapper.eq(WorkTask::getParentId, query.getParentId());
        } else if (Boolean.FALSE.equals(query.getIncludeSubTasks())) {
            wrapper.isNull(WorkTask::getParentId);
        }
        
        // 跨模块关联查询条件
        if (query.getCaseId() != null) {
            wrapper.eq(WorkTask::getCaseId, query.getCaseId());
        }
        
        if (query.getClientId() != null) {
            wrapper.eq(WorkTask::getClientId, query.getClientId());
        }
        
        if (query.getIsLegalTask() != null) {
            wrapper.eq(WorkTask::getIsLegalTask, query.getIsLegalTask());
        }
        
        if (query.getDepartmentId() != null) {
            wrapper.eq(WorkTask::getDepartmentId, query.getDepartmentId());
        }
        
        // 默认按创建时间倒序排序
        wrapper.orderByDesc(WorkTask::getCreateTime);
        
        return wrapper;
    }
    
    /**
     * 填充DTO中的关联信息
     */
    private void fillAssociatedInfo(WorkTaskDTO dto) {
        if (dto == null) {
            return;
        }
        
        // 填充任务负责人信息
        if (dto.getAssigneeId() != null) {
            // TODO: 调用人事模块接口获取员工信息
            // EmployeeDTO employee = employeeService.getEmployeeById(dto.getAssigneeId());
            // dto.setAssigneeName(employee.getName());
            // dto.setAssigneeAvatar(employee.getAvatar());
        }
        
        // 填充案例信息
        if (dto.getCaseId() != null) {
            // TODO: 调用案例模块接口获取案例信息
            // CaseDTO caseInfo = caseService.getCaseById(dto.getCaseId());
            // dto.setCaseName(caseInfo.getName());
        }
        
        // 填充客户信息
        if (dto.getClientId() != null) {
            // TODO: 调用客户模块接口获取客户信息
            // ClientDTO client = clientService.getClientById(dto.getClientId());
            // dto.setClientName(client.getName());
        }
        
        // 填充部门信息
        if (dto.getDepartmentId() != null) {
            // TODO: 调用人事模块接口获取部门信息
            // DepartmentDTO dept = departmentService.getDepartmentById(dto.getDepartmentId());
            // dto.setDepartmentName(dept.getName());
        }
        
        // 填充标签信息
        // TODO: 获取任务标签
        
        // 填充子任务信息
        // TODO: 获取子任务列表
    }
    
    /**
     * 获取当前用户ID
     */
    @Override
    public Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null) {
                return Long.valueOf(authentication.getName());
            }
        } catch (Exception e) {
            log.warn("获取当前用户ID失败", e);
        }
        return null;
    }
    
    /**
     * 获取当前用户名
     */
    @Override
    public String getCurrentUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                return authentication.getName();
            }
        } catch (Exception e) {
            log.warn("获取当前用户名失败", e);
        }
        return null;
    }
    
    /**
     * 获取当前租户ID
     */
    @Override
    public Long getCurrentTenantId() {
        // 从上下文或配置中获取租户ID
        // 这里暂时返回默认租户ID
        return 1L;
    }

    @Override
    public Map<String, Object> getTaskStatistics(WorkTaskQuery query) {
        // 1. 构建查询条件
        LambdaQueryWrapper<WorkTask> queryWrapper = buildTaskQueryWrapper(query);
        
        // 2. 查询所有符合条件的任务
        List<WorkTask> tasks = list(queryWrapper);
        
        // 3. 统计各状态的任务数量
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("total", tasks.size());
        statistics.put("todo", tasks.stream()
            .filter(task -> Objects.equals(task.getStatus(), WorkTaskStatusEnum.TODO.getCode()))
            .count());
        statistics.put("inProgress", tasks.stream()
            .filter(task -> Objects.equals(task.getStatus(), WorkTaskStatusEnum.IN_PROGRESS.getCode()))
            .count());
        statistics.put("completed", tasks.stream()
            .filter(task -> Objects.equals(task.getStatus(), WorkTaskStatusEnum.COMPLETED.getCode()))
            .count());
        statistics.put("cancelled", tasks.stream()
            .filter(task -> Objects.equals(task.getStatus(), WorkTaskStatusEnum.CANCELLED.getCode()))
            .count());
        
        // 4. 统计优先级分布
        Map<Integer, Long> priorityDistribution = tasks.stream()
            .collect(Collectors.groupingBy(WorkTask::getPriority, Collectors.counting()));
        statistics.put("priorityDistribution", priorityDistribution);
        
        // 5. 统计最近7天的任务创建数量
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Map<String, Long> dailyTaskCount = tasks.stream()
            .filter(task -> task.getCreateTime().isAfter(sevenDaysAgo))
            .collect(Collectors.groupingBy(
                task -> task.getCreateTime().toLocalDate().toString(),
                Collectors.counting()
            ));
        statistics.put("dailyTaskCount", dailyTaskCount);
        
        return statistics;
    }
} 