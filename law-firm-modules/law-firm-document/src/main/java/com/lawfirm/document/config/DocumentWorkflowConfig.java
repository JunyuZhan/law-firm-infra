package com.lawfirm.document.config;

import com.lawfirm.model.workflow.service.WorkflowService;
import com.lawfirm.core.workflow.service.impl.WorkflowServiceImpl;
import com.lawfirm.model.workflow.mapper.WorkflowMapper;
import com.lawfirm.model.workflow.mapper.WorkflowNodeMapper;
import com.lawfirm.model.workflow.mapper.WorkflowTaskMapper;
import com.lawfirm.model.workflow.converter.WorkflowConverter;
import com.lawfirm.model.workflow.converter.WorkflowNodeConverter;
import com.lawfirm.model.workflow.converter.WorkflowTaskConverter;
import com.lawfirm.document.config.properties.DocumentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档工作流配置
 */
@Configuration
@EnableConfigurationProperties(DocumentProperties.class)
public class DocumentWorkflowConfig {

    /**
     * 配置工作流服务
     */
    @Bean
    public WorkflowService workflowService(WorkflowMapper workflowMapper,
                                         WorkflowNodeMapper workflowNodeMapper,
                                         WorkflowTaskMapper workflowTaskMapper,
                                         WorkflowConverter workflowConverter,
                                         WorkflowNodeConverter workflowNodeConverter,
                                         WorkflowTaskConverter workflowTaskConverter) {
        return new WorkflowServiceImpl(workflowMapper, workflowNodeMapper, workflowTaskMapper,
                                     workflowConverter, workflowNodeConverter, workflowTaskConverter);
    }
} 