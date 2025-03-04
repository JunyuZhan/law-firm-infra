package com.lawfirm.core.audit.converter;

import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.entity.audit.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 审计日志转换器
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditLogConverter {

    /**
     * 实体转DTO
     */
    AuditLogDTO toDTO(AuditLog entity);

    /**
     * DTO转实体
     */
    AuditLog toEntity(AuditLogDTO dto);
} 