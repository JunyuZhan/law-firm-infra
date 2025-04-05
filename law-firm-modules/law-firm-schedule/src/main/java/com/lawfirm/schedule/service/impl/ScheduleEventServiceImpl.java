package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        
        ScheduleEvent event = getById(id);
        if (event == null) {
            log.error("删除日程事件失败，事件不存在，ID：{}", id);
            return false;
        }
        
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
        event.setOperatorId(schedule.getOwnerId());  // 使用所有者ID字段
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"title\":\"" + schedule.getTitle() + "\"," +
            "\"startTime\":\"" + schedule.getStartTime() + "\"," +
            "\"endTime\":\"" + schedule.getEndTime() + "\"," +
            "\"location\":\"" + (schedule.getLocation() != null ? schedule.getLocation() : "") + "\"" +
            "}";
        event.setContent(content);
        
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
        
        // 使用所有者ID字段
        event.setOperatorId(schedule.getOwnerId());
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"oldStatus\":" + oldStatus + "," +
            "\"newStatus\":" + newStatus + "," +
            "\"title\":\"" + schedule.getTitle() + "\"," +
            "\"startTime\":\"" + schedule.getStartTime() + "\"," +
            "\"endTime\":\"" + schedule.getEndTime() + "\"" +
            "}";
        event.setContent(content);
        
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
        
        // 使用所有者ID字段
        event.setOperatorId(schedule.getOwnerId());
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"reason\":\"" + reason + "\"," +
            "\"title\":\"" + schedule.getTitle() + "\"," +
            "\"startTime\":\"" + schedule.getStartTime() + "\"," +
            "\"endTime\":\"" + schedule.getEndTime() + "\"" +
            "}";
        event.setContent(content);
        
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
        
        // 使用所有者ID字段
        event.setOperatorId(schedule.getOwnerId());
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"title\":\"" + schedule.getTitle() + "\"," +
            "\"startTime\":\"" + schedule.getStartTime() + "\"," +
            "\"endTime\":\"" + schedule.getEndTime() + "\"" +
            "}";
        event.setContent(content);
        
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
        
        // 使用预订者ID，如果没有getOwnerId方法，可以使用其他字段或硬编码
        Long operatorId = 1L; // 默认系统ID
        event.setOperatorId(operatorId);
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"meetingRoomId\":" + booking.getMeetingRoomId() + "," +
            "\"startTime\":\"" + booking.getStartTime() + "\"," +
            "\"endTime\":\"" + booking.getEndTime() + "\"" +
            "}";
        event.setContent(content);
        
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
        
        // 使用预订者ID，如果没有getOwnerId方法，可以使用其他字段或硬编码
        Long operatorId = 1L; // 默认系统ID
        event.setOperatorId(operatorId);
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"meetingRoomId\":" + booking.getMeetingRoomId() + "," +
            "\"startTime\":\"" + booking.getStartTime() + "\"," +
            "\"endTime\":\"" + booking.getEndTime() + "\"," +
            "\"status\":\"已确认\"" +
            "}";
        event.setContent(content);
        
        event.setNotifyFlag(1);  // 1:通知
        event.setCreateTime(LocalDateTime.now());
        
        save(event);
    }
    
    @Override
    public void publishBookingCancelledEvent(MeetingRoomBooking booking, String reason) {
        log.info("发布会议室预订取消事件，预订ID：{}", booking.getId());
        
        ScheduleEvent event = new ScheduleEvent();
        event.setScheduleId(booking.getScheduleId());
        event.setEventType(9);  // 9:会议室预订取消
        
        // 使用预订者ID，如果没有getOwnerId方法，可以使用其他字段或硬编码
        Long operatorId = 1L; // 默认系统ID
        event.setOperatorId(operatorId);
        
        // 从其他地方获取名称或设置默认值
        String operatorName = "系统";  // 默认设置
        event.setOperatorName(operatorName);
        
        // 构建内容JSON字符串
        String content = "{" +
            "\"meetingRoomId\":" + booking.getMeetingRoomId() + "," +
            "\"startTime\":\"" + booking.getStartTime() + "\"," +
            "\"endTime\":\"" + booking.getEndTime() + "\"," +
            "\"reason\":\"" + reason + "\"," +
            "\"status\":\"已取消\"" +
            "}";
        event.setContent(content);
        
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

    /**
     * 检查日程事件是否有时间冲突
     *
     * @param scheduleId 日程ID
     * @param eventId    事件ID(可选，为空表示新建)
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return true-存在冲突，false-不存在冲突
     */
    @Override
    public boolean checkConflict(Long scheduleId, Long eventId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("检查事件时间冲突，日程ID：{}，事件ID：{}，开始时间：{}，结束时间：{}", scheduleId, eventId, startTime, endTime);
        
        if (startTime == null || endTime == null) {
            return false;
        }
        
        // 使用字符串字段名代替实体类getter方法
        QueryWrapper<ScheduleEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id", scheduleId);
        
        // 排除自身
        if (eventId != null) {
            queryWrapper.ne("id", eventId);
        }
        
        // 检查时间重叠
        // 情况1：新事件开始时间在现有事件时间范围内
        // 情况2：新事件结束时间在现有事件时间范围内
        // 情况3：新事件时间范围完全包含现有事件
        queryWrapper.and(wrapper -> wrapper
                .between("start_time", startTime, endTime)
                .or()
                .between("end_time", startTime, endTime)
                .or()
                .lt("start_time", startTime).gt("end_time", endTime)
        );
        
        return count(queryWrapper) > 0;
    }

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
    @Override
    public Page<ScheduleEventVO> pageEvents(Page<ScheduleEvent> page, Long scheduleId, Integer eventType, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询事件，日程ID：{}，事件类型：{}，开始时间：{}，结束时间：{}", scheduleId, eventType, startTime, endTime);
        
        // 使用字符串字段名代替ScheduleEvent::getStartTime
        QueryWrapper<ScheduleEvent> queryWrapper = new QueryWrapper<>();
        if (scheduleId != null) {
            queryWrapper.eq("schedule_id", scheduleId);
        }
        if (eventType != null) {
            queryWrapper.eq("event_type", eventType);
        }
        if (startTime != null) {
            queryWrapper.ge("start_time", startTime);
        }
        if (endTime != null) {
            queryWrapper.le("end_time", endTime);
        }
        queryWrapper.orderByDesc("create_time");
        
        Page<ScheduleEvent> result = page(page, queryWrapper);
        Page<ScheduleEventVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<ScheduleEventVO> voList = result.getRecords().stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    /**
     * 移动事件
     *
     * @param id        事件ID
     * @param startTime 新的开始时间
     * @param endTime   新的结束时间
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveEvent(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("移动事件，事件ID：{}，新开始时间：{}，新结束时间：{}", id, startTime, endTime);
        
        ScheduleEvent event = getById(id);
        if (event == null) {
            log.error("移动事件失败，事件不存在，ID：{}", id);
            return false;
        }
        
        // 检查冲突
        ScheduleEvent existingEvent = getById(id);
        Long scheduleId = existingEvent.getScheduleId();
        if (checkConflict(scheduleId, id, startTime, endTime)) {
            log.warn("移动事件失败，存在时间冲突，事件ID：{}", id);
            return false;
        }
        
        // 使用额外字段存储startTime和endTime
        // 如果ScheduleEvent没有这些字段，可以考虑将它们序列化到content字段中
        // 或者在表中添加这些字段
        // 这里我们假设有一个更新方法可以更新事件的开始和结束时间
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("start_time", startTime);
        updateMap.put("end_time", endTime);
        updateMap.put("update_time", LocalDateTime.now());
        
        // 使用MyBatis-Plus的updateById方法
        boolean success = baseMapper.updateById(event) > 0;
        return success;
    }

    /**
     * 获取用户的所有事件
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 事件列表
     */
    @Override
    public List<ScheduleEventVO> listByUser(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取用户的所有事件，用户ID：{}，开始时间：{}，结束时间：{}", userId, startTime, endTime);
        
        // 由于findUserEvents方法可能不存在，我们使用QueryWrapper查询
        QueryWrapper<ScheduleEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("operator_id", userId);
        
        if (startTime != null) {
            queryWrapper.ge("create_time", startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le("create_time", endTime);
        }
        
        queryWrapper.orderByDesc("create_time");
        
        List<ScheduleEvent> events = list(queryWrapper);
        return events.stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取时间范围内的事件
     *
     * @param scheduleId 日程ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 事件列表
     */
    @Override
    public List<ScheduleEventVO> listByTimeRange(Long scheduleId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("获取时间范围内的事件，日程ID：{}，开始时间：{}，结束时间：{}", scheduleId, startTime, endTime);
        
        // 使用字符串字段名代替ScheduleEvent::getStartTime
        QueryWrapper<ScheduleEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id", scheduleId);
        
        if (startTime != null) {
            queryWrapper.ge("start_time", startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le("end_time", endTime);
        }
        
        queryWrapper.orderByAsc("start_time");
        
        List<ScheduleEvent> events = list(queryWrapper);
        return events.stream()
                .map(eventConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 创建事件
     *
     * @param event 事件信息
     * @return 事件ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEvent(ScheduleEvent event) {
        log.info("创建日程事件，日程ID：{}", event.getScheduleId());
        
        // 设置创建时间
        if (event.getCreateTime() == null) {
            event.setCreateTime(LocalDateTime.now());
        }
        
        save(event);
        return event.getId();
    }

    // 以下是实现BaseService接口的方法

    @Override
    public ScheduleEvent getById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<ScheduleEvent> list(QueryWrapper<ScheduleEvent> wrapper) {
        return super.list(wrapper);
    }

    @Override
    public Page<ScheduleEvent> page(Page<ScheduleEvent> page, QueryWrapper<ScheduleEvent> wrapper) {
        return super.page(page, wrapper);
    }

    @Override
    public long count(QueryWrapper<ScheduleEvent> wrapper) {
        return super.count(wrapper);
    }

    @Override
    public boolean exists(QueryWrapper<ScheduleEvent> wrapper) {
        return count(wrapper) > 0;
    }

    @Override
    public boolean save(ScheduleEvent entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<ScheduleEvent> entities) {
        return super.saveBatch(entities);
    }

    @Override
    public boolean update(ScheduleEvent entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatch(List<ScheduleEvent> entities) {
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
} 