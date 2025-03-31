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

/**
 * 律师事务所API应用入口类
 */
@Slf4j
@SpringBootApplication(
    scanBasePackages = {
        "com.lawfirm.api",
        "com.lawfirm.common",
        "com.lawfirm.auth",
        "com.lawfirm.system",
        "com.lawfirm.core"
    },
    exclude = {
        // 排除Spring Security自动配置，使用我们自己的安全配置
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
        // 排除验证器自动配置，避免与客户端模块冲突
        ValidationAutoConfiguration.class
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