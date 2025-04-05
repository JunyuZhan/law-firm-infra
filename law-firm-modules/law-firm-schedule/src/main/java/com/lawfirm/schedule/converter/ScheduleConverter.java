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
public interface ScheduleConverter {

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus().getCode() : null)")
    @Mapping(target = "type", expression = "java(dto.getType() != null ? dto.getType().getCode() : null)")
    @Mapping(target = "priority", expression = "java(dto.getPriority() != null ? dto.getPriority().getCode() : null)")
    Schedule toEntity(ScheduleDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "status", expression = "java(mapIntegerToScheduleStatus(entity.getStatus()))")
    @Mapping(target = "type", expression = "java(mapIntegerToScheduleType(entity.getType()))")
    @Mapping(target = "priority", expression = "java(mapIntegerToPriorityLevel(entity.getPriority()))")
    @Mapping(target = "statusDesc", expression = "java(getStatusName(entity.getStatus()))")
    @Mapping(target = "typeDesc", expression = "java(getTypeName(entity.getType()))")
    @Mapping(target = "priorityDesc", expression = "java(getPriorityName(entity.getPriority()))")
    ScheduleVO toVO(Schedule entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus().getCode() : entity.getStatus())")
    @Mapping(target = "type", expression = "java(dto.getType() != null ? dto.getType().getCode() : entity.getType())")
    @Mapping(target = "priority", expression = "java(dto.getPriority() != null ? dto.getPriority().getCode() : entity.getPriority())")
    void updateEntity(@MappingTarget Schedule entity, ScheduleDTO dto);
    
    /**
     * 获取状态名称
     */
    default String getStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ScheduleStatus status = ScheduleStatus.getByCode(statusCode);
        return status != null ? status.name() : null;
    }
    
    /**
     * 获取类型名称
     */
    default String getTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ScheduleType type = ScheduleType.getByCode(typeCode);
        return type != null ? type.name() : null;
    }
    
    /**
     * 获取优先级名称
     */
    default String getPriorityName(Integer priorityCode) {
        if (priorityCode == null) {
            return null;
        }
        PriorityLevel priority = PriorityLevel.getByCode(priorityCode);
        return priority != null ? priority.name() : null;
    }
    
    /**
     * Integer转换为ScheduleStatus
     */
    default ScheduleStatus mapIntegerToScheduleStatus(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ScheduleStatus status = ScheduleStatus.getByCode(statusCode);
        return status != null ? status : ScheduleStatus.PLANNED; // 默认返回计划中状态
    }
    
    /**
     * Integer转换为ScheduleType
     */
    default ScheduleType mapIntegerToScheduleType(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ScheduleType type = ScheduleType.getByCode(typeCode);
        return type != null ? type : ScheduleType.MEETING; // 默认返回会议类型
    }
    
    /**
     * Integer转换为PriorityLevel
     */
    default PriorityLevel mapIntegerToPriorityLevel(Integer priorityCode) {
        if (priorityCode == null) {
            return null;
        }
        PriorityLevel priority = PriorityLevel.getByCode(priorityCode);
        return priority != null ? priority : PriorityLevel.MEDIUM; // 默认返回中等优先级
    }
} 