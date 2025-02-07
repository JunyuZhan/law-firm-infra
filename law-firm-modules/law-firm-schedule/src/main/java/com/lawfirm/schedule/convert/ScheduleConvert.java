package com.lawfirm.schedule.convert;

import com.lawfirm.schedule.entity.Schedule;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 日程对象转换器
 */
@Mapper
public interface ScheduleConvert {

    ScheduleConvert INSTANCE = Mappers.getMapper(ScheduleConvert.class);

    /**
     * DTO 转 Entity
     */
    Schedule toEntity(ScheduleDTO dto);

    /**
     * Entity 转 VO
     */
    ScheduleVO toVO(Schedule entity);

    /**
     * DTO 转 VO
     */
    ScheduleVO dtoToVO(ScheduleDTO dto);
} 