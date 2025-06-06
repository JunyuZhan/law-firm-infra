package com.lawfirm.evidence.config;

import org.springframework.context.annotation.Configuration;

/**
 * 证据模块MyBatis配置
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Configuration("evidenceModuleConfig")
public class EvidenceModuleConfig {
    // Mapper扫描由主应用统一处理，避免重复定义
} 