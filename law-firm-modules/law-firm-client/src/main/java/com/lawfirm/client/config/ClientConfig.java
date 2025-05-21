package com.lawfirm.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

/**
 * 客户模块基础配置类
 */
@Configuration
@EnableScheduling
@EnableTransactionManagement
public class ClientConfig {
    
    /**
     * 方法参数验证处理器
     * 
     * 注意：Bean名称已修改为clientMethodValidator以避免与Spring Boot内置Bean冲突
     * 添加条件注解，防止与ValidationAutoConfiguration冲突
     */
    @Bean("clientMethodValidator")
    @ConditionalOnMissingBean(MethodValidationPostProcessor.class)
    public MethodValidationPostProcessor clientMethodValidator() {
        return new MethodValidationPostProcessor();
    }
    
    /**
     * 客户编号生成器
     */
    @Bean(name = "clientNoGenerator")
    public ClientNoGenerator clientNoGenerator() {
        return new ClientNoGenerator();
    }
    
    /**
     * 客户编号生成器
     */
    public static class ClientNoGenerator {
        private static final String PREFIX = "CL";
        
        /**
         * 生成客户编号
         * @return 客户编号
         */
        public String generate() {
            // 前缀+年月日+6位随机数
            return PREFIX + System.currentTimeMillis() % 1000000000;
        }
    }

    /**
     * 注入core层审计服务
     */
    @Bean(name = "clientAuditService")
    public AuditService clientAuditService(@Qualifier("coreAuditServiceImpl") AuditService auditService) {
        return auditService;
    }

    /**
     * 注入core层消息发送服务
     */
    @Bean(name = "clientMessageSender")
    public MessageSender clientMessageSender(@Qualifier("coreMessageSender") MessageSender messageSender) {
        return messageSender;
    }

    /**
     * 注入core层搜索服务
     */
    @Bean(name = "clientSearchService")
    public SearchService clientSearchService(@Qualifier("coreSearchServiceImpl") SearchService searchService) {
        return searchService;
    }

    /**
     * 注入core层流程服务
     */
    @Bean(name = "clientProcessService")
    public ProcessService clientProcessService(@Qualifier("coreProcessServiceImpl") ProcessService processService) {
        return processService;
    }
}
