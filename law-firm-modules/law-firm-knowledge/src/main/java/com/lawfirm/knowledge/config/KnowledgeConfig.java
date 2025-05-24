package com.lawfirm.knowledge.config;

import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.dto.AuditRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.lawfirm.model.ai.service.QAService;
import com.lawfirm.model.ai.service.DocProcessService;
import com.lawfirm.model.ai.service.TextAnalysisService;
import com.lawfirm.model.ai.service.DecisionSupportService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;

/**
 * 知识管理模块配置
 */
@Slf4j
@Configuration("knowledgeConfig")
@EnableScheduling
@EnableConfigurationProperties(KnowledgeProperties.class)
public class KnowledgeConfig {

    private final KnowledgeProperties knowledgeProperties;

    public KnowledgeConfig(KnowledgeProperties knowledgeProperties) {
        this.knowledgeProperties = knowledgeProperties;
        initStoragePath();
    }

    /**
     * 获取存储配置
     * @return 存储配置
     */
    public KnowledgeProperties.Storage getStorage() {
        return knowledgeProperties.getStorage();
    }

    /**
     * 初始化存储路径
     */
    private void initStoragePath() {
        // 初始化基础路径
        String basePath = knowledgeProperties.getStorage().getBasePath();
        createDirectoryIfNotExists(basePath);
        log.info("初始化知识管理基础存储路径: {}", basePath);

        // 初始化临时路径
        String tempPath = knowledgeProperties.getStorage().getTempPath();
        createDirectoryIfNotExists(tempPath);
        log.info("初始化知识管理临时存储路径: {}", tempPath);
    }

    /**
     * 创建目录（如果不存在）
     */
    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                log.info("创建目录成功: {}", path);
            } else {
                log.warn("创建目录失败: {}", path);
            }
        }
    }

    /**
     * 知识模块初始化配置
     */
    @Bean(name = "knowledgeModuleInitializer")
    public KnowledgeModuleInitializer knowledgeModuleInitializer() {
        return new KnowledgeModuleInitializer();
    }

    /**
     * 知识模块初始化器
     */
    public class KnowledgeModuleInitializer {
        
        public KnowledgeModuleInitializer() {
            log.info("知识管理模块初始化完成");
            log.info("知识文档最大大小: {}", knowledgeProperties.getDocument().getMaxSize());
            log.info("知识附件最大大小: {}", knowledgeProperties.getAttachment().getMaxSize());
            log.info("知识分类最大深度: {}", knowledgeProperties.getCategory().getMaxDepth());
        }
    }

    @Bean(name = "knowledgeQAService")
    public QAService knowledgeQAService(@Qualifier("aiQAServiceImpl") QAService qaService) {
        return qaService;
    }

    @Bean(name = "knowledgeDocProcessService")
    public DocProcessService knowledgeDocProcessService(@Qualifier("aiDocProcessServiceImpl") DocProcessService docProcessService) {
        return docProcessService;
    }

    @Bean(name = "knowledgeTextAnalysisService")
    public TextAnalysisService knowledgeTextAnalysisService(@Qualifier("aiTextAnalysisServiceImpl") TextAnalysisService textAnalysisService) {
        return textAnalysisService;
    }

    @Bean(name = "knowledgeDecisionSupportService")
    public DecisionSupportService knowledgeDecisionSupportService(@Qualifier("aiDecisionSupportServiceImpl") DecisionSupportService decisionSupportService) {
        return decisionSupportService;
    }
} 