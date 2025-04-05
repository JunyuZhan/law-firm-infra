package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleParticipantDTO;
import com.lawfirm.model.schedule.entity.ScheduleParticipant;
import com.lawfirm.model.schedule.entity.enums.ParticipantType;
import com.lawfirm.model.schedule.entity.enums.ResponseStatus;
import com.lawfirm.model.schedule.vo.ScheduleParticipantVO;
import org.mapstruct.*;

/**
 * 日程参与者数据转换器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleParticipantConvert {

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "participantType", expression = "java(dto.getParticipantType() != null ? dto.getParticipantType().ordinal() : null)")
    @Mapping(target = "responseStatus", expression = "java(dto.getResponseStatus() != null ? dto.getResponseStatus().ordinal() : null)")
    ScheduleParticipant toEntity(ScheduleParticipantDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "participantTypeName", expression = "java(getParticipantTypeName(entity.getParticipantType()))")
    @Mapping(target = "responseStatusName", expression = "java(getResponseStatusName(entity.getResponseStatus()))")
    ScheduleParticipantVO toVO(ScheduleParticipant entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "participantType", expression = "java(dto.getParticipantType() != null ? dto.getParticipantType().ordinal() : entity.getParticipantType())")
    @Mapping(target = "responseStatus", expression = "java(dto.getResponseStatus() != null ? dto.getResponseStatus().ordinal() : entity.getResponseStatus())")
    ScheduleParticipant updateEntity(@MappingTarget ScheduleParticipant entity, ScheduleParticipantDTO dto);
    
    /**
     * 获取参与者类型名称
     */
    default String getParticipantTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (ParticipantType type : ParticipantType.values()) {
            if (type.ordinal() == typeCode) {
                return type.name();
            }
        }
        return null;
    }
    
    /**
     * 获取响应状态名称
     */
    default String getResponseStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (ResponseStatus status : ResponseStatus.values()) {
            if (status.ordinal() == statusCode) {
                return status.name();
            }
        }
        return null;
    }
} 