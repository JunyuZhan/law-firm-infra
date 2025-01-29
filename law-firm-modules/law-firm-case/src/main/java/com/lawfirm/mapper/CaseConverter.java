package com.lawfirm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.base.enums.StatusEnum;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaseConverter {

    @Mapping(target = "caseNumber", ignore = true)
    @Mapping(target = "filingTime", ignore = true)
    @Mapping(target = "caseType", source = "caseType")
    @Mapping(target = "caseStatus", source = "caseStatus")
    @Mapping(target = "caseProgress", source = "caseProgress")
    @Mapping(target = "caseHandleType", source = "caseHandleType")
    @Mapping(target = "caseDifficulty", source = "caseDifficulty")
    @Mapping(target = "caseImportance", source = "caseImportance")
    @Mapping(target = "casePriority", source = "casePriority")
    @Mapping(target = "caseFeeType", source = "caseFeeType")
    @Mapping(target = "caseSource", source = "caseSource")
    @Mapping(target = "lawyer", source = "lawyer")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "lawFirmId", source = "lawFirmId")
    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "departmentId", source = "departmentId")
    @Mapping(target = "isMajor", source = "isMajor")
    @Mapping(target = "hasConflict", source = "hasConflict")
    @Mapping(target = "courtName", source = "courtName")
    @Mapping(target = "judgeName", source = "judgeName")
    @Mapping(target = "courtCaseNumber", source = "courtCaseNumber")
    Case toEntity(CaseCreateDTO dto);

    @Mapping(target = "caseNumber", ignore = true)
    @Mapping(target = "filingTime", ignore = true)
    @Mapping(target = "caseType", source = "caseType")
    @Mapping(target = "caseStatus", source = "caseStatus")
    @Mapping(target = "caseProgress", source = "caseProgress")
    @Mapping(target = "caseHandleType", source = "caseHandleType")
    @Mapping(target = "caseDifficulty", source = "caseDifficulty")
    @Mapping(target = "caseImportance", source = "caseImportance")
    @Mapping(target = "casePriority", source = "casePriority")
    @Mapping(target = "caseFeeType", source = "caseFeeType")
    @Mapping(target = "caseSource", source = "caseSource")
    @Mapping(target = "lawyer", source = "lawyer")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "lawFirmId", source = "lawFirmId")
    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "departmentId", source = "departmentId")
    @Mapping(target = "isMajor", source = "isMajor")
    @Mapping(target = "hasConflict", source = "hasConflict")
    @Mapping(target = "courtName", source = "courtName")
    @Mapping(target = "judgeName", source = "judgeName")
    @Mapping(target = "courtCaseNumber", source = "courtCaseNumber")
    void updateEntity(CaseUpdateDTO dto, @MappingTarget Case entity);

    @Mapping(target = "caseType", source = "caseType")
    @Mapping(target = "caseStatus", source = "caseStatus")
    @Mapping(target = "caseProgress", source = "caseProgress")
    @Mapping(target = "caseHandleType", source = "caseHandleType")
    @Mapping(target = "caseDifficulty", source = "caseDifficulty")
    @Mapping(target = "caseImportance", source = "caseImportance")
    @Mapping(target = "casePriority", source = "casePriority")
    @Mapping(target = "caseFeeType", source = "caseFeeType")
    @Mapping(target = "caseSource", source = "caseSource")
    @Mapping(target = "lawyerName", source = "lawyer")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "lawFirmId", source = "lawFirmId")
    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "departmentId", source = "departmentId")
    @Mapping(target = "isMajor", source = "isMajor")
    @Mapping(target = "hasConflict", source = "hasConflict")
    @Mapping(target = "courtName", source = "courtName")
    @Mapping(target = "judgeName", source = "judgeName")
    @Mapping(target = "courtCaseNumber", source = "courtCaseNumber")
    @Mapping(target = "lawyer", ignore = true)
    @Mapping(target = "lawFirm", ignore = true)
    CaseDetailVO toDetailVO(Case entity);

    @ValueMapping(source = "DRAFT", target = "ENABLED")
    @ValueMapping(source = "PENDING", target = "ENABLED")
    @ValueMapping(source = "IN_PROGRESS", target = "ENABLED") 
    @ValueMapping(source = "COMPLETED", target = "ENABLED")
    @ValueMapping(source = "ARCHIVED", target = "LOCKED")
    @ValueMapping(source = "SUSPENDED", target = "DISABLED")
    @ValueMapping(source = "CANCELLED", target = "DELETED")
    @ValueMapping(source = "CLOSED", target = "EXPIRED")
    @ValueMapping(source = "REOPENED", target = "ENABLED")
    StatusEnum toStatusEnum(CaseStatusEnum status);
    
    @ValueMapping(source = "ENABLED", target = "IN_PROGRESS")
    @ValueMapping(source = "DISABLED", target = "SUSPENDED")
    @ValueMapping(source = "DELETED", target = "CANCELLED")
    @ValueMapping(source = "LOCKED", target = "ARCHIVED")
    @ValueMapping(source = "EXPIRED", target = "CLOSED")
    CaseStatusEnum toStatusEnum(StatusEnum status);
} 
