package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleReminderDTO;
import com.lawfirm.model.schedule.entity.ScheduleReminder;
import com.lawfirm.model.schedule.entity.enums.ReminderType;
import com.lawfirm.model.schedule.entity.enums.ReminderStatus;
import com.lawfirm.model.schedule.vo.ScheduleReminderVO;
import org.mapstruct.*;

/**
 * 日程提醒数据转换器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleReminderConvert {

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "reminderType", expression = "java(dto.getReminderType() != null ? dto.getReminderType().getCode() : null)")
    @Mapping(target = "reminderStatus", expression = "java(dto.getReminderStatus() != null ? dto.getReminderStatus().getCode() : null)")
    ScheduleReminder toEntity(ScheduleReminderDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "reminderType", expression = "java(mapIntegerToReminderType(entity.getReminderType()))")
    @Mapping(target = "reminderStatus", expression = "java(mapIntegerToReminderStatus(entity.getReminderStatus()))")
    @Mapping(target = "reminderTypeDesc", expression = "java(getReminderTypeName(entity.getReminderType()))")
    @Mapping(target = "reminderStatusDesc", expression = "java(getReminderStatusName(entity.getReminderStatus()))")
    ScheduleReminderVO toVO(ScheduleReminder entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "reminderType", expression = "java(dto.getReminderType() != null ? dto.getReminderType().getCode() : entity.getReminderType())")
    @Mapping(target = "reminderStatus", expression = "java(dto.getReminderStatus() != null ? dto.getReminderStatus().getCode() : entity.getReminderStatus())")
    void updateEntity(@MappingTarget ScheduleReminder entity, ScheduleReminderDTO dto);
    
    /**
     * 获取提醒类型名称
     */
    default String getReminderTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ReminderType type = ReminderType.getByCode(typeCode);
        return type != null ? type.name() : null;
    }
    
    /**
     * 获取提醒状态名称
     */
    default String getReminderStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ReminderStatus status = ReminderStatus.getByCode(statusCode);
        return status != null ? status.name() : null;
    }
    
    /**
     * Integer转换为ReminderType
     */
    default ReminderType mapIntegerToReminderType(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ReminderType type = ReminderType.getByCode(typeCode);
        return type != null ? type : ReminderType.SYSTEM; // 默认返回系统提醒
    }
    
    /**
     * Integer转换为ReminderStatus
     */
    default ReminderStatus mapIntegerToReminderStatus(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ReminderStatus status = ReminderStatus.getByCode(statusCode);
        return status != null ? status : ReminderStatus.PENDING; // 默认返回未处理状态
    }
} 