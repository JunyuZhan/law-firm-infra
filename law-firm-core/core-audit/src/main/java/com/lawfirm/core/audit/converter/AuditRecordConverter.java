package com.lawfirm.core.audit.converter;

import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 审计记录转换器
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditRecordConverter {

    /**
     * 实体转DTO
     */
    AuditRecordDTO toDTO(AuditRecord entity);

    /**
     * DTO转实体
     */
    AuditRecord toEntity(AuditRecordDTO dto);
} 