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
    @Mapping(target = "reminderType", expression = "java(dto.getReminderType() != null ? dto.getReminderType().ordinal() : null)")
    @Mapping(target = "reminderStatus", expression = "java(dto.getReminderStatus() != null ? dto.getReminderStatus().ordinal() : null)")
    ScheduleReminder toEntity(ScheduleReminderDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "reminderTypeName", expression = "java(getReminderTypeName(entity.getReminderType()))")
    @Mapping(target = "reminderStatusName", expression = "java(getReminderStatusName(entity.getReminderStatus()))")
    ScheduleReminderVO toVO(ScheduleReminder entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "reminderType", expression = "java(dto.getReminderType() != null ? dto.getReminderType().ordinal() : entity.getReminderType())")
    @Mapping(target = "reminderStatus", expression = "java(dto.getReminderStatus() != null ? dto.getReminderStatus().ordinal() : entity.getReminderStatus())")
    ScheduleReminder updateEntity(@MappingTarget ScheduleReminder entity, ScheduleReminderDTO dto);
    
    /**
     * 获取提醒类型名称
     */
    default String getReminderTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (ReminderType type : ReminderType.values()) {
            if (type.ordinal() == typeCode) {
                return type.name();
            }
        }
        return null;
    }
    
    /**
     * 获取提醒状态名称
     */
    default String getReminderStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (ReminderStatus status : ReminderStatus.values()) {
            if (status.ordinal() == statusCode) {
                return status.name();
            }
        }
        return null;
    }
} 