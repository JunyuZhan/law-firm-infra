package com.lawfirm.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 律师事务所API应用入口类
 */
@Slf4j
@SpringBootApplication(
    scanBasePackages = {
        // API 模块自身
        "com.lawfirm.api",
        // 通用模块
        "com.lawfirm.common",
        // 核心模块
        "com.lawfirm.core",
        // 系统模块 (可能包含 auth, system 等 Controller)
        "com.lawfirm.auth",
        "com.lawfirm.system",
        // 添加业务模块的包
        "com.lawfirm.cases",
        "com.lawfirm.client",
        "com.lawfirm.contract",
        "com.lawfirm.document",
        "com.lawfirm.knowledge"
        // 注意: 如果有其他业务模块的 Controller 也需要被扫描，需要在此处添加
    },
    exclude = {
        // 排除Spring Security自动配置，使用我们自己的安全配置
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
        // 排除验证器自动配置，避免与客户端模块冲突
        ValidationAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        DataSourceAutoConfiguration.class
    }
)
public class LawFirmApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(LawFirmApiApplication.class)
            .build();
        
        // 禁用懒加载，确保API文档初始化正常
        application.setLazyInitialization(false);
        
        Environment env = application.run(args).getEnvironment();
        
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String port = env.getProperty("server.port", "8080");
        String hostAddress = "localhost";
        
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("无法确定主机地址");
        }
        
        log.info("""
            ----------------------------------------------------------
            应用 '{}' 已启动! 访问URL:
            本地: \t\t{}://localhost:{}{}
            外部: \t\t{}://{}:{}{}
            配置文件: \t{}
            ----------------------------------------------------------""",
            env.getProperty("spring.application.name"),
            protocol, port, contextPath,
            protocol, hostAddress, port, contextPath,
            Arrays.toString(env.getActiveProfiles())
        );
    }
}