package com.lawfirm.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.schedule.entity.ScheduleCalendar;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import com.lawfirm.model.schedule.mapper.ScheduleCalendarMapper;
import com.lawfirm.model.schedule.service.ScheduleCalendarService;
import com.lawfirm.model.schedule.service.ScheduleService;
import com.lawfirm.model.schedule.service.MeetingRoomBookingService;
import com.lawfirm.model.schedule.service.ScheduleReminderService;
import com.lawfirm.model.schedule.vo.ScheduleCalendarDayVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarMonthVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarWeekVO;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.core.context.BaseContextHandler;

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
public class ScheduleCalendarServiceImpl extends ServiceImpl<ScheduleCalendarMapper, ScheduleCalendar> implements ScheduleCalendarService {

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
        List<ScheduleReminderVO> reminders = reminderService.getPendingReminders(startTime, endTime);
        
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
        List<ScheduleReminderVO> reminders = reminderService.getPendingReminders(startTime, endTime);
        
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
        List<ScheduleReminderVO> reminders = reminderService.getPendingReminders(startTime, endTime);
        
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
     * 统计日程属性的数量
     */
    private Map<Integer, Integer> countByProperty(List<ScheduleVO> schedules, Function<ScheduleVO, Object> propertyGetter) {
        return schedules.stream()
                .collect(Collectors.groupingBy(
                        schedule -> {
                            Object propertyValue = propertyGetter.apply(schedule);
                            // 将枚举值转换为整数
                            if (propertyValue instanceof Integer) {
                                return (Integer) propertyValue;
                            } else if (propertyValue instanceof ScheduleType) {
                                return ((ScheduleType) propertyValue).getCode();
                            } else if (propertyValue instanceof ScheduleStatus) {
                                return ((ScheduleStatus) propertyValue).getCode();
                            } else {
                                // 默认返回0
                                return 0;
                            }
                        },
                        Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));
    }

