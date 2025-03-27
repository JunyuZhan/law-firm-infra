package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 律师事务所API应用
 *
 * @since 2022-06-15
 */
@SpringBootApplication(
    // 扫描全部必要的包，确保所有服务都能被正确加载
    scanBasePackages = {
        "com.lawfirm.api", 
        "com.lawfirm.model",
        "com.lawfirm.common",  // 明确包含common包
        "com.lawfirm.auth",
        "com.lawfirm.client",
        "com.lawfirm.cases",   // 正确的包名是cases，不是case
        "com.lawfirm.document",
        "com.lawfirm.contract",
        "com.lawfirm.finance",
        "com.lawfirm.system",
        "com.lawfirm.personnel"
    },
    exclude = {
        SecurityAutoConfiguration.class,
        // 排除SQL和数据源自动配置
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        // 排除Flowable相关自动配置类
        FlywayAutoConfiguration.class,
        // 排除JPA相关自动配置类
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        // 排除Elasticsearch相关自动配置类
        ElasticsearchRepositoriesAutoConfiguration.class,
        ReactiveElasticsearchRepositoriesAutoConfiguration.class,
        // 排除LDAP相关自动配置类
        LdapRepositoriesAutoConfiguration.class,
        // 排除SQL初始化自动配置类，避免ddlApplicationRunner被创建
        SqlInitializationAutoConfiguration.class
    }
)
@ConfigurationPropertiesScan(basePackages = {"com.lawfirm.common"})
public class LawFirmApiApplication {

    public static void main(String[] args) {
        // 不再需要允许循环引用，已解决循环依赖问题
        // System.setProperty("spring.main.allow-circular-references", "true");
        
        // 核心模块功能开关
        System.setProperty("law.firm.audit.enabled", "false");  // 禁用审计功能
        System.setProperty("law.firm.workflow.enabled", "false"); // 禁用工作流功能
        System.setProperty("law.firm.storage.enabled", "true"); // 启用存储功能
        
        // API文档默认关闭，可通过环境变量ENABLE_API_DOCS启用
        String enableApiDocs = System.getenv("ENABLE_API_DOCS");
        if (!"true".equals(enableApiDocs)) {
            System.setProperty("springdoc.api-docs.enabled", "false");
            System.setProperty("springdoc.swagger-ui.enabled", "false");
        }
        
        // 开发环境下启用所有必要的模块
        System.setProperty("law.firm.client.enabled", "true");
        System.setProperty("law.firm.auth.enabled", "true");
        
        // 禁用方法级别权限验证
        System.setProperty("spring.security.enabled", "false");
        System.setProperty("method.security.enabled", "false");
        
        // 禁用Flowable相关功能
        System.setProperty("flowable.enabled", "false");
        System.setProperty("flowable.process-engine.enable", "false");
        System.setProperty("flowable.dmn-engine.enable", "false");
        System.setProperty("flowable.idm-engine.enable", "false");
        System.setProperty("flowable.form-engine.enable", "false");
        System.setProperty("flowable.content-engine.enable", "false");
        System.setProperty("flowable.event-registry-engine.enable", "false");
        System.setProperty("flowable.rest.enabled", "false");
        System.setProperty("flowable.actuator.enabled", "false");
        System.setProperty("flowable.database-schema-update", "false");
        System.setProperty("flowable.check-process-definitions", "false");
        
        // 禁用JPA相关功能
        System.setProperty("spring.jpa.enabled", "false");
        System.setProperty("spring.data.jpa.repositories.enabled", "false");
        System.setProperty("spring.data.elasticsearch.repositories.enabled", "false");
        System.setProperty("spring.data.elasticsearch-reactive.repositories.enabled", "false");
        System.setProperty("spring.data.ldap.repositories.enabled", "false");
        
        // 排除自动配置
        System.setProperty("spring.autoconfigure.exclude", 
            "org.flowable.spring.boot.FlowableAutoConfiguration," +
            "org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration," +
            "org.flowable.spring.boot.actuate.endpoint.ProcessEngineEndpointAutoConfiguration," +
            "org.flowable.spring.boot.actuate.info.FlowableInfoAutoConfiguration," +
            "org.flowable.spring.boot.app.AppEngineAutoConfiguration," +
            "org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration," +
            "org.flowable.spring.boot.engine.FlowableEngineAutoConfiguration," +
            "org.flowable.spring.boot.EndpointAutoConfiguration," +
            "org.flowable.spring.boot.FlowableSecurityAutoConfiguration," +
            "org.flowable.spring.boot.idm.IdmEngineAutoConfiguration," +
            "org.flowable.spring.boot.idm.IdmEngineServicesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration," +
            "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration");
        
        // 禁用数据库相关功能
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
        
        // 禁用SQL初始化
        System.setProperty("spring.flyway.enabled", "false");
        System.setProperty("spring.liquibase.enabled", "false");
        System.setProperty("spring.sql.init.enabled", "false");
        System.setProperty("spring.sql.init.platform", "none");
        System.setProperty("spring.sql.init.mode", "never");
        System.setProperty("spring.boot.sql.init.enabled", "false");
        
        // 创建SpringApplication并配置
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        
        // 设置额外属性
        Map<String, Object> props = new HashMap<>();
        props.put("spring.sql.init.enabled", "false");
        props.put("spring.main.banner-mode", "off");
        props.put("spring.main.log-startup-info", "false");
        props.put("spring.h2.console.enabled", "false");
        application.setDefaultProperties(props);
        
        // 运行应用
        application.run(args);
    }
}