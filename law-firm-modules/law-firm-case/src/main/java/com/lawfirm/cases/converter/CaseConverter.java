package com.lawfirm.cases.converter;

import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CaseConverter {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    Case toEntity(CaseCreateDTO createDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "caseNumber", ignore = true)
    void updateEntity(@MappingTarget Case entity, CaseUpdateDTO updateDTO);
    
    @Mapping(source = "lawyer", target = "lawyer", qualifiedByName = "toLawyerInfo")
    @Mapping(source = "lawyer", target = "lawyerName")
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "lawFirm", ignore = true)
    @Mapping(target = "teamMembers", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "feeAmount", ignore = true)
    @Mapping(target = "conflictReason", ignore = true)
    CaseDetailVO toDetailVO(Case entity);
    
    @Named("toLawyerInfo")
    default CaseDetailVO.LawyerInfo toLawyerInfo(String lawyer) {
        if (lawyer == null) {
            return null;
        }
        CaseDetailVO.LawyerInfo lawyerInfo = new CaseDetailVO.LawyerInfo();
        lawyerInfo.setName(lawyer);
        return lawyerInfo;
    }
} 