package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import com.lawfirm.model.schedule.vo.ScheduleEventVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日程事件服务接口
 */
public interface ScheduleEventService {
    
    /**
     * 添加日程事件
     *
     * @param scheduleId 日程ID
     * @param eventDTO 事件数据
     * @return 事件ID
     */
    Long addEvent(Long scheduleId, ScheduleEventDTO eventDTO);
    
    /**
     * 批量添加日程事件
     *
     * @param scheduleId 日程ID
     * @param eventDTOs 事件数据列表
     * @return 是否添加成功
     */
    boolean addEvents(Long scheduleId, List<ScheduleEventDTO> eventDTOs);
    
    /**
     * 更新日程事件
     *
     * @param id 事件ID
     * @param eventDTO 事件数据
     * @return 是否更新成功
     */
    boolean updateEvent(Long id, ScheduleEventDTO eventDTO);
    
    /**
     * 删除日程事件
     *
     * @param id 事件ID
     * @return 是否删除成功
     */
    boolean removeEvent(Long id);
    
    /**
     * 删除日程相关事件
     *
     * @param scheduleId 日程ID
     * @return 是否删除成功
     */
    boolean removeByScheduleId(Long scheduleId);
    
    /**
     * 获取日程事件列表
     *
     * @param scheduleId 日程ID
     * @return 事件列表
     */
    List<ScheduleEventVO> listByScheduleId(Long scheduleId);
    
    /**
     * 获取指定类型的日程事件列表
     *
     * @param scheduleId 日程ID
     * @param eventType 事件类型
     * @return 事件列表
     */
    List<ScheduleEventVO> listByType(Long scheduleId, Integer eventType);
    
    /**
     * 获取日程事件详情
     *
     * @param id 事件ID
     * @return 事件详情
     */
    ScheduleEventVO getEventDetail(Long id);
    
    /**
     * 获取用户最近的事件
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件列表
     */
    List<ScheduleEventVO> listRecentEvents(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 发布日程创建事件
     *
     * @param schedule 日程实体
     */
    void publishScheduleCreatedEvent(Schedule schedule);
    
    /**
     * 发布日程更新事件
     *
     * @param schedule 日程实体
     * @param oldStatus 原状态
     * @param newStatus 新状态
     */
    void publishScheduleUpdatedEvent(Schedule schedule, Integer oldStatus, Integer newStatus);
    
    /**
     * 发布日程取消事件
     *
     * @param schedule 日程实体
     * @param reason 取消原因
     */
    void publishScheduleCancelledEvent(Schedule schedule, String reason);
    
    /**
     * 发布日程完成事件
     *
     * @param schedule 日程实体
     */
    void publishScheduleCompletedEvent(Schedule schedule);
    
    /**
     * 发布会议室预订事件
     *
     * @param booking 预订实体
     */
    void publishMeetingRoomBookedEvent(MeetingRoomBooking booking);
    
    /**
     * 发布会议室预订确认事件
     *
     * @param booking 预订实体
     */
    void publishBookingConfirmedEvent(MeetingRoomBooking booking);
    
    /**
     * 发布会议室预订取消事件
     *
     * @param booking 预订实体
     * @param reason 取消原因
     */
    void publishBookingCancelledEvent(MeetingRoomBooking booking, String reason);
    
    /**
     * 发布日程提醒事件
     *
     * @param reminder 提醒实体
     */
    void publishReminderEvent(ScheduleReminder reminder);
    
    /**
     * 发布日程冲突事件
     *
     * @param schedule 新日程
     * @param conflictingSchedules 冲突的日程列表
     */
    void publishScheduleConflictEvent(Schedule schedule, List<Schedule> conflictingSchedules);
    
    /**
     * 发布参与者响应事件
     *
     * @param scheduleId 日程ID
     * @param participantId 参与者ID
     * @param responseStatus 响应状态
     */
    void publishParticipantResponseEvent(Long scheduleId, Long participantId, Integer responseStatus);
    
    /**
     * 处理日程冲突
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否存在冲突
     */
    boolean handleScheduleConflict(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 处理提醒发送
     *
     * @param reminderId 提醒ID
     * @return 是否发送成功
     */
    boolean handleReminderSending(Long reminderId);
    
    /**
     * 处理会议室预订冲突
     *
     * @param roomId 会议室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否存在冲突
     */
    boolean handleBookingConflict(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 订阅事件
     *
     * @param eventType 事件类型
     * @param subscriberId 订阅者ID
     * @return 是否订阅成功
     */
    boolean subscribeEvent(String eventType, Long subscriberId);
    
    /**
     * 取消订阅事件
     *
     * @param eventType 事件类型
     * @param subscriberId 订阅者ID
     * @return 是否取消成功
     */
    boolean unsubscribeEvent(String eventType, Long subscriberId);
    
    /**
     * 获取用户订阅的事件类型
     *
     * @param userId 用户ID
     * @return 事件类型列表
     */
    List<String> getUserSubscribedEvents(Long userId);

    /**
     * 创建事件
     *
     * @param event 事件信息
     * @return 事件ID
     */
    Long createEvent(ScheduleEvent event);

    /**
     * 获取时间范围内的事件
     *
     * @param scheduleId 日程ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 事件列表
     */
    List<ScheduleEventVO> listByTimeRange(Long scheduleId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 分页查询事件
     *
     * @param page       分页参数
     * @param scheduleId 日程ID
     * @param eventType  事件类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 分页结果
     */
    Page<ScheduleEventVO> pageEvents(Page<ScheduleEvent> page, Long scheduleId, Integer eventType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 移动事件
     *
     * @param id        事件ID
     * @param startTime 新的开始时间
     * @param endTime   新的结束时间
     * @return 是否成功
     */
    boolean moveEvent(Long id, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取用户的所有事件
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 事件列表
     */
    List<ScheduleEventVO> listByUser(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检查事件冲突
     *
     * @param scheduleId 日程ID
     * @param eventId    事件ID（更新时使用，新建时为null）
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 是否存在冲突
     */
    boolean checkConflict(Long scheduleId, Long eventId, LocalDateTime startTime, LocalDateTime endTime);
} 