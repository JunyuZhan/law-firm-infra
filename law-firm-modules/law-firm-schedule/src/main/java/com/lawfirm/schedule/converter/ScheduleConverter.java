package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 日程数据转换器
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScheduleConverter {

    /**
     * DTO转实体
     */
    Schedule toEntity(ScheduleDTO dto);

    /**
     * 实体转VO
     */
    ScheduleVO toVO(Schedule entity);

    /**
     * 更新实体
     */
    Schedule updateEntity(@MappingTarget Schedule entity, ScheduleDTO dto);
} 