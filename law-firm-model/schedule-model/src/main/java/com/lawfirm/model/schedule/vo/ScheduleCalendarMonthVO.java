package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 日程月视图VO
 */
@Data
@Accessors(chain = true)
public class ScheduleCalendarMonthVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 年份
     */
    private int year;
    
    /**
     * 月份(1-12)
     */
    private int month;
    
    /**
     * 月开始日期
     */
    private LocalDate startDate;
    
    /**
     * 月结束日期
     */
    private LocalDate endDate;
    
    /**
     * 本月的总天数
     */
    private int daysInMonth;
    
    /**
     * 本月第一天是星期几(1-7，1代表周一)
     */
    private int firstDayOfWeek;
    
    /**
     * 每周的日程数据
     * 键为周数(1-6)，值为对应的周视图数据
     */
    private transient Map<Integer, ScheduleCalendarWeekVO> weekViews;
    
    /**
     * 每天的日程数据
     * 键为日期，值为当天的日视图数据
     */
    private transient Map<LocalDate, ScheduleCalendarDayVO> dayViews;
    
    /**
     * 本月的所有日程
     */
    private transient List<ScheduleVO> schedules;
    
    /**
     * 本月的会议预订
     */
    private transient List<MeetingRoomBookingVO> bookings;
    
    /**
     * 本月的提醒
     */
    private transient List<ScheduleReminderVO> reminders;
    
    /**
     * 本月的日程总数量
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
    
    /**
     * 每日日程统计
     * 键为日期，值为当天的日程数量
     */
    private transient Map<LocalDate, Integer> dailyScheduleCount;
} 