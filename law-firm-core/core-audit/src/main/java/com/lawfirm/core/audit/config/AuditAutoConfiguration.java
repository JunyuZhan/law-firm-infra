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
import com.lawfirm.model.auth.service.PermissionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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

    @Bean
    @ConditionalOnMissingBean(name = "modelAuditLogConverter")
    public AuditLogConverter auditLogConverter() {
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(name = "modelAuditRecordConverter")
    public AuditRecordConverter auditRecordConverter() {
        return null;
    }
} 