package com.lawfirm.document.config;

import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.audit.service.impl.AuditServiceImpl;
import com.lawfirm.model.log.mapper.AuditLogMapper;
import com.lawfirm.model.log.mapper.AuditRecordMapper;
import com.lawfirm.model.log.converter.AuditLogConverter;
import com.lawfirm.model.log.converter.AuditRecordConverter;
import com.lawfirm.document.config.properties.DocumentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档审计配置
 */
@Configuration
@EnableConfigurationProperties(DocumentProperties.class)
public class DocumentAuditConfig {

    /**
     * 配置审计服务
     */
    @Bean
    public AuditService auditService(AuditLogMapper auditLogMapper,
                                   AuditRecordMapper auditRecordMapper,
                                   AuditLogConverter auditLogConverter,
                                   AuditRecordConverter auditRecordConverter) {
        return new AuditServiceImpl(auditLogMapper, auditRecordMapper, 
                                  auditLogConverter, auditRecordConverter);
    }
} 