package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程提醒Mapper接口
 */
@Mapper
public interface ScheduleReminderMapper extends BaseMapper<ScheduleReminder> {
    
    /**
     * 查询日程的所有提醒
     *
     * @param scheduleId 日程ID
     * @return 提醒列表
     */
    List<ScheduleReminder> findByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 查询指定时间范围内需要发送的提醒
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param reminderStatusCode 提醒状态码
     * @return 提醒列表
     */
    List<ScheduleReminder> findPendingReminders(@Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime,
                                              @Param("reminderStatusCode") Integer reminderStatusCode);
    
    /**
     * 更新提醒状态
     *
     * @param id 提醒ID
     * @param reminderStatusCode 提醒状态码
     * @return 影响行数
     */
    int updateReminderStatus(@Param("id") Long id, 
                             @Param("reminderStatusCode") Integer reminderStatusCode);
    
    /**
     * 根据日程ID删除所有提醒
     *
     * @param scheduleId 日程ID
     * @return 影响行数
     */
    int deleteByScheduleId(@Param("scheduleId") Long scheduleId);
    
    /**
     * 批量添加提醒
     *
     * @param reminders 提醒列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<ScheduleReminder> reminders);
} 