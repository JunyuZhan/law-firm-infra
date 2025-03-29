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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import com.lawfirm.api.config.FlowableConfig;
import com.lawfirm.api.config.WorkflowExclusionConfig;

/**
 * 律师事务所API应用
 *
 * @since 2022-06-15
 */
@SpringBootApplication(
    exclude = {
        SecurityAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        LdapRepositoriesAutoConfiguration.class,
        SqlInitializationAutoConfiguration.class
    }
)
@ComponentScan(
    basePackages = {"com.lawfirm.api", "com.lawfirm.common", "com.lawfirm.model", "com.lawfirm.core.system"},
    excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = "com\\.lawfirm\\.core\\.workflow\\..*"),
        @Filter(type = FilterType.REGEX, pattern = ".*FlowableConfig")
    }
)
@ConfigurationPropertiesScan(basePackages = {"com.lawfirm.common", "com.lawfirm.core"})
@Import({FlowableConfig.class, WorkflowExclusionConfig.class})
public class LawFirmApiApplication {

    static {
        // 启动前设置系统属性，确保Flowable相关配置被禁用
        System.setProperty("flowable.enabled", "false");
        System.setProperty("lawfirm.workflow.enabled", "false");
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        application.setLazyInitialization(true);
        application.run(args);
    }
}