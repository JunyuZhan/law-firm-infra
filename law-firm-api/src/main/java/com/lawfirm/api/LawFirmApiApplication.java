package com.lawfirm.api;

import com.lawfirm.api.config.CustomBeanNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * 律师事务所API应用入口类
 */
@Slf4j
@SpringBootApplication(
    scanBasePackages = {
        "com.lawfirm.api",
        "com.lawfirm.common",
        "com.lawfirm.core",
        "com.lawfirm.auth",
        "com.lawfirm.system",
        "com.lawfirm.cases",
        "com.lawfirm.client",
        "com.lawfirm.contract",
        "com.lawfirm.document",
        "com.lawfirm.knowledge"
    },
    exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
        // 排除所有JPA相关的自动配置
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
    }
)
public class LawFirmApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        application.setBeanNameGenerator(new CustomBeanNameGenerator());
        application.run(args);
        log.info("律师事务所管理系统已启动");
    }
}