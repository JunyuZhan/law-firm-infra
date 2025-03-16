package com.lawfirm.personnel.converter;

import com.lawfirm.model.organization.dto.department.DepartmentDTO;
import com.lawfirm.model.organization.entity.department.Department;
import com.lawfirm.model.organization.vo.department.DepartmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * 组织对象转换器
 * 用于部门DTO、VO和Entity之间的转换
 */
@Mapper(
    componentModel = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrganizationConverter {

    /**
     * DepartmentDTO转Department实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "effectiveStartDate", ignore = true)
    @Mapping(target = "effectiveEndDate", ignore = true)
    @Mapping(target = "isTemporary", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

    /**
     * Department实体更新
     */
    Department updateEntity(@MappingTarget Department department, DepartmentDTO departmentDTO);

    /**
     * Department实体转DTO
     */
    DepartmentDTO toDTO(Department department);

    /**
     * Department实体转VO
     */
    DepartmentVO toVO(Department department);
} 