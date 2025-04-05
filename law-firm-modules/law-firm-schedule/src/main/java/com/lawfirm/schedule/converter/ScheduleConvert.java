package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleDTO;
import com.lawfirm.model.schedule.entity.Schedule;
import com.lawfirm.model.schedule.entity.enums.PriorityLevel;
import com.lawfirm.model.schedule.entity.enums.ScheduleStatus;
import com.lawfirm.model.schedule.entity.enums.ScheduleType;
import com.lawfirm.model.schedule.vo.ScheduleVO;
import org.mapstruct.*;

/**
 * 日程数据转换器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleConvert {

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus().ordinal() : null)")
    @Mapping(target = "type", expression = "java(dto.getType() != null ? dto.getType().ordinal() : null)")
    @Mapping(target = "priority", expression = "java(dto.getPriority() != null ? dto.getPriority().ordinal() : null)")
    Schedule toEntity(ScheduleDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "statusName", expression = "java(getStatusName(entity.getStatus()))")
    @Mapping(target = "typeName", expression = "java(getTypeName(entity.getType()))")
    @Mapping(target = "priorityName", expression = "java(getPriorityName(entity.getPriority()))")
    ScheduleVO toVO(Schedule entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus().ordinal() : entity.getStatus())")
    @Mapping(target = "type", expression = "java(dto.getType() != null ? dto.getType().ordinal() : entity.getType())")
    @Mapping(target = "priority", expression = "java(dto.getPriority() != null ? dto.getPriority().ordinal() : entity.getPriority())")
    Schedule updateEntity(@MappingTarget Schedule entity, ScheduleDTO dto);
    
    /**
     * 获取状态名称
     */
    default String getStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (ScheduleStatus status : ScheduleStatus.values()) {
            if (status.ordinal() == statusCode) {
                return status.name();
            }
        }
        return null;
    }
    
    /**
     * 获取类型名称
     */
    default String getTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (ScheduleType type : ScheduleType.values()) {
            if (type.ordinal() == typeCode) {
                return type.name();
            }
        }
        return null;
    }
    
    /**
     * 获取优先级名称
     */
    default String getPriorityName(Integer priorityCode) {
        if (priorityCode == null) {
            return null;
        }
        for (PriorityLevel priority : PriorityLevel.values()) {
            if (priority.ordinal() == priorityCode) {
                return priority.name();
            }
        }
        return null;
    }
} 