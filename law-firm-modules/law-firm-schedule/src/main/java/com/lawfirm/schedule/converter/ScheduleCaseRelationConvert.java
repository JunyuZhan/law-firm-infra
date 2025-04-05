package com.lawfirm.schedule.converter;

import com.lawfirm.model.schedule.dto.ScheduleCaseRelationDTO;
import com.lawfirm.model.schedule.entity.ScheduleCaseRelation;
import com.lawfirm.model.schedule.vo.ScheduleCaseRelationVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 日程与案件关联数据转换器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleCaseRelationConvert {

    /**
     * DTO转实体
     * 
     * @param dto DTO对象
     * @return 实体对象
     */
    ScheduleCaseRelation toEntity(ScheduleCaseRelationDTO dto);
    
    /**
     * 实体转VO
     * 
     * @param entity 实体对象
     * @return VO对象
     */
    ScheduleCaseRelationVO toVO(ScheduleCaseRelation entity);
    
    /**
     * 更新实体
     * 
     * @param dto DTO对象
     * @param entity 待更新实体
     */
    void updateEntity(ScheduleCaseRelationDTO dto, @MappingTarget ScheduleCaseRelation entity);
    
    /**
     * 批量转换为VO
     * 
     * @param entities 实体列表
     * @return VO列表
     */
    List<ScheduleCaseRelationVO> toVOList(List<ScheduleCaseRelation> entities);
} 