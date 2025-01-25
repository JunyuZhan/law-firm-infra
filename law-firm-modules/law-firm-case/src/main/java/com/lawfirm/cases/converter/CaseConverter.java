package com.lawfirm.cases.converter;

import com.lawfirm.cases.model.dto.CaseCreateDTO;
import com.lawfirm.cases.model.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.base.enums.StatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CaseConverter {

    CaseConverter INSTANCE = Mappers.getMapper(CaseConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "statusEnum", ignore = true)
    @Mapping(target = "filingTime", ignore = true)
    @Mapping(target = "closingTime", ignore = true)
    Case toEntity(CaseCreateDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "statusEnum", ignore = true)
    @Mapping(target = "caseNumber", ignore = true)
    @Mapping(target = "filingTime", ignore = true)
    @Mapping(target = "closingTime", ignore = true)
    void updateEntity(@MappingTarget Case entity, CaseUpdateDTO updateDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "caseNumber", source = "caseNo")
    @Mapping(target = "caseName", source = "caseName")
    @Mapping(target = "caseType", source = "caseType")
    @Mapping(target = "caseStatus", source = "caseStatus")
    @Mapping(target = "lawyer", source = "lawyer")
    @Mapping(target = "remark", source = "remark")
    CaseDetailVO toDetailVO(Case entity);

    @ValueMapping(source = "ENABLED", target = "PENDING")
    @ValueMapping(source = "DISABLED", target = "COMPLETED")
    @ValueMapping(source = "DELETED", target = "CANCELLED")
    @ValueMapping(source = "LOCKED", target = "ARCHIVED")
    @ValueMapping(source = "EXPIRED", target = "ARCHIVED")
    CaseStatusEnum toStatusEnum(StatusEnum status);

    @ValueMapping(source = "PENDING", target = "ENABLED")
    @ValueMapping(source = "IN_PROGRESS", target = "ENABLED")
    @ValueMapping(source = "COMPLETED", target = "DISABLED")
    @ValueMapping(source = "ARCHIVED", target = "DISABLED")
    @ValueMapping(source = "CANCELLED", target = "DELETED")
    StatusEnum toStatusEnum(CaseStatusEnum status);
} 