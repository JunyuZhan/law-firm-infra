package com.lawfirm.model.schedule.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.schedule.dto.ScheduleReminderDTO;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程提醒服务接口
 */
public interface ScheduleReminderService extends BaseService<ScheduleReminder> {
    
    /**
     * 添加提醒
     *
     * @param scheduleId 日程ID
     * @param reminderDTO 提醒DTO
     * @return 创建的提醒ID
     */
    Long addReminder(Long scheduleId, ScheduleReminderDTO reminderDTO);
    
    /**
     * 批量添加提醒
     *
     * @param scheduleId 日程ID
     * @param reminderDTOs 提醒DTO列表
     * @return 是否成功
     */
    boolean addReminders(Long scheduleId, List<ScheduleReminderDTO> reminderDTOs);
    
    /**
     * 更新提醒
     *
     * @param id 提醒ID
     * @param reminderDTO 提醒DTO
     * @return 是否成功
     */
    boolean updateReminder(Long id, ScheduleReminderDTO reminderDTO);
    
    /**
     * 删除提醒
     *
     * @param id 提醒ID
     * @return 是否成功
     */
    boolean removeReminder(Long id);
    
    /**
     * 根据日程ID删除所有提醒
     *
     * @param scheduleId 日程ID
     * @return 是否成功
     */
    boolean removeByScheduleId(Long scheduleId);
    
    /**
     * 获取日程的所有提醒
     *
     * @param scheduleId 日程ID
     * @return 提醒列表
     */
    List<ScheduleReminderVO> listByScheduleId(Long scheduleId);
    
    /**
     * 获取提醒详情
     *
     * @param id 提醒ID
     * @return 提醒VO
     */
    ScheduleReminderVO getReminderDetail(Long id);
    
    /**
     * 获取待处理的提醒
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 提醒列表
     */
    List<ScheduleReminderVO> getPendingReminders(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 标记提醒为已发送
     *
     * @param id 提醒ID
     * @return 是否成功
     */
    boolean markAsReminded(Long id);
    
    /**
     * 标记提醒为已忽略
     *
     * @param id 提醒ID
     * @return 是否成功
     */
    boolean markAsIgnored(Long id);
    
    /**
     * 标记提醒为发送失败
     *
     * @param id 提醒ID
     * @return 是否成功
     */
    boolean markAsFailed(Long id);
} 