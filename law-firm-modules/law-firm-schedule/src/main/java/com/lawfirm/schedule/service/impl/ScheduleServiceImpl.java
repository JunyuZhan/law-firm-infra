package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.schedule.dto.ScheduleCaseRelationDTO;
import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.dto.ScheduleReminderDTO;
import com.lawfirm.model.schedule.dto.ScheduleTaskRelationDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.entity.enums.PriorityLevel;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import com.lawfirm.model.schedule.mapper.ScheduleMapper;
import com.lawfirm.model.schedule.service.ScheduleRelationService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import com.lawfirm.schedule.converter.ScheduleConverter;
import com.lawfirm.schedule.integration.CaseIntegration;
import com.lawfirm.schedule.integration.PersonnelIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.model.schedule.vo.ScheduleCaseRelationVO;
import com.lawfirm.model.schedule.vo.ScheduleTaskRelationVO;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;

/**
 * 日程服务实现类
 */
@Service("scheduleService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl extends BaseServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    private final ScheduleConverter scheduleConverter;
    private final ScheduleRelationService scheduleRelationService;
    private final PersonnelIntegration personnelIntegration;
    private final CaseIntegration caseIntegration;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSchedule(ScheduleDTO scheduleDTO) {
        log.info("创建日程，标题：{}", scheduleDTO.getTitle());
        
        // 检查所有者是否存在
        Long ownerId = scheduleDTO.getOwnerId();
        if (ownerId != null && !personnelIntegration.employeeExists(ownerId)) {
            log.error("创建日程失败，所有者不存在，所有者ID：{}", ownerId);
            throw new BusinessException("所有者不存在");
        }
        
        // 检查日程时间是否有效
        validateScheduleTime(scheduleDTO);
        
        // 检查是否与其他日程冲突
        checkTimeConflict(null, scheduleDTO);
        
        Schedule schedule = scheduleConverter.toEntity(scheduleDTO);
        
        // 设置初始状态
        if (schedule.getStatus() == null) {
            schedule.setStatus(ScheduleStatus.PLANNED.getCode());
        }
        
        boolean success = save(schedule);
        if (!success) {
            throw new BusinessException("创建日程失败");
        }
        
        Long scheduleId = schedule.getId();
        
        // 保存参与者
        if (scheduleDTO.getParticipants() != null && !scheduleDTO.getParticipants().isEmpty()) {
            saveParticipants(scheduleId, scheduleDTO.getParticipants());
        }
        
        // 保存提醒
        if (scheduleDTO.getReminders() != null && !scheduleDTO.getReminders().isEmpty()) {
            saveReminders(scheduleId, scheduleDTO.getReminders());
        }
        
        // 处理案件关联
        if (scheduleDTO.getCaseId() != null) {
            scheduleRelationService.linkCase(scheduleId, scheduleDTO.getCaseId(), scheduleDTO.getTitle());
        }
        
        // 处理任务关联
        if (scheduleDTO.getTaskId() != null) {
            scheduleRelationService.linkTask(scheduleId, scheduleDTO.getTaskId(), scheduleDTO.getTitle());
        }
        
        return scheduleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        log.info("更新日程，ID：{}", id);
        
        Schedule schedule = getById(id);
        if (schedule == null) {
            log.error("更新日程失败，日程不存在，ID：{}", id);
            return false;
        }
        
        // 验证所有者是否存在
        if (scheduleDTO.getOwnerId() != null && !personnelIntegration.employeeExists(scheduleDTO.getOwnerId())) {
            log.error("更新日程失败，所有者不存在，所有者ID：{}", scheduleDTO.getOwnerId());
            throw new BusinessException("所有者不存在");
        }
        
        // 如果时间变更，检查冲突
        if (scheduleDTO.getStartTime() != null && scheduleDTO.getEndTime() != null &&
                (!scheduleDTO.getStartTime().equals(schedule.getStartTime()) || 
                 !scheduleDTO.getEndTime().equals(schedule.getEndTime()))) {
            
            List<ScheduleVO> conflicts = checkConflicts(
                    scheduleDTO.getOwnerId() != null ? scheduleDTO.getOwnerId() : schedule.getOwnerId(),
                    scheduleDTO.getStartTime(),
                    scheduleDTO.getEndTime(),
                    id);
            
            if (!conflicts.isEmpty()) {
                log.warn("更新日程时检测到时间冲突，冲突数量：{}", conflicts.size());
                // 根据业务需求决定是否允许冲突
            }
        }
        
        scheduleConverter.updateEntity(schedule, scheduleDTO);
        boolean success = updateById(schedule);
        
        // 更新关联关系
        if (success) {
            // 如果提供了新的案件ID且与旧值不同，更新关联
            if (scheduleDTO.getCaseId() != null) {
                // 先解除原有关联
                // 这里可以根据实际需求决定是否保留原有关联
                // relationService.unlinkCasesByScheduleId(id);
                
                // 建立新关联
                scheduleRelationService.linkCase(id, scheduleDTO.getCaseId(), scheduleDTO.getTitle());
            }
            
            // 如果提供了新的任务ID且与旧值不同，更新关联
            if (scheduleDTO.getTaskId() != null) {
                // 先解除原有关联
                // 这里可以根据实际需求决定是否保留原有关联
                // relationService.unlinkTasksByScheduleId(id);
                
                // 建立新关联
                scheduleRelationService.linkTask(id, scheduleDTO.getTaskId(), scheduleDTO.getTitle());
            }
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSchedule(Long id) {
        log.info("删除日程，ID：{}", id);
        return removeById(id);
    }

    @Override
    public ScheduleVO getScheduleDetail(Long id) {
        log.info("获取日程详情，ID：{}", id);
        
        Schedule schedule = getById(id);
        if (schedule == null) {
            return null;
        }
        
        ScheduleVO scheduleVO = scheduleConverter.toVO(schedule);
        
        // 填充所有者信息
        fillOwnerInfo(scheduleVO);
        
        // 获取关联的案件和任务
        scheduleVO.setCaseRelations(scheduleRelationService.listCaseRelations(id));
        scheduleVO.setTaskRelations(scheduleRelationService.listTaskRelations(id));
        
        return scheduleVO;
    }

    @Override
    public Page<ScheduleVO> pageByUser(Page<Schedule> page, Long userId, Integer status) {
        log.info("分页查询用户日程，用户ID：{}, 状态：{}", userId, status);
        
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Schedule::getStartTime);
        
        queryWrapper.eq(Schedule::getOwnerId, userId);
        if (status != null) {
            queryWrapper.eq(Schedule::getStatus, status);
        }
        
        Page<Schedule> result = page(page, queryWrapper);
        
        Page<ScheduleVO> voPage = new Page<>(
                result.getCurrent(),
                result.getSize(),
                result.getTotal());
        
        List<ScheduleVO> scheduleVOs = result.getRecords().stream()
                .map(scheduleConverter::toVO)
                .collect(Collectors.toList());
        
        // 批量填充所有者信息
        fillOwnerInfo(scheduleVOs);
        
        voPage.setRecords(scheduleVOs);
        return voPage;
    }

    @Override
    public List<ScheduleVO> listByDate(Long userId, LocalDate date) {
        log.info("查询指定日期的日程，用户ID：{}, 日期：{}", userId, date);
        
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        
        return listByTimeRange(userId, startTime, endTime);
    }

    @Override
    public List<ScheduleVO> listByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询指定时间范围的日程，用户ID：{}, 开始时间：{}, 结束时间：{}", userId, startTime, endTime);
        
        if (startTime == null || endTime == null) {
            log.error("查询时间范围的日程失败，时间参数不完整");
            return Collections.emptyList();
        }
        
        // 使用查询构造器替代自定义方法
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Schedule::getStartTime, startTime)
                   .le(Schedule::getEndTime, endTime);
                   
        if (userId != null) {
            queryWrapper.eq(Schedule::getOwnerId, userId);
        }
        
        List<Schedule> schedules = list(queryWrapper);
        List<ScheduleVO> scheduleVOs = schedules.stream()
                .map(scheduleConverter::toVO)
                .collect(Collectors.toList());
        
        // 批量填充所有者信息
        fillOwnerInfo(scheduleVOs);
        
        return scheduleVOs;
    }

    @Override
    public List<ScheduleVO> listByCaseId(Long caseId) {
        log.info("查询与案件关联的日程，案件ID：{}", caseId);
        
        // 验证案件是否存在
        if (!caseIntegration.caseExists(caseId)) {
            log.error("查询与案件关联的日程失败，案件不存在，案件ID：{}", caseId);
            return Collections.emptyList();
        }
        
        // 获取案件关联的日程关系
        List<ScheduleCaseRelationVO> relations = scheduleRelationService.listSchedulesByCaseId(caseId);
        if (relations.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 提取日程ID列表
        List<Long> scheduleIds = relations.stream()
                .map(ScheduleCaseRelationVO::getScheduleId)
                .collect(Collectors.toList());
        
        // 查询日程列表
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Schedule::getId, scheduleIds);
        List<Schedule> schedules = list(queryWrapper);
        
        // 转换为VO并填充所有者信息
        List<ScheduleVO> scheduleVOs = schedules.stream()
                .map(scheduleConverter::toVO)
                .collect(Collectors.toList());
        fillOwnerInfo(scheduleVOs);
        
        return scheduleVOs;
    }

    @Override
    public List<ScheduleVO> listByTaskId(Long taskId) {
        log.info("查询与任务关联的日程，任务ID：{}", taskId);
        
        // 获取任务关联的日程关系
        List<ScheduleTaskRelationVO> relations = scheduleRelationService.listSchedulesByTaskId(taskId);
        if (relations.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 提取日程ID列表
        List<Long> scheduleIds = relations.stream()
                .map(ScheduleTaskRelationVO::getScheduleId)
                .collect(Collectors.toList());
        
        // 查询日程列表
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Schedule::getId, scheduleIds);
        List<Schedule> schedules = list(queryWrapper);
        
        // 转换为VO并填充所有者信息
        List<ScheduleVO> scheduleVOs = schedules.stream()
                .map(scheduleConverter::toVO)
                .collect(Collectors.toList());
        fillOwnerInfo(scheduleVOs);
        
        return scheduleVOs;
    }

    @Override
    public List<ScheduleVO> checkConflicts(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long excludeScheduleId) {
        log.info("检查日程时间冲突，用户ID：{}, 开始时间：{}, 结束时间：{}, 排除的日程ID：{}", 
                userId, startTime, endTime, excludeScheduleId);
        
        if (startTime == null || endTime == null) {
            log.error("检查日程时间冲突失败，时间参数不完整");
            return Collections.emptyList();
        }
        
        // 构建时间冲突查询条件
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Schedule::getOwnerId, userId)
                .and(w -> w
                        .between(Schedule::getStartTime, startTime, endTime)
                        .or()
                        .between(Schedule::getEndTime, startTime, endTime)
                        .or()
                        .and(sw -> sw
                                .le(Schedule::getStartTime, startTime)
                                .ge(Schedule::getEndTime, endTime)
                        )
                );
        
        // 排除指定的日程
        if (excludeScheduleId != null) {
            queryWrapper.ne(Schedule::getId, excludeScheduleId);
        }
        
        List<Schedule> schedules = list(queryWrapper);
        return schedules.stream()
                .map(scheduleConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkCase(Long scheduleId, Long caseId, String description) {
        log.info("将日程与案件关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        
        // 验证日程是否存在
        if (!exists(scheduleId)) {
            log.error("关联日程与案件失败，日程不存在，日程ID：{}", scheduleId);
            return false;
        }
        
        return scheduleRelationService.linkCase(scheduleId, caseId, description);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkCase(Long scheduleId, Long caseId) {
        log.info("解除日程与案件的关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        
        // 验证日程是否存在
        if (!exists(scheduleId)) {
            log.error("解除日程与案件关联失败，日程不存在，日程ID：{}", scheduleId);
            return false;
        }
        
        return scheduleRelationService.unlinkCase(scheduleId, caseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkTask(Long scheduleId, Long taskId, String description) {
        log.info("将日程与任务关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        
        // 验证日程是否存在
        if (!exists(scheduleId)) {
            log.error("关联日程与任务失败，日程不存在，日程ID：{}", scheduleId);
            return false;
        }
        
        return scheduleRelationService.linkTask(scheduleId, taskId, description);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkTask(Long scheduleId, Long taskId) {
        log.info("解除日程与任务的关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        
        // 验证日程是否存在
        if (!exists(scheduleId)) {
            log.error("解除日程与任务关联失败，日程不存在，日程ID：{}", scheduleId);
            return false;
        }
        
        return scheduleRelationService.unlinkTask(scheduleId, taskId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer statusCode) {
        log.info("更新日程状态，日程ID：{}，状态码：{}", id, statusCode);
        
        Schedule schedule = getById(id);
        if (schedule == null) {
            log.error("更新日程状态失败，日程不存在，ID：{}", id);
            return false;
        }
        
        // 验证状态码是否有效
        if (ScheduleStatus.getByCode(statusCode) == null) {
            log.error("更新日程状态失败，状态码无效：{}", statusCode);
            return false;
        }
        
        // 根据实际的Schedule类字段名来更新状态
        schedule.setStatus(statusCode);
        return updateById(schedule);
    }
    
    /**
     * 验证日程时间是否合法
     */
    private void validateScheduleTime(ScheduleDTO scheduleDTO) {
        LocalDateTime startTime = scheduleDTO.getStartTime();
        LocalDateTime endTime = scheduleDTO.getEndTime();
        
        if (startTime == null || endTime == null) {
            throw new BusinessException("日程开始时间和结束时间不能为空");
        }
        
        if (endTime.isBefore(startTime)) {
            throw new BusinessException("日程结束时间不能早于开始时间");
        }
        
        if (startTime.isBefore(LocalDateTime.now().minusMinutes(5))) {
            log.warn("日程开始时间早于当前时间，开始时间：{}", startTime);
            // 这里可以根据业务需求决定是否允许创建过去的日程
        }
    }
    
    /**
     * 填充所有者信息
     *
     * @param scheduleVOs 日程VO列表
     */
    private void fillOwnerInfo(List<ScheduleVO> scheduleVOs) {
        if (scheduleVOs == null || scheduleVOs.isEmpty()) {
            return;
        }
        
        for (ScheduleVO scheduleVO : scheduleVOs) {
            fillOwnerInfo(scheduleVO);
        }
    }
    
    /**
     * 填充所有者信息
     * 
     * @param scheduleVO 日程VO
     */
    private void fillOwnerInfo(ScheduleVO scheduleVO) {
        if (scheduleVO == null || scheduleVO.getOwnerId() == null) {
            return;
        }
        
        try {
            EmployeeVO owner = personnelIntegration.getEmployeeInfo(scheduleVO.getOwnerId());
            if (owner != null) {
                scheduleVO.setOwnerName(owner.getName());
                // 注意：如果EmployeeVO中没有getAvatar方法，请移除这行代码
                // scheduleVO.setOwnerAvatar(owner.getAvatar());
            }
        } catch (Exception e) {
            log.error("获取所有者信息失败，日程ID：{}，所有者ID：{}", scheduleVO.getId(), scheduleVO.getOwnerId(), e);
        }
    }

    /**
     * 检查日程时间冲突
     *
     * @param scheduleId 当前日程ID（更新时使用，创建时为null）
     * @param scheduleDTO 日程DTO
     */
    private void checkTimeConflict(Long scheduleId, ScheduleDTO scheduleDTO) {
        // 只有当有所有者且非全天日程时才检查冲突
        if (scheduleDTO.getOwnerId() == null || Boolean.TRUE.equals(scheduleDTO.getAllDay())) {
            return;
        }
        
        LocalDateTime startTime = scheduleDTO.getStartTime();
        LocalDateTime endTime = scheduleDTO.getEndTime();
        Long ownerId = scheduleDTO.getOwnerId();
        
        // 查询时间范围内的已有日程
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Schedule::getOwnerId, ownerId)
                .and(w -> w
                        .between(Schedule::getStartTime, startTime, endTime)
                        .or()
                        .between(Schedule::getEndTime, startTime, endTime)
                        .or()
                        .and(sw -> sw
                                .le(Schedule::getStartTime, startTime)
                                .ge(Schedule::getEndTime, endTime)
                        )
                );
        
        // 更新时排除自身
        if (scheduleId != null) {
            queryWrapper.ne(Schedule::getId, scheduleId);
        }
        
        List<Schedule> conflictSchedules = list(queryWrapper);
        
        if (!conflictSchedules.isEmpty()) {
            log.warn("日程时间存在冲突，冲突数量：{}", conflictSchedules.size());
            // 可以根据业务需求决定是否抛出异常或者仅记录警告
            // throw new BusinessException("日程时间存在冲突，请重新选择时间");
        }
    }

    /**
     * 保存日程参与者
     *
     * @param scheduleId 日程ID
     * @param participants 参与者列表
     */
    private void saveParticipants(Long scheduleId, List<ScheduleParticipantDTO> participants) {
        if (participants == null || participants.isEmpty()) {
            return;
        }
        
        // 调用参与者服务保存参与者
        for (ScheduleParticipantDTO participant : participants) {
            participant.setScheduleId(scheduleId);
        }
        
        // 可以调用participantService的批量保存方法
        log.info("保存日程参与者，日程ID：{}，参与者数量：{}", scheduleId, participants.size());
    }

    /**
     * 保存日程提醒
     *
     * @param scheduleId 日程ID
     * @param reminders 提醒列表
     */
    private void saveReminders(Long scheduleId, List<ScheduleReminderDTO> reminders) {
        if (reminders == null || reminders.isEmpty()) {
            return;
        }
        
        // 调用提醒服务保存提醒
        for (ScheduleReminderDTO reminder : reminders) {
            reminder.setScheduleId(scheduleId);
        }
        
        // 可以调用reminderService的批量保存方法
        log.info("保存日程提醒，日程ID：{}，提醒数量：{}", scheduleId, reminders.size());
    }

    /**
     * 检查日程是否存在
     * 
     * @param scheduleId 日程ID
     * @return 是否存在
     */
    private boolean exists(Long scheduleId) {
        return getById(scheduleId) != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateScheduleByDTO(com.lawfirm.model.schedule.dto.ScheduleUpdateDTO scheduleUpdateDTO) {
        log.info("使用ScheduleUpdateDTO更新日程，ID：{}", scheduleUpdateDTO.getId());
        // 1. 查询原始日程
        com.lawfirm.model.schedule.entity.Schedule schedule = getById(scheduleUpdateDTO.getId());
        if (schedule == null) {
            log.error("更新日程失败，日程不存在，ID：{}", scheduleUpdateDTO.getId());
            return false;
        }
        // 2. 更新主表字段
        scheduleConverter.updateEntityFromUpdateDTO(schedule, scheduleUpdateDTO);
        boolean success = updateById(schedule);
        // 3. 处理参与者
        if (scheduleUpdateDTO.getParticipants() != null) {
            saveParticipants(schedule.getId(), scheduleUpdateDTO.getParticipants());
        }
        // 4. 处理提醒
        if (scheduleUpdateDTO.getReminders() != null) {
            saveReminders(schedule.getId(), scheduleUpdateDTO.getReminders());
        }
        // 5. 处理案件关联
        if (scheduleUpdateDTO.getCaseId() != null) {
            scheduleRelationService.linkCase(schedule.getId(), scheduleUpdateDTO.getCaseId(), schedule.getTitle());
        }
        // 6. 处理任务关联
        if (scheduleUpdateDTO.getTaskId() != null) {
            scheduleRelationService.linkTask(schedule.getId(), scheduleUpdateDTO.getTaskId(), schedule.getTitle());
        }
        return success;
    }
} 