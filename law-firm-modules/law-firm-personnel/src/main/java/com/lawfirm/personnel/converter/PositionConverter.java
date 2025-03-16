package com.lawfirm.personnel.converter;

import com.lawfirm.model.organization.dto.position.PositionDTO;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.vo.position.PositionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * 职位对象转换器
 * 用于DTO、VO和Entity之间的转换
 */
@Mapper(
    componentModel = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PositionConverter {

    /**
     * PositionDTO转Position实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Position toEntity(PositionDTO positionDTO);

    /**
     * Position实体更新
     */
    Position updateEntity(@MappingTarget Position position, PositionDTO positionDTO);

    /**
     * Position实体转DTO
     */
    PositionDTO toDTO(Position position);

    /**
     * Position实体转VO
     */
    PositionVO toVO(Position position);
} 