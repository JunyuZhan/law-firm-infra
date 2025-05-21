package com.lawfirm.system.config;

import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.WorkflowService;
import com.lawfirm.model.ai.service.QAService;
import com.lawfirm.model.ai.service.DocProcessService;
import com.lawfirm.model.ai.service.TextAnalysisService;
import com.lawfirm.model.ai.service.DecisionSupportService;
import com.lawfirm.core.message.service.MessageSender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统模块对core层服务的统一注入配置，所有bean名称加system前缀防止冲突
 */
@Configuration
public class SystemCoreServiceConfig {

    @Bean(name = "systemFileOperator")
    @Autowired(required = false)
    public FileOperator fileOperator(@Qualifier("fileOperator") FileOperator fileOperator) {
        return fileOperator;
    }

    @Bean(name = "systemAuditService")
    @Autowired(required = false)
    public AuditService auditService(@Qualifier("coreAuditServiceImpl") AuditService auditService) {
        return auditService;
    }

    @Bean(name = "systemSearchService")
    @Autowired(required = false)
    public SearchService searchService(@Qualifier("coreSearchServiceImpl") SearchService searchService) {
        return searchService;
    }

    @Bean(name = "systemQAService")
    @Autowired(required = false)
    public QAService qaService(@Qualifier("aiQAServiceImpl") QAService qaService) {
        return qaService;
    }

    @Bean(name = "systemDocProcessService")
    @Autowired(required = false)
    public DocProcessService docProcessService(@Qualifier("aiDocProcessServiceImpl") DocProcessService docProcessService) {
        return docProcessService;
    }

    @Bean(name = "systemTextAnalysisService")
    @Autowired(required = false)
    public TextAnalysisService textAnalysisService(@Qualifier("aiTextAnalysisServiceImpl") TextAnalysisService textAnalysisService) {
        return textAnalysisService;
    }

    @Bean(name = "systemDecisionSupportService")
    @Autowired(required = false)
    public DecisionSupportService decisionSupportService(@Qualifier("aiDecisionSupportServiceImpl") DecisionSupportService decisionSupportService) {
        return decisionSupportService;
    }

    @Bean(name = "systemMessageSender")
    @Autowired(required = false)
    public MessageSender messageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }

    @Bean(name = "systemWorkflowService")
    @Autowired(required = false)
    public WorkflowService workflowService(@Qualifier("workflowService") WorkflowService workflowService) {
        return workflowService;
    }
} 