package com.lawfirm.api;

import com.lawfirm.api.config.dev.DevSecurityAutoConfiguration;
import com.lawfirm.auth.config.AuthAutoConfiguration;
import com.lawfirm.core.workflow.config.WorkflowDisableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 律师事务所API应用启动类
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.lawfirm"})
@Import({
    DevSecurityAutoConfiguration.class,
    AuthAutoConfiguration.class,
    WorkflowDisableAutoConfiguration.class
})
@ConfigurationPropertiesScan("com.lawfirm")
public class LawFirmApiApplication {

    /**
     * 为密码编码器提供BCrypt实现
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        try {
            SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
            application.setLazyInitialization(true);
            ApplicationContext ctx = application.run(args);
            
            // 检查字符编码设置
            log.info("JVM默认字符编码: {}", System.getProperty("file.encoding"));
            log.info("本地默认字符集: {}", java.nio.charset.Charset.defaultCharset().name());
            
            // 打印环境信息
            Environment env = ctx.getEnvironment();
            printEnvironmentInfo(env);
        } catch (Exception e) {
            log.error("应用启动失败: {}", e.getMessage(), e);
            // 打印更详细的根因
            Throwable cause = e.getCause();
            while (cause != null) {
                log.error("故障原因: {}", cause.getMessage());
                cause = cause.getCause();
            }
        }
    }
    
    /**
     * 打印环境信息，帮助排查部署和运行问题
     */
    private static void printEnvironmentInfo(Environment env) {
        String[] activeProfiles = env.getActiveProfiles();
        String applicationName = env.getProperty("spring.application.name", "law-firm-api");
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String hostAddress = "localhost";
        
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("无法解析主机地址: {}", e.getMessage());
        }

        log.info("\n----------------------------------------------------------\n" +
                applicationName + " 应用启动成功！\n" +
                "环境: \t" + Arrays.toString(activeProfiles) + "\n" +
                "地址: \thttp://{}:{}{}\n" +
                "----------------------------------------------------------",
                hostAddress, port, contextPath);
                
        // 检查是否为生产环境，提供额外警告
        if (Arrays.asList(activeProfiles).contains("prod")) {
            log.warn("应用正在生产环境运行！请确保所有安全配置已完成并启用。");
        }
    }
}