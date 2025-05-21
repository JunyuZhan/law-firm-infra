package com.lawfirm.finance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

@Data
@Configuration
@ConfigurationProperties(prefix = "law-firm.finance")
public class FinanceConfig {
    private CacheConfig cache;
    private EventConfig event;
    private BusinessConfig business;

    @Data
    public static class CacheConfig {
        private boolean enabled;
        private long expireTime;
    }

    @Data
    public static class EventConfig {
        private boolean async;
        private int retryTimes;
    }

    @Data
    public static class BusinessConfig {
        private int maxPageSize;
        private int exportLimit;
    }

    /**
     * 注入core层审计服务
     */
    @Bean(name = "financeAuditService")
    public AuditService financeAuditService(@Qualifier("coreAuditServiceImpl") AuditService auditService) {
        return auditService;
    }

    /**
     * 注入core层消息发送服务
     */
    @Bean(name = "financeMessageSender")
    public MessageSender financeMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }

    /**
     * 注入core层全文检索服务
     */
    @Bean(name = "financeSearchService")
    public SearchService financeSearchService(@Qualifier("coreSearchServiceImpl") SearchService searchService) {
        return searchService;
    }

    /**
     * 注入core层流程服务
     */
    @Bean(name = "financeProcessService")
    public ProcessService financeProcessService(@Qualifier("coreProcessServiceImpl") ProcessService processService) {
        return processService;
    }
} 