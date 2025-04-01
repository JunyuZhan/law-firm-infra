package com.lawfirm.api.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * DDL应用运行器配置
 * <p>
 * 提供正确类型的Bean解决运行时错误
 * </p>
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DdlApplicationRunner implements ApplicationRunner {

    /**
     * 创建DDL应用运行器
     * 
     * @param args 应用程序参数
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("创建DDL应用运行器");
        
        log.info("DDL应用运行器执行完成");
    }
} 