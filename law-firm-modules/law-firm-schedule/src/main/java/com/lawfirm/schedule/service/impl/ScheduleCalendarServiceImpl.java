package com.lawfirm.schedule.service.impl;

import com.lawfirm.model.schedule.service.ScheduleCalendarService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.service.MeetingRoomBookingService;
import com.lawfirm.model.schedule.service.ScheduleReminderService;
import com.lawfirm.model.schedule.vo.ScheduleCalendarDayVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarMonthVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarWeekVO;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 日程日历视图服务实现类
 */
@Service("scheduleCalendarService")
@RequiredArgsConstructor
@Slf4j
public class ScheduleCalendarServiceImpl implements ScheduleCalendarService {

    private final ScheduleService scheduleService;
    private final MeetingRoomBookingService bookingService;
    private final ScheduleReminderService reminderService;
    
    @Override
    public ScheduleCalendarDayVO getDayView(Long userId, LocalDate date, boolean includeShared) {
        log.info("获取日视图数据，用户ID：{}，日期：{}，包含共享日程：{}", userId, date, includeShared);
        
        if (date == null) {
            date = LocalDate.now();
        }
        
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        
        // 获取当天的日程
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        
        // 获取当天的会议预订
        List<MeetingRoomBookingVO> bookings = bookingService.listBookingsByDate(startTime);
        
        // 获取当天的提醒
        List<ScheduleReminderVO> reminders = reminderService.listPendingReminders(startTime, endTime, null);
        
        // 构建日视图
        ScheduleCalendarDayVO dayView = new ScheduleCalendarDayVO();
        dayView.setDate(date);
        dayView.setWeekend(isWeekend(date));
        // 假日信息可以通过调用节假日服务获取，这里简化处理
        dayView.setHoliday(false);
        dayView.setHolidayName(null);
        dayView.setSchedules(schedules);
        dayView.setBookings(bookings);
        dayView.setReminders(reminders);
        dayView.setTotalSchedules(schedules.size());
        
        // 统计日程类型和状态
        Map<Integer, Integer> typeCount = countByProperty(schedules, ScheduleVO::getType);
        Map<Integer, Integer> statusCount = countByProperty(schedules, ScheduleVO::getStatus);
        
        dayView.setScheduleTypeCount(typeCount);
        dayView.setScheduleStatusCount(statusCount);
        
        return dayView;
    }

    @Override
    public ScheduleCalendarWeekVO getWeekView(Long userId, LocalDate weekStartDate, boolean includeShared) {
        log.info("获取周视图数据，用户ID：{}，周开始日期：{}，包含共享日程：{}", userId, weekStartDate, includeShared);
        
        if (weekStartDate == null) {
            // 如果未指定周开始日期，取本周的周一
            weekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        
        LocalDate weekEndDate = weekStartDate.plusDays(6); // 周日
        
        // 获取周的年份和周数
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfYear = weekStartDate.get(weekFields.weekOfYear());
        int year = weekStartDate.getYear();
        
        // 构建周视图
        ScheduleCalendarWeekVO weekView = new ScheduleCalendarWeekVO();
        weekView.setStartDate(weekStartDate);
        weekView.setEndDate(weekEndDate);
        weekView.setYear(year);
        weekView.setWeekOfYear(weekOfYear);
        
        // 构建周几对应的日期Map
        Map<Integer, LocalDate> weekDays = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = weekStartDate.plusDays(i);
            weekDays.put(date.getDayOfWeek().getValue(), date);
        }
        weekView.setWeekDays(weekDays);
        
        // 获取每天的日视图
        Map<LocalDate, ScheduleCalendarDayVO> dayViews = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = weekStartDate.plusDays(i);
            ScheduleCalendarDayVO dayView = getDayView(userId, date, includeShared);
            dayViews.put(date, dayView);
        }
        weekView.setDayViews(dayViews);
        
        // 获取本周的所有日程
        LocalDateTime startTime = weekStartDate.atStartOfDay();
        LocalDateTime endTime = weekEndDate.atTime(LocalTime.MAX);
        
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        List<MeetingRoomBookingVO> bookings = new ArrayList<>();
        List<ScheduleReminderVO> reminders = reminderService.listPendingReminders(startTime, endTime, null);
        
        // 获取本周的会议预订
        for (int i = 0; i < 7; i++) {
            LocalDate date = weekStartDate.plusDays(i);
            LocalDateTime dateTime = date.atStartOfDay();
            bookings.addAll(bookingService.listBookingsByDate(dateTime));
        }
        
        weekView.setSchedules(schedules);
        weekView.setBookings(bookings);
        weekView.setReminders(reminders);
        weekView.setTotalSchedules(schedules.size());
        
        // 统计日程类型和状态
        Map<Integer, Integer> typeCount = countByProperty(schedules, ScheduleVO::getType);
        Map<Integer, Integer> statusCount = countByProperty(schedules, ScheduleVO::getStatus);
        
