package com.lawfirm.personnel.converter;

import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.personnel.dto.request.EmployeeAddRequest;
import com.lawfirm.personnel.dto.request.EmployeeUpdateRequest;
import com.lawfirm.personnel.dto.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeConverter {

    EmployeeConverter INSTANCE = Mappers.getMapper(EmployeeConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Employee toEntity(EmployeeAddRequest request);

    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget Employee employee, EmployeeUpdateRequest request);

    @Mapping(target = "employeeTypeName", source = "employeeType.description")
    @Mapping(target = "employmentStatusName", expression = "java(employee.getEmploymentStatus().name())")
    EmployeeResponse toResponse(Employee employee);
} 