package com.lawfirm.core.audit.config;

import com.lawfirm.model.log.converter.AuditLogConverter;
import com.lawfirm.model.log.converter.AuditRecordConverter;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import com.lawfirm.model.log.entity.audit.AuditLog;
import com.lawfirm.model.log.entity.audit.AuditRecord;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 审计转换器配置
 * <p>
 * 配置审计日志和审计记录转换器Bean，解决自动装配问题。
 * 由于MapStruct生成的实现类无法正确注入，这里提供手动实现的转换器。
 * </p>
 *
 * @author JunyuZhan
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class AuditConverterConfig {

    /**
     * 创建AuditLogConverter实现Bean
     * 
     * @return 审计日志转换器
     */
    @Bean
    public AuditLogConverter auditLogConverter() {
        log.info("创建手动实现的审计日志转换器");
        return new AuditLogConverter() {
            @Override
            public AuditLogDTO toDTO(AuditLog entity) {
                if (entity == null) {
                    return null;
                }
                AuditLogDTO dto = new AuditLogDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }

            @Override
            public AuditLog toEntity(AuditLogDTO dto) {
                if (dto == null) {
                    return null;
                }
                AuditLog entity = new AuditLog();
                BeanUtils.copyProperties(dto, entity);
                return entity;
            }
        };
    }

    /**
     * 创建AuditRecordConverter实现Bean
     * 
     * @return 审计记录转换器
     */
    @Bean
    public AuditRecordConverter auditRecordConverter() {
        log.info("创建手动实现的审计记录转换器");
        return new AuditRecordConverter() {
            @Override
            public AuditRecordDTO toDTO(AuditRecord entity) {
                if (entity == null) {
                    return null;
                }
                AuditRecordDTO dto = new AuditRecordDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }

            @Override
            public AuditRecord toEntity(AuditRecordDTO dto) {
                if (dto == null) {
                    return null;
                }
                AuditRecord entity = new AuditRecord();
                BeanUtils.copyProperties(dto, entity);
                return entity;
            }
        };
    }
} 