        weekView.setScheduleTypeCount(typeCount);
        weekView.setScheduleStatusCount(statusCount);
        
        return weekView;
    }

    @Override
    public ScheduleCalendarMonthVO getMonthView(Long userId, int year, int month, boolean includeShared) {
        log.info("获取月视图数据，用户ID：{}，年份：{}，月份：{}，包含共享日程：{}", userId, year, month, includeShared);
        
        // 构建月份的起始和结束日期
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate monthStartDate = yearMonth.atDay(1);
        LocalDate monthEndDate = yearMonth.atEndOfMonth();
        
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = monthStartDate.getDayOfWeek().getValue();
        
        // 构建月视图
        ScheduleCalendarMonthVO monthView = new ScheduleCalendarMonthVO();
        monthView.setYear(year);
        monthView.setMonth(month);
        monthView.setStartDate(monthStartDate);
        monthView.setEndDate(monthEndDate);
        monthView.setDaysInMonth(daysInMonth);
        monthView.setFirstDayOfWeek(firstDayOfWeek);
        
        // 获取每周的视图
        Map<Integer, ScheduleCalendarWeekVO> weekViews = new HashMap<>();
        LocalDate date = monthStartDate;
        int weekNumber = 1;
        
        while (date.getMonthValue() == month) {
            // 该周的周一
            LocalDate weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            ScheduleCalendarWeekVO weekView = getWeekView(userId, weekStart, includeShared);
            weekViews.put(weekNumber++, weekView);
            
            // 移到下一周的周一
            date = date.plusDays(7);
        }
        
        monthView.setWeekViews(weekViews);
        
        // 获取每天的视图
        Map<LocalDate, ScheduleCalendarDayVO> dayViews = new HashMap<>();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            ScheduleCalendarDayVO dayView = getDayView(userId, currentDate, includeShared);
            dayViews.put(currentDate, dayView);
        }
        
        monthView.setDayViews(dayViews);
        
        // 获取本月的所有日程
        LocalDateTime startTime = monthStartDate.atStartOfDay();
        LocalDateTime endTime = monthEndDate.atTime(LocalTime.MAX);
        
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        List<MeetingRoomBookingVO> bookings = new ArrayList<>();
        List<ScheduleReminderVO> reminders = reminderService.listPendingReminders(startTime, endTime, null);
        
        // 获取本月的会议预订
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            LocalDateTime dateTime = currentDate.atStartOfDay();
            bookings.addAll(bookingService.listBookingsByDate(dateTime));
        }
        
        monthView.setSchedules(schedules);
        monthView.setBookings(bookings);
        monthView.setReminders(reminders);
        monthView.setTotalSchedules(schedules.size());
        
        // 统计日程类型和状态
        Map<Integer, Integer> typeCount = countByProperty(schedules, ScheduleVO::getType);
        Map<Integer, Integer> statusCount = countByProperty(schedules, ScheduleVO::getStatus);
        
        monthView.setScheduleTypeCount(typeCount);
        monthView.setScheduleStatusCount(statusCount);
        
        // 每日日程统计
        Map<LocalDate, Integer> dailyScheduleCount = new HashMap<>();
        for (ScheduleVO schedule : schedules) {
            LocalDate scheduleDate = schedule.getStartTime().toLocalDate();
            dailyScheduleCount.merge(scheduleDate, 1, Integer::sum);
        }
        
        monthView.setDailyScheduleCount(dailyScheduleCount);
        
        return monthView;
    }

    @Override
    public List<Long> getVisibleCalendarOwners(Long userId) {
        log.info("获取用户可见的日程所有者列表，用户ID：{}", userId);
        
        // 这里应该实现逻辑从权限系统获取用户可见的日程所有者列表
        // 简化处理，仅返回用户自己的ID
        List<Long> owners = new ArrayList<>();
        owners.add(userId);
        return owners;
    }

    @Override
    public Map<LocalDate, Integer> getScheduleStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        log.info("获取用户日程统计，用户ID：{}，开始日期：{}，结束日期：{}", userId, startDate, endDate);
        
        LocalDateTime startTime = startDate.atStartOfDay();
        LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
        
        List<ScheduleVO> schedules = scheduleService.listByTimeRange(userId, startTime, endTime);
        
        Map<LocalDate, Integer> statistics = new HashMap<>();
        for (ScheduleVO schedule : schedules) {
            LocalDate scheduleDate = schedule.getStartTime().toLocalDate();
            statistics.merge(scheduleDate, 1, Integer::sum);
        }
        
        return statistics;
    }
    
    /**
     * 判断日期是否为周末
     */
    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
    
    /**
     * 按属性统计
     */
    private <T, K> Map<K, Integer> countByProperty(List<T> list, Function<T, K> keyExtractor) {
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }
        
        return list.stream()
                .collect(Collectors.groupingBy(keyExtractor, Collectors.summingInt(item -> 1)));
    }
} 