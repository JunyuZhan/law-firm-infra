package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.mapper.ScheduleEventMapper;
import com.lawfirm.model.schedule.service.ScheduleEventService;
import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.vo.ScheduleEventVO;
import com.lawfirm.schedule.converter.ScheduleEventConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 日程事件服务实现类
 */
@Service("scheduleEventService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleEventServiceImpl extends ServiceImpl<ScheduleEventMapper, ScheduleEvent> implements ScheduleEventService {

    private final ScheduleEventConvert eventConvert;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addEvent(Long scheduleId, ScheduleEventDTO eventDTO) {
        log.info("添加日程事件，日程ID：{}，事件类型：{}", scheduleId, eventDTO.getEventType());
        
        ScheduleEvent event = eventConvert.toEntity(eventDTO);
        event.setScheduleId(scheduleId);
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
        return event.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEvents(Long scheduleId, List<ScheduleEventDTO> eventDTOs) {
        log.info("批量添加日程事件，日程ID：{}，事件数量：{}", scheduleId, eventDTOs.size());
        
        List<ScheduleEvent> events = eventDTOs.stream()
                .map(eventConvert::toEntity)
                .peek(event -> {
                    event.setScheduleId(scheduleId);
                    event.setCreateTime(LocalDateTime.now());
                })
                .collect(Collectors.toList());
        
        return saveBatch(events);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEvent(Long id, ScheduleEventDTO eventDTO) {
        log.info("更新日程事件，事件ID：{}", id);
        
        ScheduleEvent existingEvent = getById(id);
        if (existingEvent == null) {
            log.error("更新日程事件失败，事件不存在，ID：{}", id);
            return false;
        }
        
        eventConvert.updateEntity(eventDTO, existingEvent);
        existingEvent.setUpdateTime(LocalDateTime.now());
        
        return updateById(existingEvent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeEvent(Long id) {
        log.info("删除日程事件，事件ID：{}", id);
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByScheduleId(Long scheduleId) {
        log.info("删除日程相关事件，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleEvent::getScheduleId, scheduleId);
        
        return remove(queryWrapper);
    }

    @Override
    public List<ScheduleEventVO> listByScheduleId(Long scheduleId) {
        log.info("查询日程事件列表，日程ID：{}", scheduleId);
        
        LambdaQueryWrapper<ScheduleEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleEvent::getScheduleId, scheduleId)
                .orderByDesc(ScheduleEvent::getCreateTime);
        
        List<ScheduleEvent> events = list(queryWrapper);
        return events.stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleEventVO> listByType(Long scheduleId, Integer eventType) {
        log.info("查询指定类型的日程事件，日程ID：{}，事件类型：{}", scheduleId, eventType);
        
        LambdaQueryWrapper<ScheduleEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScheduleEvent::getScheduleId, scheduleId)
                .eq(ScheduleEvent::getEventType, eventType)
                .orderByDesc(ScheduleEvent::getCreateTime);
        
        List<ScheduleEvent> events = list(queryWrapper);
        return events.stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleEventVO getEventDetail(Long id) {
        log.info("获取日程事件详情，事件ID：{}", id);
        
        ScheduleEvent event = getById(id);
        if (event == null) {
            return null;
        }
        
        return eventConvert.toVO(event);
    }

    @Override
    public List<ScheduleEventVO> listRecentEvents(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询用户最近事件，用户ID：{}，开始时间：{}，结束时间：{}", userId, startTime, endTime);
        
        List<ScheduleEvent> events = baseMapper.findRecentEvents(userId, startTime, endTime);
        return events.stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void publishScheduleCreatedEvent(Schedule schedule) {
        log.info("发布日程创建事件，日程ID：{}", schedule.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(schedule.getId());
        event.setEventType(1);  // 1:创建日程
        event.setOperatorId(schedule.getCreatorId());
        event.setOperatorName(schedule.getCreatorName());
        event.setContent(generateScheduleEventContent(schedule));
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishScheduleUpdatedEvent(Schedule schedule, Integer oldStatus, Integer newStatus) {
        log.info("发布日程更新事件，日程ID：{}", schedule.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(schedule.getId());
        event.setEventType(2);  // 2:更新日程
        event.setOperatorId(schedule.getUpdaterId() != null ? schedule.getUpdaterId() : schedule.getCreatorId());
        event.setOperatorName(schedule.getUpdaterName() != null ? schedule.getUpdaterName() : schedule.getCreatorName());
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("oldStatus", oldStatus);
        contentMap.put("newStatus", newStatus);
        contentMap.put("scheduleInfo", generateScheduleEventContent(schedule));
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishScheduleCancelledEvent(Schedule schedule, String reason) {
        log.info("发布日程取消事件，日程ID：{}", schedule.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(schedule.getId());
        event.setEventType(3);  // 3:取消日程
        event.setOperatorId(schedule.getUpdaterId() != null ? schedule.getUpdaterId() : schedule.getCreatorId());
        event.setOperatorName(schedule.getUpdaterName() != null ? schedule.getUpdaterName() : schedule.getCreatorName());
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reason", reason);
        contentMap.put("scheduleInfo", generateScheduleEventContent(schedule));
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishScheduleCompletedEvent(Schedule schedule) {
        log.info("发布日程完成事件，日程ID：{}", schedule.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(schedule.getId());
        event.setEventType(4);  // 4:完成日程
        event.setOperatorId(schedule.getUpdaterId() != null ? schedule.getUpdaterId() : schedule.getCreatorId());
        event.setOperatorName(schedule.getUpdaterName() != null ? schedule.getUpdaterName() : schedule.getCreatorName());
        event.setContent(generateScheduleEventContent(schedule));
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishMeetingRoomBookedEvent(MeetingRoomBooking booking) {
        log.info("发布会议室预订事件，预订ID：{}", booking.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(booking.getScheduleId());
        event.setEventType(8);  // 8:会议室预订确认
        event.setOperatorId(booking.getCreatorId());
        event.setOperatorName(booking.getCreatorName());
        event.setContent(generateBookingEventContent(booking));
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishBookingConfirmedEvent(MeetingRoomBooking booking) {
        log.info("发布会议室预订确认事件，预订ID：{}", booking.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(booking.getScheduleId());
        event.setEventType(8);  // 8:会议室预订确认
        event.setOperatorId(booking.getConfirmerId() != null ? booking.getConfirmerId() : booking.getCreatorId());
        event.setOperatorName(booking.getConfirmerName() != null ? booking.getConfirmerName() : booking.getCreatorName());
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("status", "confirmed");
        contentMap.put("bookingInfo", generateBookingEventContent(booking));
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishBookingCancelledEvent(MeetingRoomBooking booking, String reason) {
        log.info("发布会议室预订取消事件，预订ID：{}", booking.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(booking.getScheduleId());
        event.setEventType(8);  // 8:会议室预订确认
        event.setOperatorId(booking.getCancelerId() != null ? booking.getCancelerId() : booking.getCreatorId());
        event.setOperatorName(booking.getCancelerName() != null ? booking.getCancelerName() : booking.getCreatorName());
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("status", "cancelled");
        contentMap.put("reason", reason);
        contentMap.put("bookingInfo", generateBookingEventContent(booking));
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishReminderEvent(ScheduleReminder reminder) {
        log.info("发布日程提醒事件，提醒ID：{}", reminder.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(reminder.getScheduleId());
        event.setEventType(9);  // 9:日程提醒
        event.setOperatorId(0L);  // 系统操作
        event.setOperatorName("系统");
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reminderId", reminder.getId());
        contentMap.put("reminderTime", reminder.getReminderTime());
        contentMap.put("reminderType", reminder.getReminderType());
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishScheduleConflictEvent(Schedule schedule, List<Schedule> conflictingSchedules) {
        log.info("发布日程冲突事件，日程ID：{}", schedule.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(schedule.getId());
        event.setEventType(10);  // 10:其他
        event.setOperatorId(0L);  // 系统操作
        event.setOperatorName("系统");
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("type", "conflict");
        contentMap.put("scheduleInfo", generateScheduleEventContent(schedule));
        
        List<Map<String, Object>> conflictList = new ArrayList<>();
        for (Schedule conflictSchedule : conflictingSchedules) {
            conflictList.add(generateScheduleEventContent(conflictSchedule));
        }
        contentMap.put("conflictSchedules", conflictList);
        
        event.setContent(contentMap.toString());
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishParticipantResponseEvent(Long scheduleId, Long participantId, Integer responseStatus) {
        log.info("发布参与者响应事件，日程ID：{}，参与者ID：{}，响应状态：{}", scheduleId, participantId, responseStatus);
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(scheduleId);
        event.setEventType(7);  // 7:响应邀请
        event.setOperatorId(participantId);
        // 这里应该从用户服务获取参与者姓名，简化处理
        event.setOperatorName("参与者" + participantId);
        
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("participantId", participantId);
        contentMap.put("responseStatus", responseStatus);
        event.setContent(contentMap.toString());
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public boolean handleScheduleConflict(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("处理日程冲突，用户ID：{}，开始时间：{}，结束时间：{}", userId, startTime, endTime);
        // 实际实现应该检查用户在该时间段是否有其他日程
        // 这里简化处理，返回没有冲突
        return false;
    }
    
    @Override
    public boolean handleReminderSending(Long reminderId) {
        log.info("处理提醒发送，提醒ID：{}", reminderId);
        // 实际实现应该调用消息服务发送提醒
        // 这里简化处理，直接返回成功
        return true;
    }
    
    @Override
    public boolean handleBookingConflict(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("处理会议室预订冲突，会议室ID：{}，开始时间：{}，结束时间：{}", roomId, startTime, endTime);
        // 实际实现应该检查会议室在该时间段是否有其他预订
        // 这里简化处理，返回没有冲突
        return false;
    }
    
    @Override
    public boolean subscribeEvent(String eventType, Long subscriberId) {
        log.info("订阅事件，事件类型：{}，订阅者ID：{}", eventType, subscriberId);
        // 实际实现应该将订阅信息存入数据库
        // 这里简化处理，直接返回成功
        return true;
    }
    
    @Override
    public boolean unsubscribeEvent(String eventType, Long subscriberId) {
        log.info("取消订阅事件，事件类型：{}，订阅者ID：{}", eventType, subscriberId);
        // 实际实现应该从数据库中删除订阅信息
        // 这里简化处理，直接返回成功
        return true;
    }
    
    @Override
    public List<String> getUserSubscribedEvents(Long userId) {
        log.info("获取用户订阅的事件类型，用户ID：{}", userId);
        // 实际实现应该从数据库中查询用户订阅的事件类型
        // 这里简化处理，返回空列表
        return new ArrayList<>();
    }
    
    /**
     * 生成日程事件内容
     */
    private Map<String, Object> generateScheduleEventContent(Schedule schedule) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("id", schedule.getId());
        contentMap.put("title", schedule.getTitle());
        contentMap.put("startTime", schedule.getStartTime());
        contentMap.put("endTime", schedule.getEndTime());
        contentMap.put("location", schedule.getLocation());
        contentMap.put("status", schedule.getStatus());
        return contentMap;
    }
    
    /**
     * 生成会议室预订事件内容
     */
    private Map<String, Object> generateBookingEventContent(MeetingRoomBooking booking) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("id", booking.getId());
        contentMap.put("meetingRoomId", booking.getMeetingRoomId());
        contentMap.put("scheduleId", booking.getScheduleId());
        contentMap.put("startTime", booking.getStartTime());
        contentMap.put("endTime", booking.getEndTime());
        contentMap.put("status", booking.getStatus());
        return contentMap;
    }
} 