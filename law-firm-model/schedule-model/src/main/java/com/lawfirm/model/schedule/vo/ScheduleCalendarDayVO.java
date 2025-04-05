package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 日程日视图VO
 */
@Data
@Accessors(chain = true)
public class ScheduleCalendarDayVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日期
     */
    private LocalDate date;
    
    /**
     * 是否为周末
     */
    private boolean weekend;
    
    /**
     * 是否为节假日
     */
    private boolean holiday;
    
    /**
     * 节假日名称
     */
    private String holidayName;
    
    /**
     * 当天的所有日程
     */
    private transient List<ScheduleVO> schedules;
    
    /**
     * 当天的会议预订
     */
    private transient List<MeetingRoomBookingVO> bookings;
    
    /**
     * 当天的提醒
     */
    private transient List<ScheduleReminderVO> reminders;
    
    /**
     * 当天的日程总数量
     */
    private int totalSchedules;
    
    /**
     * 日程类型统计
     * 键为日程类型，值为该类型的数量
     */
    private transient Map<Integer, Integer> scheduleTypeCount;
    
    /**
     * 日程状态统计
     * 键为日程状态，值为该状态的数量
     */
    private transient Map<Integer, Integer> scheduleStatusCount;
} 