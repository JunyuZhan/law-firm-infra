package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
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
        "com.lawfirm.common",     
        "com.lawfirm.auth",
        "com.lawfirm.client",
        "com.lawfirm.cases",   
        "com.lawfirm.document",
        "com.lawfirm.contract",
        "com.lawfirm.finance",
        "com.lawfirm.system",
        "com.lawfirm.personnel",
        "com.lawfirm.knowledge",  
        "com.lawfirm.core.audit",  
        "com.lawfirm.core.message",  
        "com.lawfirm.core.search",   
        "com.lawfirm.core.storage"   
        // 不扫描com.lawfirm.core.workflow包，因为工作流功能已禁用
    },
    exclude = {
        SecurityAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        LdapRepositoriesAutoConfiguration.class,
        SqlInitializationAutoConfiguration.class
    }
)
@ConfigurationPropertiesScan(basePackages = {"com.lawfirm.common", "com.lawfirm.core"})
public class LawFirmApiApplication {

    /**
     * 应用配置初始化
     * 设置系统属性和环境变量
     */
    private static void initApplicationConfig() {
        // 核心模块功能开关
        System.setProperty("lawfirm.audit.enabled", "true");
        System.setProperty("lawfirm.workflow.enabled", "false");
        System.setProperty("lawfirm.storage.enabled", "true");
        
        // API文档配置
        System.setProperty("springdoc.api-docs.enabled", "true");
        System.setProperty("springdoc.swagger-ui.enabled", "false");
        System.setProperty("knife4j.enable", "true");
        
        // 模块开关
        System.setProperty("lawfirm.client.enabled", "true");
        System.setProperty("lawfirm.auth.enabled", "true");
        System.setProperty("lawfirm.module.case", "true");
        System.setProperty("lawfirm.module.contract", "true");
        
        // 安全配置
        System.setProperty("spring.security.enabled", "false");
        System.setProperty("method.security.enabled", "false");
        
        // 禁用Flowable
        disableFlowable();
        
        // 禁用JPA
        disableJpa();
        
        // 禁用SQL初始化
        disableSqlInit();
        
        // 消息服务配置
        configureMessageService();
    }
    
    /**
     * 禁用Flowable相关功能
     */
    private static void disableFlowable() {
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
    }
    
    /**
     * 禁用JPA相关功能
     */
    private static void disableJpa() {
        System.setProperty("spring.jpa.enabled", "false");
        System.setProperty("spring.data.jpa.repositories.enabled", "false");
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
            "org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration");
    }
    
    /**
     * 禁用SQL初始化
     */
    private static void disableSqlInit() {
        System.setProperty("spring.flyway.enabled", "false");
        System.setProperty("spring.liquibase.enabled", "false");
        System.setProperty("spring.sql.init.enabled", "false");
        System.setProperty("spring.sql.init.platform", "none");
        System.setProperty("spring.sql.init.mode", "never");
        System.setProperty("spring.boot.sql.init.enabled", "false");
    }
    
    /**
     * 配置消息服务
     */
    private static void configureMessageService() {
        // 禁用RocketMQ
        System.setProperty("rocketmq.enabled", "false");
        System.setProperty("rocketmq.producer.enabled", "false");
        System.setProperty("rocketmq.consumer.enabled", "false");
        
        // 启用消息服务
        System.setProperty("message.enabled", "true");
        System.setProperty("message.async.enabled", "true");
        System.setProperty("message.rocketmq.topic", "law-firm-message");
        System.setProperty("message.rocketmq.consumer-group", "law-firm-consumer");
    }

    public static void main(String[] args) {
        // 初始化应用配置
        initApplicationConfig();
        
        // 创建SpringApplication并配置
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        
        // 设置应用属性
        Map<String, Object> props = new HashMap<>();
        props.put("spring.main.banner-mode", "off");
        props.put("spring.main.log-startup-info", "false");
        props.put("spring.main.allow-bean-definition-overriding", "true");
        
        // 解决了循环依赖问题后，这个参数应该设置为false
        props.put("spring.main.allow-circular-references", "false"); 
        
        // 设置服务器端口
        props.put("server.port", "8080");
        
        application.setDefaultProperties(props);
        
        // 运行应用
        application.run(args);
    }
}