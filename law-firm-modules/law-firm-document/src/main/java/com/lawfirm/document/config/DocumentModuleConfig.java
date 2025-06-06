package com.lawfirm.document.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.ai.service.QAService;
import com.lawfirm.model.ai.service.DocProcessService;
import com.lawfirm.model.ai.service.TextAnalysisService;
import com.lawfirm.model.ai.service.DecisionSupportService;

/**
 * 文档模块配置类
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Configuration
@EnableCaching
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

    @Bean(name = "documentQAService")
    public QAService documentQAService(@Qualifier("aiQAServiceImpl") QAService qaService) {
        return qaService;
    }

    @Bean(name = "documentDocProcessService")
    public DocProcessService documentDocProcessService(@Qualifier("aiDocProcessServiceImpl") DocProcessService docProcessService) {
        return docProcessService;
    }

    @Bean(name = "documentTextAnalysisService")
    public TextAnalysisService documentTextAnalysisService(@Qualifier("aiTextAnalysisServiceImpl") TextAnalysisService textAnalysisService) {
        return textAnalysisService;
    }

    @Bean(name = "documentDecisionSupportService")
    public DecisionSupportService documentDecisionSupportService(@Qualifier("aiDecisionSupportServiceImpl") DecisionSupportService decisionSupportService) {
        return decisionSupportService;
    }
} 