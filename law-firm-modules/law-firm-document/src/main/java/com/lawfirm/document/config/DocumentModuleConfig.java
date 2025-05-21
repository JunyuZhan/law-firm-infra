package com.lawfirm.document.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

/**
 * 文档模块配置类
 * 负责扫描文档模块相关的Mapper接口
 */
@Configuration
@EnableCaching
@MapperScan(basePackages = {"com.lawfirm.model.document.mapper"})
public class DocumentModuleConfig {
    // 文档模块配置

    /**
     * 注入core层审计服务
     */
    @Bean(name = "documentAuditService")
    public AuditService documentAuditService(@Qualifier("coreAuditServiceImpl") AuditService auditService) {
        return auditService;
    }

    /**
     * 注入core层消息发送服务
     */
    @Bean(name = "documentMessageSender")
    public MessageSender documentMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }

    /**
     * 注入core层全文检索服务
     */
    @Bean(name = "documentSearchService")
    public SearchService documentSearchService(@Qualifier("coreSearchServiceImpl") SearchService searchService) {
        return searchService;
    }

    /**
     * 注入core层流程服务
     */
    @Bean(name = "documentProcessService")
    public ProcessService documentProcessService(@Qualifier("coreProcessServiceImpl") ProcessService processService) {
        return processService;
    }
} 