package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleEventDTO;
import com.lawfirm.model.schedule.entity.ScheduleEvent;
import com.lawfirm.model.schedule.vo.ScheduleEventVO;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * 日程事件数据转换器
 */
@Mapper(componentModel = "spring", 
        imports = {TimeConverter.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleEventConvert {

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "id", source = "eventId")
    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "eventType", source = "type")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    ScheduleEvent toEntity(ScheduleEventDTO dto);

    /**
     * 将实体转换为VO
     */
    @Mapping(target = "eventTypeName", expression = "java(getEventTypeName(entity.getEventType()))")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "content", source = "content")
    ScheduleEventVO toVO(ScheduleEvent entity);

    /**
     * 根据DTO更新实体
     */
    @Mapping(target = "id", source = "eventId")
    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "eventType", source = "type")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    void updateEntity(ScheduleEventDTO dto, @MappingTarget ScheduleEvent entity);
    
    /**
     * 获取事件类型名称
     */
    default String getEventTypeName(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        
        switch (typeCode) {
            case 1: return "创建日程";
            case 2: return "更新日程";
            case 3: return "取消日程";
            case 4: return "完成日程";
            case 5: return "添加参与者";
            case 6: return "移除参与者";
            case 7: return "响应邀请";
            case 8: return "会议室预订确认";
            case 9: return "日程提醒";
            case 10: return "其他";
            default: return "未知类型";
        }
    }
    
    /**
     * 将时间戳转换为LocalDateTime
     */
    default LocalDateTime fromTimestamp(Long timestamp) {
        if (timestamp == null || timestamp == 0L) {
            return null;
        }
        return TimeConverter.fromTimestamp(timestamp);
    }
    
    /**
     * 将LocalDateTime转换为时间戳
     */
    default Long toTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return TimeConverter.toTimestamp(dateTime);
    }
} 