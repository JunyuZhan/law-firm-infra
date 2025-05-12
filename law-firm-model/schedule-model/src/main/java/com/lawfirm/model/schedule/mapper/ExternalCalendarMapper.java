package com.lawfirm.model.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.schedule.entity.ExternalCalendar;
import org.apache.ibatis.annotations.Mapper;

/**
 * 外部日历Mapper接口
 */
@Mapper
public interface ExternalCalendarMapper extends BaseMapper<ExternalCalendar> {
    
} 