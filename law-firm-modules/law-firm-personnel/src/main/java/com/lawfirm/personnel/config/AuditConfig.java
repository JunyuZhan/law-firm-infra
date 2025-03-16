package com.lawfirm.personnel.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 人事模块审计配置
 * 集成core-audit模块功能
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "lawfirm.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuditConfig {

    /**
     * 人事模块ID
     */
    public static final String MODULE_ID = "personnel";
    
    /**
     * 人事模块名称
     */
    public static final String MODULE_NAME = "人事管理";
    
    /**
     * 员工管理业务类型
     */
    public static final String BUSINESS_TYPE_EMPLOYEE = "EMPLOYEE";
    
    /**
     * 职位管理业务类型
     */
    public static final String BUSINESS_TYPE_POSITION = "POSITION";
    
    /**
     * 组织关系管理业务类型
     */
    public static final String BUSINESS_TYPE_ORGANIZATION = "ORGANIZATION";
    
    /**
     * 初始化审计配置
     */
    @Bean
    public void initAuditConfig() {
        log.info("初始化人事模块审计配置");
        
        // 注意：实际审计功能需要在core-audit模块可用时实现
        // 这里仅做占位，避免编译错误
    }
} 