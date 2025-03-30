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
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

/**
 * 审计自动配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AuditProperties.class)
@ConditionalOnProperty(prefix = "law.firm.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({
    AuditAsyncConfig.class,
    DevLogConfig.class
})
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
    
    /**
     * 当没有AuditPropertiesProvider实现类时提供默认实现
     */
    @Bean
    @ConditionalOnMissingBean(AuditPropertiesProvider.class)
    public AuditPropertiesProvider defaultAuditPropertiesProvider(AuditProperties auditProperties) {
        log.info("创建默认审计配置提供者");
        return () -> auditProperties;
    }

    @Bean(name = "coreAuditServiceImpl")
    @Primary
    @ConditionalOnMissingBean(name = "coreAuditServiceImpl")
    public AuditService coreAuditServiceImpl(AuditLogMapper auditLogMapper, 
                                            AuditRecordMapper auditRecordMapper,
                                            AuditLogConverter auditLogConverter, 
                                            AuditRecordConverter auditRecordConverter) {
        log.info("创建审计服务实现，使用MapStruct生成的转换器");
        AuditServiceImpl auditService = new AuditServiceImpl(auditLogMapper, auditRecordMapper);
        auditService.setAuditLogConverter(auditLogConverter);
        auditService.setAuditRecordConverter(auditRecordConverter);
        return auditService;
    }
} 