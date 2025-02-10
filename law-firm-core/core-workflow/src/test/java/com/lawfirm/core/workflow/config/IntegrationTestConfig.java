package com.lawfirm.core.workflow.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * 集成测试配置
 */
@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/flowable/db/create/flowable.h2.create.engine.sql")
                .addScript("classpath:org/flowable/db/create/flowable.h2.create.history.sql")
                .build();
    }

    @Bean
    public ProcessEngine processEngine(DataSource dataSource) {
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration()
                .setDataSource(dataSource)
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setAsyncExecutorActivate(false);

        return configuration.buildProcessEngine();
    }
} 