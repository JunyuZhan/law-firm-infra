package com.lawfirm.evidence.config;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.ai.service.QAService;
import com.lawfirm.model.ai.service.DocProcessService;
import com.lawfirm.model.ai.service.TextAnalysisService;
import com.lawfirm.model.ai.service.DecisionSupportService;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 证据模块对core层服务的统一注入配置，所有bean名称加 evidence 前缀防止冲突
 */
@Configuration
public class EvidenceCoreServiceConfig {
    @Bean(name = "evidenceMessageSender")
    public MessageSender evidenceMessageSender(@Qualifier("messageSender") MessageSender messageSender) {
        return messageSender;
    }

    @Bean(name = "evidenceFileService")
    public FileService evidenceFileService(@Qualifier("storageFileServiceImpl") FileService fileService) {
        return fileService;
    }

    @Bean(name = "evidenceBucketService")
    public BucketService evidenceBucketService(@Qualifier("storageBucketServiceImpl") BucketService bucketService) {
        return bucketService;
    }

    @Bean(name = "evidenceQAService")
    public QAService evidenceQAService(@Qualifier("aiQAServiceImpl") QAService qaService) {
        return qaService;
    }

    @Bean(name = "evidenceDocProcessService")
    public DocProcessService evidenceDocProcessService(@Qualifier("aiDocProcessServiceImpl") DocProcessService docProcessService) {
        return docProcessService;
    }

    @Bean(name = "evidenceTextAnalysisService")
    public TextAnalysisService evidenceTextAnalysisService(@Qualifier("aiTextAnalysisServiceImpl") TextAnalysisService textAnalysisService) {
        return textAnalysisService;
    }

    @Bean(name = "evidenceDecisionSupportService")
    public DecisionSupportService evidenceDecisionSupportService(@Qualifier("aiDecisionSupportServiceImpl") DecisionSupportService decisionSupportService) {
        return decisionSupportService;
    }

    @Bean(name = "evidenceAuditService")
    public AuditService evidenceAuditService(@Qualifier("coreAuditServiceImpl") AuditService auditService) {
        return auditService;
    }

    @Bean(name = "evidenceWorkflowService")
    public WorkflowService evidenceWorkflowService(@Qualifier("workflowService") WorkflowService workflowService) {
        return workflowService;
    }
} 