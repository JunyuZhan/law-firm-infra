package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.dto.ScheduleReminderDTO;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.mapper.ScheduleReminderMapper;
import com.lawfirm.model.schedule.service.ScheduleReminderService;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;
import com.lawfirm.schedule.converter.ScheduleReminderConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程提醒服务实现类
 */
@Service("scheduleReminderService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleReminderServiceImpl extends ServiceImpl<ScheduleReminderMapper, ScheduleReminder> implements ScheduleReminderService {

    private final ScheduleReminderConvert reminderConvert;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addReminder(Long scheduleId, ScheduleReminderDTO reminderDTO) {
        log.info("添加日程提醒，日程ID：{}", scheduleId);
        
        reminderDTO.setScheduleId(scheduleId);
        ScheduleReminder reminder = reminderConvert.toEntity(reminderDTO);
        save(reminder);
        return reminder.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addReminders(Long scheduleId, List<ScheduleReminderDTO> reminderDTOs) {
        log.info("批量添加日程提醒，日程ID：{}，提醒数量：{}", scheduleId, reminderDTOs.size());
        
        List<ScheduleReminder> reminders = reminderDTOs.stream()
                .peek(dto -> dto.setScheduleId(scheduleId))
                .map(reminderConvert::toEntity)
                .collect(Collectors.toList());
        
        return saveBatch(reminders);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReminder(Long id, ScheduleReminderDTO reminderDTO) {
        log.info("更新日程提醒，ID：{}", id);
        
        ScheduleReminder reminder = getById(id);
        if (reminder == null) {
            log.error("更新日程提醒失败，提醒不存在，ID：{}", id);
            return false;
        }
        
        reminderConvert.updateEntity(reminder, reminderDTO);
        return updateById(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeReminder(Long id) {
        log.info("删除日程提醒，ID：{}", id);
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByScheduleId(Long scheduleId) {
        log.info("删除日程的所有提醒，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleReminder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleReminder::getScheduleId, scheduleId);
        
        return remove(queryWrapper);
    }

    @Override
    public List<ScheduleReminderVO> listByScheduleId(Long scheduleId) {
        log.info("查询日程的提醒列表，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleReminder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleReminder::getScheduleId, scheduleId);
        
        List<ScheduleReminder> reminders = list(queryWrapper);
        return reminders.stream()
                .map(reminderConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleReminderVO getReminderDetail(Long id) {
        log.info("获取提醒详情，ID：{}", id);
        
        ScheduleReminder reminder = getById(id);
        if (reminder == null) {
            return null;
        }
        
        return reminderConvert.toVO(reminder);
    }

    @Override
    public List<ScheduleReminderVO> getPendingReminders(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询待处理的提醒，开始时间：{}，结束时间：{}", startTime, endTime);
        
        // 默认为null，表示不限制优先级
        Integer priority = null;
        
        List<ScheduleReminder> reminders = baseMapper.findPendingReminders(startTime, endTime, priority);
        return reminders.stream()
                .map(reminderConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsReminded(Long id) {
        log.info("标记提醒为已发送，ID：{}", id);
        
        ScheduleReminder reminder = getById(id);
        if (reminder == null) {
            log.error("标记提醒失败，提醒不存在，ID：{}", id);
            return false;
        }
        
        reminder.setIsSent(true);
        reminder.setSentTime(LocalDateTime.now());
        return updateById(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsIgnored(Long id) {
        log.info("标记提醒为已忽略，ID：{}", id);
        
        ScheduleReminder reminder = getById(id);
        if (reminder == null) {
            log.error("标记提醒失败，提醒不存在，ID：{}", id);
            return false;
        }
        
        reminder.setReminderStatus(2); // 假设2表示已忽略
        return updateById(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsFailed(Long id) {
        log.info("标记提醒为发送失败，ID：{}", id);
        
        ScheduleReminder reminder = getById(id);
        if (reminder == null) {
            log.error("标记提醒失败，提醒不存在，ID：{}", id);
            return false;
        }
        
        reminder.setReminderStatus(3); // 假设3表示发送失败
        return updateById(reminder);
    }
    
    // 以下是实现BaseService接口的方法
    
    @Override
    public ScheduleReminder getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<ScheduleReminder> list(QueryWrapper<ScheduleReminder> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<ScheduleReminder> page(Page<ScheduleReminder> page, QueryWrapper<ScheduleReminder> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<ScheduleReminder> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean save(ScheduleReminder entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<ScheduleReminder> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(ScheduleReminder entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<ScheduleReminder> entities) {
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
    public boolean exists(QueryWrapper<ScheduleReminder> wrapper) {
        return count(wrapper) > 0;
    }
} 