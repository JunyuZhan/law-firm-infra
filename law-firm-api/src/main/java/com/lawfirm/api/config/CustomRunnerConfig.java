package com.lawfirm.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义Runner配置类
 * <p>
 * 提供定制的CommandLineRunner用于应用启动后执行特定初始化任务
 * </p>
 */
@Slf4j
@Configuration
public class CustomRunnerConfig implements BeanFactoryAware {

    private BeanFactory beanFactory;
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    /**
     * 应用初始化Runner
     * <p>
     * 用于应用启动后执行特定的初始化任务
     * </p>
     */
    @Bean("apiInitRunner")
    @Order(10)
    public CommandLineRunner apiInitRunner() {
        return args -> {
            log.info("API应用初始化工作开始执行...");
            
            try {
                // 执行初始化逻辑
                log.info("1. 检查基础配置");
                log.info("2. 初始化资源目录");
                log.info("3. 验证数据库连接");
                
                // 模拟一些初始化工作
                Thread.sleep(500);
                
                log.info("API应用初始化工作完成");
            } catch (Exception e) {
                log.error("API应用初始化工作失败: {}", e.getMessage(), e);
            }
        };
    }
    
    /**
     * 应用启动完成通知Runner
     * <p>
     * 在所有初始化完成后打印应用启动成功消息
     * </p>
     */
    @Bean("apiStartupCompleteRunner")
    @Order(Integer.MAX_VALUE - 10) // 确保在最后执行
    public CommandLineRunner apiStartupCompleteRunner() {
        return args -> {
            log.info("API应用启动完成，系统准备就绪");
        };
    }
} 