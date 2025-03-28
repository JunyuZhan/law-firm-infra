package com.lawfirm.core.audit.config;

import com.lawfirm.core.audit.aspect.AuditFieldAspect;
import com.lawfirm.core.audit.aspect.AuditLogAspect;
import com.lawfirm.core.audit.aspect.AuditPointcut;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.audit.service.impl.AuditServiceImpl;
import com.lawfirm.model.log.mapper.AuditLogMapper;
import com.lawfirm.model.log.mapper.AuditRecordMapper;
import com.lawfirm.model.log.converter.AuditLogConverter;
import com.lawfirm.model.log.converter.AuditRecordConverter;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.entity.audit.AuditRecord;
import com.lawfirm.model.auth.service.PermissionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * 审计自动配置
 */
@Configuration
@EnableConfigurationProperties(AuditProperties.class)
@ConditionalOnProperty(prefix = "lawfirm.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(AuditAsyncConfig.class)
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditPointcut auditPointcut() {
        return new AuditPointcut();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditLogAspect auditLogAspect(AuditService auditService, PermissionService permissionService) {
        return new AuditLogAspect(auditService, permissionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditFieldAspect auditFieldAspect(AuditService auditService) {
        return new AuditFieldAspect(auditService);
    }

    @Bean(name = "coreAuditService")
    @ConditionalOnMissingBean(name = "coreAuditService")
    public AuditService auditService(AuditLogMapper auditLogMapper, AuditRecordMapper auditRecordMapper,
            AuditLogConverter auditLogConverter, AuditRecordConverter auditRecordConverter) {
        AuditServiceImpl auditService = new AuditServiceImpl(auditLogMapper, auditRecordMapper);
        auditService.setAuditLogConverter(auditLogConverter);
        auditService.setAuditRecordConverter(auditRecordConverter);
        return auditService;
    }

    @Bean(name = "modelAuditLogConverter")
    @ConditionalOnMissingBean(name = "modelAuditLogConverter")
    public AuditLogConverter auditLogConverter() {
        return new AuditLogConverter() {
            @Override
            public AuditLogDTO toDTO(AuditLog entity) {
                if (entity == null) {
                    return null;
                }
                AuditLogDTO dto = new AuditLogDTO();
                dto.setId(entity.getId());
                dto.setOperatorId(entity.getOperatorId());
                dto.setOperatorName(entity.getOperatorName());
                dto.setOperatorIp(entity.getOperatorIp());
                dto.setOperateType(entity.getOperateType());
                dto.setBusinessType(entity.getBusinessType());
                dto.setModule(entity.getModule());
                dto.setDescription(entity.getDescription());
                dto.setStatus(entity.getStatus());
                dto.setErrorMsg(entity.getErrorMsg());
                dto.setOperationTime(entity.getOperationTime());
                dto.setBeforeData(entity.getBeforeData());
                dto.setAfterData(entity.getAfterData());
                dto.setChangedFields(entity.getChangedFields());
                return dto;
            }

            @Override
            public AuditLog toEntity(AuditLogDTO dto) {
                if (dto == null) {
                    return null;
                }
                AuditLog entity = new AuditLog();
                entity.setId(dto.getId());
                entity.setOperatorId(dto.getOperatorId());
                entity.setOperatorName(dto.getOperatorName());
                entity.setOperatorIp(dto.getOperatorIp());
                entity.setOperateType(dto.getOperateType());
                entity.setBusinessType(dto.getBusinessType());
                entity.setModule(dto.getModule());
                entity.setDescription(dto.getDescription());
                entity.setStatus(dto.getStatus());
                entity.setErrorMsg(dto.getErrorMsg());
                entity.setOperationTime(dto.getOperationTime());
                entity.setBeforeData(dto.getBeforeData());
                entity.setAfterData(dto.getAfterData());
                entity.setChangedFields(dto.getChangedFields());
                return entity;
            }
        };
    }

    @Bean(name = "modelAuditRecordConverter")
    @ConditionalOnMissingBean(name = "modelAuditRecordConverter")
    public AuditRecordConverter auditRecordConverter() {
        return new AuditRecordConverter() {
            @Override
            public AuditRecordDTO toDTO(AuditRecord entity) {
                if (entity == null) {
                    return null;
                }
                AuditRecordDTO dto = new AuditRecordDTO();
                dto.setId(entity.getId());
                dto.setAuditLogId(entity.getAuditLogId());
                dto.setAuditorId(entity.getAuditorId());
                dto.setAuditorName(entity.getAuditorName());
                dto.setAuditAction(entity.getAuditAction());
                dto.setAuditResult(entity.getAuditResult());
                dto.setAuditOpinion(entity.getAuditOpinion());
                dto.setAuditTime(entity.getAuditTime());
                dto.setAuditNode(entity.getAuditNode());
                return dto;
            }

            @Override
            public AuditRecord toEntity(AuditRecordDTO dto) {
                if (dto == null) {
                    return null;
                }
                AuditRecord entity = new AuditRecord();
                entity.setId(dto.getId());
                entity.setAuditLogId(dto.getAuditLogId());
                entity.setAuditorId(dto.getAuditorId());
                entity.setAuditorName(dto.getAuditorName());
                entity.setAuditAction(dto.getAuditAction());
                entity.setAuditResult(dto.getAuditResult());
                entity.setAuditOpinion(dto.getAuditOpinion());
                entity.setAuditTime(dto.getAuditTime());
                entity.setAuditNode(dto.getAuditNode());
                return entity;
            }
        };
    }
} 