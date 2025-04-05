package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.MeetingRoomDTO;
import com.lawfirm.model.schedule.entity.MeetingRoom;
import com.lawfirm.model.schedule.vo.MeetingRoomVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @Mapping(source = "facilities", target = "facilities", qualifiedByName = "facilitiesListToString")
    MeetingRoom toEntity(MeetingRoomDTO dto);
    
    /**
     * 将实体转换为VO
     *
     * @param entity 会议室实体
     * @return 会议室VO
     */
    @Mapping(source = "facilities", target = "facilities", qualifiedByName = "facilitiesStringToList")
    MeetingRoomVO toVO(MeetingRoom entity);
    
    /**
     * 更新实体
     *
     * @param dto 会议室DTO
     * @param entity 待更新的会议室实体
     */
    @Mapping(source = "facilities", target = "facilities", qualifiedByName = "facilitiesListToString")
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
    
    /**
     * 将设施列表转换为逗号分隔的字符串
     *
     * @param facilities 设施列表
     * @return 逗号分隔的设施字符串
     */
    @Named("facilitiesListToString")
    default String facilitiesListToString(List<String> facilities) {
        if (facilities == null || facilities.isEmpty()) {
            return "";
        }
        return facilities.stream().collect(Collectors.joining(","));
    }
    
    /**
     * 将逗号分隔的设施字符串转换为设施列表
     *
     * @param facilities 逗号分隔的设施字符串
     * @return 设施列表
     */
    @Named("facilitiesStringToList")
    default List<String> facilitiesStringToList(String facilities) {
        if (facilities == null || facilities.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(facilities.split(","));
    }
} 