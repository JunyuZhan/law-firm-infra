package com.lawfirm.model.log.converter;

import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * 审计记录转换器
 * <p>
 * 该接口用于审计记录实体与DTO之间的相互转换。
 * 使用MapStruct框架自动生成实现类，提供实体与DTO之间的无缝转换。
 * 属于日志模型模块，处理审计记录的数据转换逻辑。
 * </p>
 *
 * @author lawfirm-dev
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, implementationName = "ModelAuditRecordConverterImpl")
@Component("modelAuditRecordConverter")
public interface AuditRecordConverter {

    /**
     * 将审计记录实体转换为DTO
     *
     * @param entity 审计记录实体
     * @return 审计记录DTO
     */
    AuditRecordDTO toDTO(AuditRecord entity);

    /**
     * 将审计记录DTO转换为实体
     *
     * @param dto 审计记录DTO
     * @return 审计记录实体
     */
    AuditRecord toEntity(AuditRecordDTO dto);
} 