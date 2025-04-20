package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * DDL配置类
 * <p>
 * 此配置类用于提供应用运行器Bean以支持MyBatis-Plus与Spring Boot的兼容性
 * 该配置是确保MyBatis-Plus与Spring Boot版本3.2.3兼容的关键部分
 * 整个系统升级到Spring Boot 3.2.3后，需要此配置确保MyBatis-Plus正常工作
 * </p>
 */
@Slf4j
@Configuration
public class DdlConfig implements CommandLineRunner {

    /**
     * 创建DDL应用运行器Bean
     * <p>
     * 该Bean用于满足MyBatis-Plus的依赖需求，确保系统启动兼容性
     * 实际上不执行DDL操作，因为项目使用Flyway进行数据库迁移
     * </p>
     * @return ApplicationRunner实例
     */
    @Bean(name = "ddlApplicationRunner")
    @Order(100)
    public CommandLineRunner ddlApplicationRunner() {
        log.info("注册ddlApplicationRunner Bean以支持MyBatis-Plus兼容性");
        return args -> {
            log.info("ddlApplicationRunner已启动，但不执行实际DDL操作，项目使用Flyway进行数据库迁移");
        };
    }
    
    /**
     * 实现CommandLineRunner接口的run方法
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("DdlConfig CommandLineRunner执行");
    }
} 