package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.MeetingRoomDTO;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import com.lawfirm.model.schedule.vo.MeetingRoomVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * 会议室数据转换器
 */
@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MeetingRoomConvert {
    
    /**
     * 将DTO转换为实体
     *
     * @param dto 会议室DTO
     * @return 会议室实体
     */
    MeetingRoom toEntity(MeetingRoomDTO dto);
    
    /**
     * 将实体转换为VO
     *
     * @param entity 会议室实体
     * @return 会议室VO
     */
    MeetingRoomVO toVO(MeetingRoom entity);
    
    /**
     * 更新实体
     *
     * @param dto 会议室DTO
     * @param entity 待更新的会议室实体
     */
    void updateEntity(MeetingRoomDTO dto, @MappingTarget MeetingRoom entity);
    
    /**
     * 将DTO列表转换为实体列表
     *
     * @param dtoList DTO列表
     * @return 实体列表
     */
    java.util.List<MeetingRoom> toEntityList(java.util.List<MeetingRoomDTO> dtoList);
    
    /**
     * 将实体列表转换为VO列表
     *
     * @param entityList 实体列表
     * @return VO列表
     */
    java.util.List<MeetingRoomVO> toVOList(java.util.List<MeetingRoom> entityList);
} 