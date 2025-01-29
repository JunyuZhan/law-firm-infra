package com.lawfirm.core.workflow.config;

import lombok.RequiredArgsConstructor;
import org.flowable.engine.*;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 工作流自动配置
 */
@Configuration
@EnableConfigurationProperties(WorkflowProperties.class)
@RequiredArgsConstructor
public class WorkflowAutoConfiguration {
    
    private final WorkflowProperties workflowProperties;
    
    @Bean
    @Primary
    public ProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource) {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(true);
        configuration.setHistory("full");

        // 设置流程定义文件路径
        if (workflowProperties.getProcess() != null) {
            String location = workflowProperties.getProcess().getDefinitionLocation();
            try {
                Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:" + location + "*.bpmn20.xml");
                configuration.setDeploymentResources(resources);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load process definitions", e);
            }
        }

        return configuration;
    }
    
    @Bean
    public ProcessEngine processEngine(ProcessEngineConfiguration configuration) {
        return configuration.buildProcessEngine();
    }
    
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }
    
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }
    
    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }
} 