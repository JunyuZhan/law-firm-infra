package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.mapper.ScheduleMapper;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import com.lawfirm.schedule.converter.ScheduleConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程服务实现类
 */
@Service("scheduleService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    private final ScheduleConvert scheduleConvert;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSchedule(ScheduleDTO scheduleDTO) {
        log.info("创建日程，标题：{}", scheduleDTO.getTitle());
        Schedule schedule = scheduleConvert.toEntity(scheduleDTO);
        save(schedule);
        return schedule.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        log.info("更新日程，ID：{}, 标题：{}", id, scheduleDTO.getTitle());
        
        Schedule schedule = getById(id);
        if (schedule == null) {
            log.error("更新日程失败，日程不存在，ID：{}", id);
            return false;
        }
        
        schedule = scheduleConvert.updateEntity(schedule, scheduleDTO);
        return updateById(schedule);
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
        return scheduleConvert.toVO(schedule);
    }

    @Override
    public Page<ScheduleVO> pageByUser(Page<Schedule> page, Long userId, Integer status) {
        log.info("分页查询用户日程，用户ID：{}, 状态：{}", userId, status);
        
        // 创建查询条件
        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Schedule::getOwnerId, userId);
        
        if (status != null) {
            queryWrapper.eq(Schedule::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Schedule::getCreateTime);
        
        // 使用通用方法进行分页查询
        Page<Schedule> result = page(page, queryWrapper);
        
        Page<ScheduleVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<ScheduleVO> records = result.getRecords().stream()
                .map(scheduleConvert::toVO)
                .collect(Collectors.toList());
        voPage.setRecords(records);
        
        return voPage;
    }

    @Override
    public List<ScheduleVO> listByDate(Long userId, LocalDate date) {
        log.info("查询指定日期的日程，用户ID：{}, 日期：{}", userId, date);
        
        if (date == null) {
            return Collections.emptyList();
        }
        
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        
        return listByTimeRange(userId, startTime, endTime);
    }

    @Override
    public List<ScheduleVO> listByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询指定时间范围的日程，用户ID：{}, 开始时间：{}, 结束时间：{}", userId, startTime, endTime);
        
        if (startTime == null || endTime == null) {
            return Collections.emptyList();
        }
        
        List<Schedule> schedules = baseMapper.findByTimeRange(startTime, endTime, userId);
        return schedules.stream()
                .map(scheduleConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleVO> listByCaseId(Long caseId) {
        log.info("查询与案件关联的日程，案件ID：{}", caseId);
        List<Schedule> schedules = baseMapper.findByCaseId(caseId);
        return schedules.stream()
                .map(scheduleConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleVO> listByTaskId(Long taskId) {
        log.info("查询与任务关联的日程，任务ID：{}", taskId);
        List<Schedule> schedules = baseMapper.findByTaskId(taskId);
        return schedules.stream()
                .map(scheduleConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleVO> checkConflicts(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long excludeScheduleId) {
        log.info("检查日程时间冲突，用户ID：{}, 开始时间：{}, 结束时间：{}, 排除的日程ID：{}", 
                userId, startTime, endTime, excludeScheduleId);
        
        if (startTime == null || endTime == null) {
            return Collections.emptyList();
        }
        
        List<Schedule> schedules = baseMapper.findConflicts(startTime, endTime, userId, excludeScheduleId);
        return schedules.stream()
                .map(scheduleConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkCase(Long scheduleId, Long caseId, String description) {
        log.info("将日程与案件关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        // 这里需要实现关联逻辑，暂时返回true
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkTask(Long scheduleId, Long taskId, String description) {
        log.info("将日程与任务关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        // 这里需要实现关联逻辑，暂时返回true
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkCase(Long scheduleId, Long caseId) {
        log.info("解除日程与案件的关联，日程ID：{}，案件ID：{}", scheduleId, caseId);
        // 这里需要实现解除关联逻辑，暂时返回true
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkTask(Long scheduleId, Long taskId) {
        log.info("解除日程与任务的关联，日程ID：{}，任务ID：{}", scheduleId, taskId);
        // 这里需要实现解除关联逻辑，暂时返回true
        return true;
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
        
        schedule.setStatus(statusCode);
        return updateById(schedule);
    }
    
    // 以下是实现BaseService接口的方法
    
    @Override
    public boolean save(Schedule entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<Schedule> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(Schedule entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<Schedule> entities) {
        return super.updateBatchById(entities);
    }
    
    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return super.removeByIds(ids);
    }
    
    @Override
    public Schedule getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<Schedule> list(QueryWrapper<Schedule> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<Schedule> page(Page<Schedule> page, QueryWrapper<Schedule> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<Schedule> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean exists(QueryWrapper<Schedule> wrapper) {
        return super.count(wrapper) > 0;
    }
} 