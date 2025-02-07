package com.lawfirm.schedule.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.schedule.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日程数据访问层
 */
@Mapper
public interface ScheduleRepository extends BaseMapper<Schedule> {
} 