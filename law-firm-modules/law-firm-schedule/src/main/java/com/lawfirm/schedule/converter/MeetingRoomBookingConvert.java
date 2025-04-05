package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.MeetingRoomBookingDTO;
import com.lawfirm.model.schedule.entity.MeetingRoomBooking;
import com.lawfirm.model.schedule.vo.MeetingRoomBookingVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 会议室预订数据转换器
 */
@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MeetingRoomBookingConvert {

    /**
     * 将DTO转换为实体
     *
     * @param dto 会议室预订DTO
     * @return 会议室预订实体
     */
    MeetingRoomBooking toEntity(MeetingRoomBookingDTO dto);

    /**
     * 将实体转换为VO
     *
     * @param entity 会议室预订实体
     * @return 会议室预订VO
     */
    @Mapping(target = "bookingTypeName", expression = "java(getBookingTypeName(entity.getBookingType()))")
    @Mapping(target = "statusName", expression = "java(getStatusName(entity.getStatus()))")
    MeetingRoomBookingVO toVO(MeetingRoomBooking entity);

    /**
     * 更新实体
     *
     * @param entity 待更新的实体
     * @param dto DTO
     * @return 更新后的实体
     */
    MeetingRoomBooking updateEntity(@MappingTarget MeetingRoomBooking entity, MeetingRoomBookingDTO dto);

    /**
     * 批量转换为VO
     *
     * @param entities 实体列表
     * @return VO列表
     */
    List<MeetingRoomBookingVO> toVOList(List<MeetingRoomBooking> entities);

    /**
     * 获取预订类型名称
     *
     * @param type 预订类型代码
     * @return 预订类型名称
     */
    default String getBookingTypeName(Integer type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case 1: return "常规会议";
            case 2: return "视频会议";
            case 3: return "培训";
            case 4: return "面试";
            default: return "未知类型";
        }
    }

    /**
     * 获取状态名称
     *
     * @param status 状态代码
     * @return 状态名称
     */
    default String getStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 0: return "待审核";
            case 1: return "已确认";
            case 2: return "已完成";
            case 3: return "已取消";
            default: return "未知状态";
        }
    }
} 