package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleTaskRelationDTO;
import com.lawfirm.model.schedule.entity.ScheduleTaskRelation;
import com.lawfirm.model.schedule.vo.ScheduleTaskRelationVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 日程与任务关联数据转换器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleTaskRelationConvert {

    /**
     * DTO转实体
     * 
     * @param dto DTO对象
     * @return 实体对象
     */
    ScheduleTaskRelation toEntity(ScheduleTaskRelationDTO dto);
    
    /**
     * 实体转VO
     * 
     * @param entity 实体对象
     * @return VO对象
     */
    ScheduleTaskRelationVO toVO(ScheduleTaskRelation entity);
    
    /**
     * 更新实体
     * 
     * @param dto DTO对象
     * @param entity 待更新实体
     */
    void updateEntity(ScheduleTaskRelationDTO dto, @MappingTarget ScheduleTaskRelation entity);
    
    /**
     * 批量转换为VO
     * 
     * @param entities 实体列表
     * @return VO列表
     */
    List<ScheduleTaskRelationVO> toVOList(List<ScheduleTaskRelation> entities);
} 