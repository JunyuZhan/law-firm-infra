package com.lawfirm.cases.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import com.lawfirm.common.security.config.SecurityConfig;
import com.lawfirm.common.security.core.SecurityService;
import com.lawfirm.common.security.audit.SecurityAudit;
import com.lawfirm.cases.constant.CaseBusinessConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 案件模块安全配置类
 * 
 * 负责案件管理模块的安全配置，集成认证授权模块提供的安全功能，
 * 配置案件模块特定的权限和数据权限规则。
 * 基于common-security模块提供的安全基础设施。
 */
@Slf4j
@Configuration
@Order(200)
@ConditionalOnBean(SecurityConfig.class)
public class CaseSecurityConfiguration {
    
    @Autowired
    private CaseModuleConfiguration.CaseProperties caseProperties;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired(required = false)
    private SecurityAudit securityAudit;
    
    /**
     * 案件安全配置初始化
     */
    public CaseSecurityConfiguration() {
        log.info("案件模块安全配置初始化");
    }
    
    /**
     * 配置案件权限提供者
     * 集成认证模块提供的权限框架
     */
    @Bean(name = "casePermissionConfigurer")
    public CasePermissionConfigurer casePermissionConfigurer() {
        log.info("初始化案件权限配置");
        return new CasePermissionConfigurer(securityService, securityAudit);
    }
    
    /**
     * 配置案件数据权限过滤器
     */
    @Bean(name = "caseDataPermissionFilter")
    public CaseDataPermissionFilter caseDataPermissionFilter() {
        log.info("初始化案件数据权限过滤器");
        return new CaseDataPermissionFilter(securityService);
    }
    
    /**
     * 案件权限配置器
     * 负责配置案件相关的权限规则，集成common-security
     */
    public static class CasePermissionConfigurer {
        
        private final SecurityService securityService;
        private final SecurityAudit securityAudit;
        
        public CasePermissionConfigurer(SecurityService securityService, SecurityAudit securityAudit) {
            this.securityService = securityService;
            this.securityAudit = securityAudit;
        }
        
        /**
         * 注册案件模块权限资源
         */
        public void configure() {
            // 注册案件管理相关权限
            log.info("注册案件模块权限资源");
            
            // 如果有安全审计组件，注册审计事件
            if (securityAudit != null) {
                securityAudit.logOperationEvent(
                    securityService.getSecurityContext().getAuthentication() != null ? 
                    securityService.getSecurityContext().getAuthentication().getPrincipal() : "SYSTEM",
                    "INITIALIZE", 
                    CaseBusinessConstants.Integration.AUTH_RESOURCE_CASE
                );
            }
        }
    }
    
    /**
     * 案件数据权限过滤器
     * 负责处理案件数据的权限过滤，集成common-security
     */
    public static class CaseDataPermissionFilter {
        
        private final SecurityService securityService;
        
        public CaseDataPermissionFilter(SecurityService securityService) {
            this.securityService = securityService;
        }
        
        /**
         * 配置数据权限过滤规则
         */
        public void configure() {
            // 配置案件数据权限过滤规则
            log.info("配置案件数据权限过滤规则");
            
            // 通过securityService获取当前用户身份信息，用于数据权限判断
        }
    }
} 