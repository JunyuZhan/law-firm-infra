package com.lawfirm.cases.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Import;
import com.lawfirm.client.config.ClientAutoConfiguration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 案件模块配置类
 * 
 * 负责案件管理模块的自动配置，包括组件扫描、MyBatis映射器扫描、
 * 事务管理和异步任务支持等配置。
 */
@Slf4j
@AutoConfiguration
@ConditionalOnProperty(prefix = "lawfirm.module", name = "case", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = "com.lawfirm.cases")
// 注释掉冲突的MapperScan配置，使用全局配置
// @MapperScan("com.lawfirm.model.cases.mapper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableAsync
@EnableConfigurationProperties(CaseModuleConfiguration.CaseProperties.class)
@Import(ClientAutoConfiguration.class)
public class CaseModuleConfiguration {

    /**
     * 配置模块初始化完成日志记录器
     */
    @Bean(name = "caseModuleInitializer")
    public String caseModuleInitializer() {
        log.info("案件管理模块初始化完成");
        return "caseModuleInitializer";
    }
    
    /**
     * 配置案件业务处理器
     */
    @Bean(name = "caseModuleProcessor")
    public CaseModuleProcessor caseModuleProcessor() {
        return new CaseModuleProcessor();
    }
    
    /**
     * 案件模块处理器
     * 负责处理模块初始化后的业务逻辑
     */
    public static class CaseModuleProcessor {
        
        /**
         * 处理模块初始化任务
         */
        public void process() {
            // 模块初始化后的业务处理逻辑
        }
    }
    
    /**
     * 案件配置属性
     */
    @Data
    @ConfigurationProperties(prefix = "lawfirm.case")
    public static class CaseProperties {
        
        /**
         * 模块是否启用
         */
        private boolean enabled = true;
        
        /**
         * 案件编号生成配置
         */
        private CaseNumberConfig caseNumber = new CaseNumberConfig();
        
        /**
         * 文档配置
         */
        private DocumentConfig document = new DocumentConfig();
        
        /**
         * 状态流转配置
         */
        private StatusFlowConfig statusFlow = new StatusFlowConfig();
        
        /**
         * 案件编号生成配置
         */
        @Data
        public static class CaseNumberConfig {
            /**
             * 案件编号前缀
             */
            private String prefix = "CASE";
            
            /**
             * 日期格式
             */
            private String dateFormat = "yyyyMMdd";
            
            /**
             * 序列号长度
             */
            private int sequenceLength = 4;
        }
        
        /**
         * 文档配置
         */
        @Data
        public static class DocumentConfig {
            /**
             * 允许的文件扩展名，逗号分隔
             */
            private String allowedExtensions = ".pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.jpg,.png";
            
            /**
             * 最大文件大小
             */
            private String maxSize = "50MB";
            
            /**
             * 存储路径
             */
            private String storagePath = "/case/documents";
        }
        
        /**
         * 状态流转配置
         */
        @Data
        public static class StatusFlowConfig {
            /**
             * 是否启用状态流转
             */
            private boolean enabled = true;
            
            /**
             * 是否需要审批
             */
            private boolean requireApproval = true;
            
            /**
             * 是否通知案件负责人
             */
            private boolean notifyOwner = true;
        }
    }
    
    /**
     * 案件服务配置
     */
    @Bean(name = "caseServiceConfig")
    @ConditionalOnProperty(name = "lawfirm.case.enabled", havingValue = "true", matchIfMissing = true)
    public CaseServiceConfig caseServiceConfig(CaseProperties properties) {
        log.info("案件服务配置初始化，编号前缀：{}，状态流转：{}", 
                properties.getCaseNumber().getPrefix(),
                properties.getStatusFlow().isEnabled() ? "启用" : "禁用");
        return new CaseServiceConfig(properties);
    }
    
    /**
     * 案件服务配置类
     */
    public static class CaseServiceConfig {
        private final CaseProperties properties;
        
        public CaseServiceConfig(CaseProperties properties) {
            this.properties = properties;
        }
        
        public CaseProperties getProperties() {
            return properties;
        }
    }
} 