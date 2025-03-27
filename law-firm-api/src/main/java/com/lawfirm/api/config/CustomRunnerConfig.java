package com.lawfirm.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 自定义Runner配置
 * 使用反射动态创建ddlApplicationRunner满足Spring Boot 3.2.3的启动需求
 */
@Configuration
public class CustomRunnerConfig {
    
    /**
     * 配置BeanFactoryPostProcessor来动态注册Runner
     * 这将在bean定义阶段插入ddlApplicationRunner
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static BeanFactoryPostProcessor runnerBeanFixProcessor() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                if (beanFactory instanceof BeanDefinitionRegistry) {
                    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
                    
                    // 检查是否已存在ddlApplicationRunner
                    if (registry.containsBeanDefinition("ddlApplicationRunner")) {
                        registry.removeBeanDefinition("ddlApplicationRunner");
                    }
                    
                    // 创建一个空的ApplicationRunner
                    ApplicationRunner emptyRunner = args -> 
                        System.out.println("动态创建的ddlApplicationRunner执行完成");
                    
                    // 使用桥接工厂创建兼容的Runner
                    Object runnerBridge = RunnerBridgeFactory.createRunnerBridge(emptyRunner);
                    
                    // 使用Object类型注册，避免类型不匹配问题
                    BeanDefinition beanDefinition = BeanDefinitionBuilder
                        .genericBeanDefinition(Object.class, () -> runnerBridge)
                        .getBeanDefinition();
                    
                    // 注册Bean定义
                    registry.registerBeanDefinition("ddlApplicationRunner", beanDefinition);
                    
                    System.out.println("已注册自定义ddlApplicationRunner");
                }
            }
        };
    }
    
    /**
     * 额外提供一个后备的ddlApplicationRunner
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ApplicationRunner backupDdlApplicationRunner() {
        return args -> {
            System.out.println("后备DDL应用Runner已执行");
        };
    }
} 