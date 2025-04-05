package com.lawfirm.model.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.schedule.entity.ScheduleCalendar;
import com.lawfirm.model.schedule.vo.ScheduleCalendarVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarDayVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarMonthVO;
import com.lawfirm.model.schedule.vo.ScheduleCalendarWeekVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 日历服务接口
 */
public interface ScheduleCalendarService {

    /**
     * 创建日历
     *
     * @param calendar 日历信息
     * @return 日历ID
     */
    Long createCalendar(ScheduleCalendar calendar);

    /**
     * 更新日历
     *
     * @param id       日历ID
     * @param calendar 日历信息
     * @return 是否成功
     */
    boolean updateCalendar(Long id, ScheduleCalendar calendar);

    /**
     * 删除日历
     *
     * @param id 日历ID
     * @return 是否成功
     */
    boolean deleteCalendar(Long id);

    /**
     * 获取日历详情
     *
     * @param id 日历ID
     * @return 日历详情
     */
    ScheduleCalendarVO getCalendarDetail(Long id);

    /**
     * 获取用户的所有日历
     *
     * @param userId 用户ID
     * @return 日历列表
     */
    List<ScheduleCalendarVO> listByUserId(Long userId);

    /**
     * 更新日历可见性
     *
     * @param id         日历ID
     * @param visibility 可见性
     * @return 是否成功
     */
    boolean updateCalendarVisibility(Long id, Integer visibility);

    /**
     * 获取与用户共享的日历
     *
     * @param userId 用户ID
     * @return 日历列表
     */
    List<ScheduleCalendarVO> listSharedCalendars(Long userId);

    /**
     * 分页查询日历
     *
     * @param page   分页参数
     * @param userId 用户ID
     * @param type   日历类型
     * @return 分页结果
     */
    Page<ScheduleCalendarVO> pageCalendars(Page<ScheduleCalendar> page, Long userId, Integer type);

    /**
     * 设置默认日历
     *
     * @param userId    用户ID
     * @param calendarId 日历ID
     * @return 是否成功
     */
    boolean setDefaultCalendar(Long userId, Long calendarId);

    /**
     * 获取用户的默认日历
     *
     * @param userId 用户ID
     * @return 日历详情
     */
    ScheduleCalendarVO getDefaultCalendar(Long userId);

    /**
     * 共享日历给用户
     *
     * @param calendarId 日历ID
     * @param userIds    用户ID列表
     * @return 是否成功
     */
    boolean shareCalendar(Long calendarId, List<Long> userIds);

    /**
     * 取消与用户共享日历
     *
     * @param calendarId 日历ID
     * @param userId     用户ID
     * @return 是否成功
     */
    boolean unshareCalendar(Long calendarId, Long userId);

    /**
     * 获取日视图数据
     *
     * @param userId 用户ID
     * @param date 日期
     * @param includeShared 是否包含共享日程
     * @return 日视图数据
     */
    ScheduleCalendarDayVO getDayView(Long userId, LocalDate date, boolean includeShared);
    
    /**
     * 获取周视图数据
     *
     * @param userId 用户ID
     * @param weekStartDate 周开始日期
     * @param includeShared 是否包含共享日程
     * @return 周视图数据
     */
    ScheduleCalendarWeekVO getWeekView(Long userId, LocalDate weekStartDate, boolean includeShared);
    
    /**
     * 获取月视图数据
     *
     * @param userId 用户ID
     * @param year 年份
     * @param month 月份(1-12)
     * @param includeShared 是否包含共享日程
     * @return 月视图数据
     */
    ScheduleCalendarMonthVO getMonthView(Long userId, int year, int month, boolean includeShared);
    
    /**
     * 获取用户可见的日程所有者列表（可用于日历切换）
     *
     * @param userId 用户ID
     * @return 所有者列表
     */
    List<Long> getVisibleCalendarOwners(Long userId);
    
    /**
     * 获取用户特定日期的日程统计
     *
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据（日期 -> 数量）
     */
    java.util.Map<LocalDate, Integer> getScheduleStatistics(Long userId, LocalDate startDate, LocalDate endDate);
} 