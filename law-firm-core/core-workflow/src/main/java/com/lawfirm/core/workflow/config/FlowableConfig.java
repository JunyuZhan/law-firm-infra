package com.lawfirm.core.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * Flowable工作流引擎配�? * 
 * @author JunyuZhan
 */
@Slf4j
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    /**
     * 自定义配置Flowable流程引擎
     * 
     * @param processEngineConfiguration 流程引擎配置
     */
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        log.info("配置Flowable流程引擎...");
        
        // 设置流程实例ID生成�?        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
        
        // 设置异步执行器激�?        processEngineConfiguration.setAsyncExecutorActivate(true);
        
        // 设置历史记录级别为完整级别，保存所有历史记�?        processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
        
        // 部署流程定义文件
        deployProcessDefinitions(processEngineConfiguration);
    }
    
    /**
     * 部署流程定义文件
     * 
     * @param processEngineConfiguration 流程引擎配置
     */
    private void deployProcessDefinitions(SpringProcessEngineConfiguration processEngineConfiguration) {
        try {
            // 获取classpath:bpmn/下的所有流程定义文�?            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:bpmn/**/*.bpmn20.xml");
            
            if (resources != null && resources.length > 0) {
                log.info("发现{}个流程定义文�?, resources.length);
                processEngineConfiguration.setDeploymentResources(resources);
            } else {
                log.warn("未发现流程定义文�?);
            }
        } catch (IOException e) {
            log.error("加载流程定义文件失败", e);
        }
    }
    
    /**
     * 流程运行时服�?     */
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }
    
    /**
     * 任务服务
     */
    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }
    
    /**
     * 表单服务
     */
    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }
    
    /**
     * 历史服务
     */
    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }
    
    /**
     * 存储库服�?     */
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }
    
    /**
     * 管理服务
     */
    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }
    
    /**
     * 动态表单服�?     */
    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
        return processEngine.getDynamicBpmnService();
    }
} 