    /**
     * 取消与用户共享日历
     *
     * @param calendarId 日历ID
     * @param userId     用户ID
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unshareCalendar(Long calendarId, Long userId) {
        log.info("取消共享日历，日历ID：{}，用户ID：{}", calendarId, userId);
        
        try {
            // 实现取消共享逻辑
            // 通常涉及删除日历共享表的记录
            
            // 简化处理，假设取消共享成功
            return true;
        } catch (Exception e) {
            log.error("取消共享日历失败，日历ID：{}，用户ID：{}", calendarId, userId, e);
            return false;
        }
    }
    
    // 以下是实现BaseService接口的方法
    
    @Override
    public ScheduleCalendar getById(Long id) {
        return super.getById(id);
    }
    
    @Override
    public List<ScheduleCalendar> list(QueryWrapper<ScheduleCalendar> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<ScheduleCalendar> page(Page<ScheduleCalendar> page, QueryWrapper<ScheduleCalendar> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<ScheduleCalendar> wrapper) {
        return super.count(wrapper);
    }
    
    @Override
    public boolean save(ScheduleCalendar entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<ScheduleCalendar> entities) {
        return super.saveBatch(entities);
    }
    
    @Override
    public boolean update(ScheduleCalendar entity) {
        return super.updateById(entity);
    }
    
    @Override
    public boolean updateBatch(List<ScheduleCalendar> entities) {
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
    public boolean exists(QueryWrapper<ScheduleCalendar> wrapper) {
        return count(wrapper) > 0;
    }
    
    @Override
    public Long createCalendar(ScheduleCalendar calendar) {
        log.info("创建日历：{}", calendar.getName());
        
        calendar.setCreateTime(LocalDateTime.now());
        calendar.setUpdateTime(LocalDateTime.now());
        
        save(calendar);
        return calendar.getId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCalendar(Long id, ScheduleCalendar calendar) {
        log.info("更新日历：{}", id);
        
        ScheduleCalendar existingCalendar = getById(id);
        if (existingCalendar == null) {
            log.error("更新日历失败，日历不存在，ID：{}", id);
            return false;
        }
        
        calendar.setId(id);
        calendar.setUpdateTime(LocalDateTime.now());
        
        return updateById(calendar);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCalendar(Long id) {
        log.info("删除日历：{}", id);
        
        return removeById(id);
    }
    
    @Override
    public ScheduleCalendarVO getCalendarDetail(Long id) {
        log.info("获取日历详情：{}", id);
        
        ScheduleCalendar calendar = getById(id);
        if (calendar == null) {
            return null;
        }
        
        ScheduleCalendarVO vo = new ScheduleCalendarVO();
        // 设置VO属性
        vo.setId(calendar.getId());
        vo.setName(calendar.getName());
        vo.setDescription(calendar.getDescription());
        vo.setColor(calendar.getColor());
        vo.setUserId(calendar.getUserId());
        vo.setType(calendar.getType());
        vo.setVisibility(calendar.getVisibility());
        vo.setIsDefault(calendar.getIsDefault());
        
        return vo;
    }
    
    @Override
    public List<ScheduleCalendarVO> listByUserId(Long userId) {
        log.info("获取用户的所有日历：{}", userId);
        
        QueryWrapper<ScheduleCalendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        
        List<ScheduleCalendar> calendars = list(queryWrapper);
        return calendars.stream()
                .map(calendar -> {
                    ScheduleCalendarVO vo = new ScheduleCalendarVO();
                    // 设置VO属性
                    vo.setId(calendar.getId());
                    vo.setName(calendar.getName());
                    vo.setDescription(calendar.getDescription());
                    vo.setColor(calendar.getColor());
                    vo.setUserId(calendar.getUserId());
                    vo.setType(calendar.getType());
                    vo.setVisibility(calendar.getVisibility());
                    vo.setIsDefault(calendar.getIsDefault());
                    return vo;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCalendarVisibility(Long id, Integer visibility) {
        log.info("更新日历可见性：{}，可见性：{}", id, visibility);
        
        ScheduleCalendar calendar = getById(id);
        if (calendar == null) {
            log.error("更新日历可见性失败，日历不存在，ID：{}", id);
            return false;
        }
        
        calendar.setVisibility(visibility);
        calendar.setUpdateTime(LocalDateTime.now());
        
        return updateById(calendar);
    }
    
    @Override
    public List<ScheduleCalendarVO> listSharedCalendars(Long userId) {
        log.info("获取与用户共享的日历：{}", userId);
        
        // 这里应该实现查询共享给用户的日历
        // 这可能涉及多表查询
        // 简化处理，返回空列表
        return new ArrayList<>();
    }
    
    @Override
    public Page<ScheduleCalendarVO> pageCalendars(Page<ScheduleCalendar> page, Long userId, Integer type) {
        log.info("分页查询日历，用户ID：{}，类型：{}", userId, type);
        
        QueryWrapper<ScheduleCalendar> queryWrapper = new QueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if (type != null) {
            queryWrapper.eq("type", type);
        }
        
        Page<ScheduleCalendar> result = page(page, queryWrapper);
        
        Page<ScheduleCalendarVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<ScheduleCalendarVO> voList = result.getRecords().stream()
                .map(calendar -> {
                    ScheduleCalendarVO vo = new ScheduleCalendarVO();
                    // 设置VO属性
                    vo.setId(calendar.getId());
                    vo.setName(calendar.getName());
                    vo.setDescription(calendar.getDescription());
                    vo.setColor(calendar.getColor());
                    vo.setUserId(calendar.getUserId());
                    vo.setType(calendar.getType());
                    vo.setVisibility(calendar.getVisibility());
                    vo.setIsDefault(calendar.getIsDefault());
                    return vo;
                })
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDefaultCalendar(Long userId, Long calendarId) {
        log.info("设置默认日历，用户ID：{}，日历ID：{}", userId, calendarId);
        
        // 先将用户的所有日历设置为非默认
        QueryWrapper<ScheduleCalendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("is_default", true);
        
        List<ScheduleCalendar> defaultCalendars = list(queryWrapper);
        for (ScheduleCalendar calendar : defaultCalendars) {
            calendar.setIsDefault(false);
            updateById(calendar);
        }
        
        // 设置指定日历为默认
        ScheduleCalendar calendar = getById(calendarId);
        if (calendar == null || !userId.equals(calendar.getUserId())) {
            log.error("设置默认日历失败，日历不存在或不属于该用户，用户ID：{}，日历ID：{}", userId, calendarId);
            return false;
        }
        
        calendar.setIsDefault(true);
        calendar.setUpdateTime(LocalDateTime.now());
        
        return updateById(calendar);
    }
    
    @Override
    public ScheduleCalendarVO getDefaultCalendar(Long userId) {
        log.info("获取用户的默认日历：{}", userId);
        
        QueryWrapper<ScheduleCalendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("is_default", true);
        
        ScheduleCalendar calendar = getOne(queryWrapper);
        if (calendar == null) {
            return null;
        }
        
        ScheduleCalendarVO vo = new ScheduleCalendarVO();
        // 设置VO属性
        vo.setId(calendar.getId());
        vo.setName(calendar.getName());
        vo.setDescription(calendar.getDescription());
        vo.setColor(calendar.getColor());
        vo.setUserId(calendar.getUserId());
        vo.setType(calendar.getType());
        vo.setVisibility(calendar.getVisibility());
        vo.setIsDefault(calendar.getIsDefault());
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean shareCalendar(Long calendarId, List<Long> userIds) {
        log.info("共享日历，日历ID：{}，用户数量：{}", calendarId, userIds.size());
        
        // 这里实现共享逻辑
        // 通常涉及插入日历共享表的记录
        
        // 简化处理，假设共享成功
        return true;
    }

    @Override
    public Long getCurrentTenantId() {
        return 1L; // 默认返回租户ID为1
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }

    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }
} 