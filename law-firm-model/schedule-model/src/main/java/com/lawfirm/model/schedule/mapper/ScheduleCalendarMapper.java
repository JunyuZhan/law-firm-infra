package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ScheduleCalendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 日程日历Mapper接口
 */
@Mapper
public interface ScheduleCalendarMapper extends BaseMapper<ScheduleCalendar> {
    
    /**
     * 获取用户的默认日历
     *
     * @param userId 用户ID
     * @return 默认日历
     */
    @Select("SELECT * FROM schedule_calendar WHERE user_id = #{userId} AND is_default = 1 LIMIT 1")
    ScheduleCalendar getDefaultCalendar(@Param("userId") Long userId);
    
    /**
     * 获取用户的所有日历
     *
     * @param userId 用户ID
     * @return 用户的所有日历
     */
    @Select("SELECT * FROM schedule_calendar WHERE user_id = #{userId}")
    java.util.List<ScheduleCalendar> getUserCalendars(@Param("userId") Long userId);
    
    /**
     * 通过类型获取用户日历
     *
     * @param userId 用户ID
     * @param type 日历类型
     * @return 日历列表
     */
    @Select("SELECT * FROM schedule_calendar WHERE user_id = #{userId} AND type = #{type}")
    java.util.List<ScheduleCalendar> getUserCalendarsByType(@Param("userId") Long userId, @Param("type") Integer type);
} 