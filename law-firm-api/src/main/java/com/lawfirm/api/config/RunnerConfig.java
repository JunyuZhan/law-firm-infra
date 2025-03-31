package com.lawfirm.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

/**
 * Runner配置类
 * <p>
 * 提供系统所需的各种Runner实现
 * </p>
 */
@Configuration
public class RunnerConfig {
    
    private static final Logger log = LoggerFactory.getLogger(RunnerConfig.class);
    
    /**
     * 提供一个明确命名为ddlApplicationRunner的Bean
     * <p>
     * 用于满足Spring Boot的需求
     * </p>
     */
    @Bean(name = "ddlApplicationRunner")
    @Primary
    @Order(1)
    public CommandLineRunner ddlApplicationRunner() {
        log.info("创建DDL应用Runner");
        return args -> {
            log.info("DDL应用Runner执行完成");
        };
    }
} 