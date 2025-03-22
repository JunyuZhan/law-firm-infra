package com.lawfirm.model.log.converter;

import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.entity.audit.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * 审计日志转换器
 * <p>
 * 该接口用于审计日志实体与DTO之间的相互转换。
 * 使用MapStruct框架自动生成实现类，提供实体与DTO之间的无缝转换。
 * 属于日志模型模块，处理审计日志的数据转换逻辑。
 * </p>
 *
 * @author lawfirm-dev
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, implementationName = "ModelAuditLogConverterImpl")
@Component("modelAuditLogConverter")
public interface AuditLogConverter {

    /**
     * 将审计日志实体转换为DTO
     *
     * @param entity 审计日志实体
     * @return 审计日志DTO
     */
    AuditLogDTO toDTO(AuditLog entity);

    /**
     * 将审计日志DTO转换为实体
     *
     * @param dto 审计日志DTO
     * @return 审计日志实体
     */
    AuditLog toEntity(AuditLogDTO dto);
} 