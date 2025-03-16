package com.lawfirm.personnel.converter;

import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.model.organization.enums.CenterTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * 员工对象转换器
 * 用于DTO、VO和Entity之间的转换
 */
@Mapper(
    componentModel = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EmployeeConverter {

    /**
     * CreateEmployeeDTO转Employee实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "tenantCode", ignore = true)
    @Mapping(target = "personCode", ignore = true)
    @Mapping(target = "isPartner", ignore = true)
    @Mapping(target = "staffFunction", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "resignDate", ignore = true)
    @Mapping(target = "contractStatus", ignore = true)
    @Mapping(target = "currentContractId", ignore = true)
    @Mapping(target = "resignReason", ignore = true)
    Employee toEntity(CreateEmployeeDTO createDTO);

    /**
     * UpdateEmployeeDTO更新Employee实体
     */
    Employee updateEntity(@MappingTarget Employee employee, UpdateEmployeeDTO updateDTO);

    /**
     * Employee实体转EmployeeDTO
     */
    EmployeeDTO toDTO(Employee employee);

    /**
     * EmployeeDTO转EmployeeVO
     */
    EmployeeVO toVO(EmployeeDTO dto);

    /**
     * Employee实体转EmployeeVO
     */
    EmployeeVO toVO(Employee employee);
    
    /**
     * 将Integer类型转换为CenterTypeEnum
     */
    default CenterTypeEnum map(Integer value) {
        if (value == null) {
            return null;
        }
        for (CenterTypeEnum type : CenterTypeEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 将CenterTypeEnum转换为Integer
     */
    default Integer map(CenterTypeEnum value) {
        return value == null ? null : value.getValue();
    }
} 