package com.lawfirm.evidence.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 证据模块MyBatis配置
 * 确保所有Mapper都被正确扫描
 */
@Configuration
@MapperScan(basePackages = {"com.lawfirm.model.evidence.mapper"})
public class EvidenceModuleConfig {
    // 只做Mapper扫描配置
} 