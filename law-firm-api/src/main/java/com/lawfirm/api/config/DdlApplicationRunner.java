package com.lawfirm.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * DDL应用运行器配置
 * <p>
 * 提供正确类型的Bean解决运行时错误
 * </p>
 */
@Slf4j
@Configuration
public class DdlApplicationRunner {

    /**
     * 创建DDL应用运行器
     * 
     * @return CommandLineRunner实例
     */
    @Bean(name = "ddlApplicationRunner")
    @Primary
    @Order(1)
    public CommandLineRunner ddlApplicationRunner() {
        log.info("创建DDL应用运行器");
        
        return args -> {
            log.info("DDL应用运行器执行完成");
        };
    }
} 