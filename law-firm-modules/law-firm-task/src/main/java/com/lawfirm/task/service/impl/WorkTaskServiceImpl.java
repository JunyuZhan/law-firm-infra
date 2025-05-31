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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.cases.dto.base.CaseBaseDTO;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.organization.dto.department.DepartmentDTO;
import org.springframework.beans.factory.annotation.Value;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.task.service.TaskMessageService;

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
@Service("workTaskService")
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTaskMapper, WorkTask> implements WorkTaskService {

    @Autowired
    private WorkTaskTagService taskTagService;
    
    @Autowired
    private WorkTaskTagRelationServiceImpl taskTagRelationService;
    
    @Autowired
    private WorkTaskConverter taskConverter;

    @Autowired
    @Qualifier("commonRestTemplate")
    private RestTemplate restTemplate;

    /**
     * 任务消息服务（新的统一消息服务）
     */
    @Autowired
    private TaskMessageService taskMessageService;

    /**
     * 通知服务（如无外部实现，这里简单定义内部类模拟）
     * @deprecated 使用TaskMessageService替代
     */
    @Autowired(required = false)
    private NotificationService notificationService;

    /**
     * @deprecated 使用TaskMessageService替代
     */
    @Autowired(required = false)
    @Qualifier("taskMessageSender")
    private MessageSender messageSender;

    /**
     * 通知服务接口
     */
    public interface NotificationService {
        void send(String type, Long userId, String title, String content, Map<String, Object> variables);
        void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables);
    }

    /**
     * 默认通知服务实现（仅日志模拟）
     */
    @Service("defaultTaskNotificationService")
    public static class DefaultNotificationService implements NotificationService {
        @Override
        public void send(String type, Long userId, String title, String content, Map<String, Object> variables) {
            log.info("[通知][{}] userId={}, title={}, content={}, vars={}", type, userId, title, content, variables);
        }
        @Override
        public void sendBatch(String type, List<Long> userIds, String title, String content, Map<String, Object> variables) {
            log.info("[批量通知][{}] userIds={}, title={}, content={}, vars={}", type, userIds, title, content, variables);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(WorkTaskDTO dto) {
        // 1. 校验参数
        validateTaskParams(dto);
        
        // 2. 转换DTO为实体
        WorkTask task = taskConverter.dtoToEntity(dto);
        
        // 3. 设置默认值
        if (task.getStatus() == null) {
            task.setStatus(WorkTaskStatusEnum.TO_DO.getCode());
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
        
        // 6. 发送任务创建通知
        sendTaskCreatedNotification(task.getId(), task.getTitle(), task.getAssigneeId(), null);
        
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
        WorkTaskStatusEnum statusEnum = WorkTaskStatusEnum.getByCode(status);
        if (statusEnum == null) {
            throw new TaskException(TaskErrorCode.TASK_STATUS_ERROR);
        }
        
        // 2.1 避免重复更新相同状态
        if (Objects.equals(task.getStatus(), status)) {
            log.info("任务状态未变化，跳过更新，taskId={}, status={}", taskId, status);
            return;
        }
        
        // 3. 更新状态
        LocalDateTime now = LocalDateTime.now();
        task.setStatus(status);
        
        // 4. 根据状态设置相关时间
        try {
            switch (statusEnum) {
                case COMPLETED:
                    // 记录任务的完成时间
                    task.setEndTime(now);
                    break;
                case CANCELLED:
                    // 记录任务的取消时间（如果有需要，可以添加cancelTime字段）
                    break;
                case IN_PROGRESS:
                    // 如果任务开始时间未设置，设置为当前时间
                    if (task.getStartTime() == null) {
                        task.setStartTime(now);
                    }
                    break;
                default:
                    // 其他状态无需特殊处理
                    break;
            }
            
            // 5. 保存更新
            updateById(task);
            
            // 6. 记录状态变更日志
            log.info("任务状态已更新，taskId={}, oldStatus={}, newStatus={}", 
                    taskId, task.getStatus(), status);
                    
        } catch (Exception e) {
            log.error("更新任务状态失败，taskId={}，status={}, error={}", taskId, status, e.getMessage());
            throw new TaskException("更新任务状态失败: " + e.getMessage(), e);
        }
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
        Long oldAssigneeId = task.getAssigneeId();
        task.setAssigneeId(assigneeId);
        updateById(task);
        
        // 3. 发送任务分配通知
        sendTaskAssignedNotification(taskId, task.getTitle(), assigneeId, oldAssigneeId, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(Long taskId) {
        updateTaskStatus(taskId, WorkTaskStatusEnum.COMPLETED.getCode());
        
        // 发送任务完成通知
        WorkTask task = getById(taskId);
        sendTaskCompletedNotification(taskId, task.getTitle(), task.getAssigneeId(), null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(Long taskId) {
        updateTaskStatus(taskId, WorkTaskStatusEnum.CANCELLED.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(Long taskId, String reason) {
        // 1. 检查任务是否存在
        WorkTask task = getById(taskId);
        if (task == null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        
        // 2. 设置取消原因
        if (StringUtils.hasText(reason)) {
            task.setCancelReason(reason);
        }
        
        // 3. 更新状态
        task.setStatus(WorkTaskStatusEnum.CANCELLED.getCode());
        
        // 4. 保存更新
        updateById(task);
        
        log.info("已取消任务: taskId={}, reason={}", taskId, reason);
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
            try {
                String url = "http://law-firm-personnel/personnel/employee/" + dto.getAssigneeId();
                EmployeeDTO employee = restTemplate.getForObject(url, EmployeeDTO.class);
                if (employee != null) {
                    dto.setAssigneeName(employee.getName());
                    dto.setAssigneeAvatar(employee.getPhotoUrl());
                }
            } catch (Exception e) {
                log.warn("获取员工信息失败", e);
            }
        }
        // 填充案例信息
        if (dto.getCaseId() != null) {
            try {
                String url = "http://law-firm-case/case/" + dto.getCaseId();
                CaseBaseDTO caseInfo = restTemplate.getForObject(url, CaseBaseDTO.class);
                if (caseInfo != null) {
                    dto.setCaseName(caseInfo.getCaseName());
                }
            } catch (Exception e) {
                log.warn("获取案例信息失败", e);
            }
        }
        // 填充客户信息
        if (dto.getClientId() != null) {
            try {
                String url = "http://law-firm-client/client/" + dto.getClientId();
                ClientDTO client = restTemplate.getForObject(url, ClientDTO.class);
                if (client != null) {
                    dto.setClientName(client.getClientName());
                }
            } catch (Exception e) {
                log.warn("获取客户信息失败", e);
            }
        }
        // 填充部门信息
        if (dto.getDepartmentId() != null) {
            try {
                String url = "http://law-firm-personnel/personnel/department/" + dto.getDepartmentId();
                DepartmentDTO dept = restTemplate.getForObject(url, DepartmentDTO.class);
                if (dept != null) {
                    dto.setDepartmentName(dept.getName());
                }
            } catch (Exception e) {
                log.warn("获取部门信息失败", e);
            }
        }
        // 填充标签信息
        try {
            dto.setTags(taskTagService.getTagsByTaskId(dto.getId()));
        } catch (Exception e) {
            log.warn("获取任务标签失败", e);
        }
        // 填充子任务信息
        try {
            WorkTaskQuery subTaskQuery = new WorkTaskQuery();
            subTaskQuery.setParentId(dto.getId());
            dto.setSubTasks(this.queryTaskList(subTaskQuery));
        } catch (Exception e) {
            log.warn("获取子任务列表失败", e);
        }
    }
    
    /**
     * 获取当前用户ID
     */
    @Override
    public Long getCurrentUserId() {
        // 优先使用SecurityUtils，兼容多端
        try {
            Long userId = com.lawfirm.common.security.utils.SecurityUtils.getUserId();
            if (userId != null) {
                return userId;
            }
        } catch (Exception ignore) {}
        // 兜底Spring Security
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null) {
                return Long.valueOf(authentication.getName());
            }
        } catch (Exception e) {
            log.warn("获取当前用户ID失败", e);
        }
        // 兜底返回-1
        return -1L;
    }
    
    /**
     * 获取当前用户名
     */
    @Override
    public String getCurrentUsername() {
        // 优先使用SecurityUtils
        try {
            String username = com.lawfirm.common.security.utils.SecurityUtils.getUsername();
            if (username != null) {
                return username;
            }
        } catch (Exception ignore) {}
        // 兜底Spring Security
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                return authentication.getName();
            }
        } catch (Exception e) {
            log.warn("获取当前用户名失败", e);
        }
        return "anonymous";
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
            .filter(task -> Objects.equals(task.getStatus(), WorkTaskStatusEnum.TO_DO.getCode()))
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

    // =============== 任务分配 ===============

    /**
     * 根据角色获取候选用户
     */
    public List<Long> getCandidateUsersByRole(Long roleId) {
        // 假设调用人事模块接口获取角色下所有在岗用户
        try {
            String url = "http://law-firm-personnel/personnel/employee/role/" + roleId;
            EmployeeDTO[] employees = restTemplate.getForObject(url, EmployeeDTO[].class);
            List<Long> userIds = new ArrayList<>();
            if (employees != null) {
                for (EmployeeDTO emp : employees) {
                    if (emp.getStatus() == null || emp.getStatus() == 1) { // 1=在岗
                        userIds.add(emp.getId());
                    }
                }
            }
            return userIds;
        } catch (Exception e) {
            log.warn("获取角色候选用户失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 根据部门获取候选用户
     */
    public List<Long> getCandidateUsersByDept(Long deptId) {
        // 假设调用组织模块接口获取部门下所有在岗用户
        try {
            String url = "http://law-firm-personnel/personnel/employee/department/" + deptId;
            EmployeeDTO[] employees = restTemplate.getForObject(url, EmployeeDTO[].class);
            List<Long> userIds = new ArrayList<>();
            if (employees != null) {
                for (EmployeeDTO emp : employees) {
                    if (emp.getStatus() == null || emp.getStatus() == 1) {
                        userIds.add(emp.getId());
                    }
                }
            }
            return userIds;
        } catch (Exception e) {
            log.warn("获取部门候选用户失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 自动分配任务给最合适的处理人（优先分配任务数最少、负载分最低的用户）
     */
    public Long autoAssignTask(String taskId, List<Long> candidateUserIds) {
        if (candidateUserIds == null || candidateUserIds.isEmpty()) {
            return null;
        }
        Long bestUser = null;
        double minScore = Double.MAX_VALUE;
        for (Long userId : candidateUserIds) {
            double score = calculateUserTaskLoadScore(userId);
            if (score < minScore) {
                minScore = score;
                bestUser = userId;
            }
        }
        return bestUser;
    }

    /**
     * 轮询分配任务（简单实现：按用户ID排序后取下一个）
     */
    private static final Map<String, Integer> roundRobinIndexMap = new HashMap<>();
    public Long roundRobinAssignTask(String taskId, List<Long> candidateUserIds) {
        if (candidateUserIds == null || candidateUserIds.isEmpty()) {
            return null;
        }
        candidateUserIds.sort(Long::compareTo);
        int idx = roundRobinIndexMap.getOrDefault(taskId, -1);
        idx = (idx + 1) % candidateUserIds.size();
        roundRobinIndexMap.put(taskId, idx);
        return candidateUserIds.get(idx);
    }

    /**
     * 负载均衡分配任务（分配给当前任务数最少的用户）
     */
    public Long loadBalanceAssignTask(String taskId, List<Long> candidateUserIds) {
        if (candidateUserIds == null || candidateUserIds.isEmpty()) {
            return null;
        }
        Long bestUser = null;
        int minCount = Integer.MAX_VALUE;
        for (Long userId : candidateUserIds) {
            int count = getUserTaskCount(userId);
            if (count < minCount) {
                minCount = count;
                bestUser = userId;
            }
        }
        return bestUser;
    }

    /**
     * 获取用户当前任务数量（未完成任务数）
     */
    public int getUserTaskCount(Long userId) {
        LambdaQueryWrapper<WorkTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTask::getAssigneeId, userId)
                .in(WorkTask::getStatus, WorkTaskStatusEnum.TO_DO.getCode(), WorkTaskStatusEnum.IN_PROGRESS.getCode());
        return (int) count(wrapper);
    }

    /**
     * 计算用户的任务负载分数（任务数+优先级加权+工时加权）
     */
    public double calculateUserTaskLoadScore(Long userId) {
        LambdaQueryWrapper<WorkTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTask::getAssigneeId, userId)
                .in(WorkTask::getStatus, WorkTaskStatusEnum.TO_DO.getCode(), WorkTaskStatusEnum.IN_PROGRESS.getCode());
        List<WorkTask> tasks = list(wrapper);
        if (tasks.isEmpty()) return 0.0;
        double score = 0.0;
        for (WorkTask t : tasks) {
            int priority = t.getPriority() == null ? 1 : t.getPriority();
            // WorkTask没有estimatedHours字段，负载分数仅用优先级和任务数
            score += priority * 2;
        }
        return score;
    }

    // =============== 任务通知 ===============

    /**
     * 发送任务创建通知
     */
    public void sendTaskCreatedNotification(Long taskId, String taskName, Long assigneeId, Map<String, Object> variables) {
        taskMessageService.sendTaskCreatedNotification(taskId, taskName, assigneeId, variables);
    }

    /**
     * 发送任务分配通知
     */
    public void sendTaskAssignedNotification(Long taskId, String taskName, Long assigneeId, Long oldAssigneeId, Map<String, Object> variables) {
        taskMessageService.sendTaskAssignedNotification(taskId, taskName, assigneeId, oldAssigneeId, variables);
    }

    /**
     * 发送任务完成通知
     */
    public void sendTaskCompletedNotification(Long taskId, String taskName, Long assigneeId, Map<String, Object> variables) {
        taskMessageService.sendTaskCompletedNotification(taskId, taskName, assigneeId, variables);
    }

    /**
     * 发送任务截止提醒
     */
    public void sendTaskDueReminder(Long taskId, String taskName, Long assigneeId, LocalDateTime dueDate) {
        taskMessageService.sendTaskDueReminder(taskId, taskName, assigneeId, dueDate);
    }

    /**
     * 发送任务超时通知
     */
    public void sendTaskOverdueNotification(Long taskId, String taskName, Long assigneeId, LocalDateTime dueDate) {
        taskMessageService.sendTaskOverdueNotification(taskId, taskName, assigneeId, dueDate);
    }

    /**
     * 发送批量任务通知
     */
    public void sendBatchTaskNotification(List<Long> recipientIds, String subject, String content, Map<String, Object> variables) {
        taskMessageService.sendBatchTaskNotification(recipientIds, subject, content, variables);
    }
} 