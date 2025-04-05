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
    @Mapping(target = "participantType", expression = "java(dto.getParticipantType() != null ? dto.getParticipantType().getCode() : null)")
    @Mapping(target = "responseStatus", expression = "java(dto.getResponseStatus() != null ? dto.getResponseStatus().getCode() : null)")
    ScheduleParticipant toEntity(ScheduleParticipantDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "participantType", expression = "java(mapIntegerToParticipantType(entity.getParticipantType()))")
    @Mapping(target = "responseStatus", expression = "java(mapIntegerToResponseStatus(entity.getResponseStatus()))")
    @Mapping(target = "participantTypeDesc", expression = "java(getParticipantTypeName(entity.getParticipantType()))")
    @Mapping(target = "responseStatusDesc", expression = "java(getResponseStatusName(entity.getResponseStatus()))")
    ScheduleParticipantVO toVO(ScheduleParticipant entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "participantType", expression = "java(dto.getParticipantType() != null ? dto.getParticipantType().getCode() : entity.getParticipantType())")
    @Mapping(target = "responseStatus", expression = "java(dto.getResponseStatus() != null ? dto.getResponseStatus().getCode() : entity.getResponseStatus())")
    void updateEntity(@MappingTarget ScheduleParticipant entity, ScheduleParticipantDTO dto);
    
    /**
     * 获取参与者类型名称
     */
    default String getParticipantTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ParticipantType type = ParticipantType.getByCode(typeCode);
        return type != null ? type.name() : null;
    }
    
    /**
     * 获取响应状态名称
     */
    default String getResponseStatusName(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ResponseStatus status = ResponseStatus.getByCode(statusCode);
        return status != null ? status.name() : null;
    }
    
    /**
     * Integer转换为ParticipantType
     */
    default ParticipantType mapIntegerToParticipantType(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ParticipantType type = ParticipantType.getByCode(typeCode);
        return type != null ? type : ParticipantType.REQUIRED; // 默认返回必要参与者
    }
    
    /**
     * Integer转换为ResponseStatus
     */
    default ResponseStatus mapIntegerToResponseStatus(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        ResponseStatus status = ResponseStatus.getByCode(statusCode);
        return status != null ? status : ResponseStatus.PENDING; // 默认返回未回复状态
    }
} 