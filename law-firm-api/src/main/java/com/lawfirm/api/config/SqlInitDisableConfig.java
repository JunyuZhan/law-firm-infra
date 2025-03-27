package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * SQL初始化禁用配置
 * 提供一个空的SQL初始化器，防止应用启动时尝试加载ddlApplicationRunner
 */
@Configuration
@ConditionalOnClass(DataSource.class)
public class SqlInitDisableConfig {

    /**
     * 提供一个空的数据库初始化器替代默认的初始化器
     * 这会阻止默认的ddlApplicationRunner被创建
     */
    @Bean
    @Order(1)
    @ConditionalOnMissingBean(SqlDataSourceScriptDatabaseInitializer.class)
    public SqlDataSourceScriptDatabaseInitializer disabledSqlDataSourceScriptDatabaseInitializer(DataSource dataSource) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        // 设置空的脚本位置，禁用初始化
        settings.setSchemaLocations(new ArrayList<>());
        settings.setDataLocations(new ArrayList<>());
        settings.setContinueOnError(true);
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, settings);
    }
} 