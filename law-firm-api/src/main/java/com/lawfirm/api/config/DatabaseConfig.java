package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.mybatis.spring.annotation.MapperScan;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * 数据库配置类
 * 提供数据库功能启用和禁用时的不同配置
 */
@Slf4j
@Configuration
public class DatabaseConfig {

    /**
     * 当数据库功能启用时，扫描所有Mapper接口
     * 注意：必须放在API层而不是各个模块中，以确保所有模块的Mapper都被扫描到
     */
    @Configuration
    @ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "true", matchIfMissing = true)
    @MapperScan(basePackages = {
        "com.lawfirm.model.*.mapper",
        "com.lawfirm.model.*.mapper.*",
        "com.lawfirm.*.mapper"
    })
    public static class DatabaseEnabledConfig {
        
        public DatabaseEnabledConfig() {
            log.info("数据库功能已启用，扫描所有Mapper接口");
        }
    }
    
    /**
     * 当数据库功能禁用时，提供一个内存数据库作为替代
     * 主要用于开发测试环境
     */
    @Configuration
    @ConditionalOnProperty(name = "lawfirm.database.enabled", havingValue = "false")
    public static class DatabaseDisabledConfig {
        
        public DatabaseDisabledConfig() {
            log.info("数据库功能已禁用，使用内存数据库作为替代");
        }
        
        /**
         * 创建一个内存数据库用于替代生产数据库
         * 这样可以避免因为没有数据源而导致的启动失败
         */
        @Bean
        @Primary
        public DataSource embeddedDataSource() {
            return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("law-firm-memory-db")
                .build();
        }
        
        /**
         * 配置事务管理器
         */
        @Bean
        @Primary
        public DataSourceTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
} 