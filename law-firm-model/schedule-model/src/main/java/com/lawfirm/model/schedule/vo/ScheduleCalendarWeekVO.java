package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 日程周视图VO
 */
@Data
@Accessors(chain = true)
public class ScheduleCalendarWeekVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 周开始日期
     */
    private LocalDate startDate;
    
    /**
     * 周结束日期
     */
    private LocalDate endDate;
    
    /**
     * 年份
     */
    private int year;
    
    /**
     * 周数(1-52)
     */
    private int weekOfYear;
    
    /**
     * 周几的对应日期
     * 键为星期几(1-7，1代表周一)，值为对应的日期
     */
    private transient Map<Integer, LocalDate> weekDays;
    
    /**
     * 每天的日程数据
     * 键为日期，值为当天的日视图数据
     */
    private transient Map<LocalDate, ScheduleCalendarDayVO> dayViews;
    
    /**
     * 本周的所有日程
     */
    private transient List<ScheduleVO> schedules;
    
    /**
     * 本周的会议预订
     */
    private transient List<MeetingRoomBookingVO> bookings;
    
    /**
     * 本周的提醒
     */
    private transient List<ScheduleReminderVO> reminders;
    
    /**
     * 本周的日程总数量
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