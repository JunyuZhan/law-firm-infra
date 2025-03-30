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
@SpringBootApplication(
    scanBasePackages = "com.lawfirm"
)
@ConfigurationPropertiesScan("com.lawfirm")
@Import({
    AuthAutoConfiguration.class, 
    WorkflowDisableAutoConfiguration.class,
    DevSecurityAutoConfiguration.class // 导入开发环境安全自动配置
})
@ComponentScan(
    basePackages = {"com.lawfirm"},
    excludeFilters = {
        // 排除所有工作流相关的类
        @ComponentScan.Filter(
            type = FilterType.REGEX, 
            pattern = "com\\.lawfirm\\.core\\.workflow\\..*"
        ),
        // 排除所有Flowable相关的配置类
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*FlowableConfig"
        ),
        // 排除与Flowable相关的自动配置
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "org\\.flowable\\.spring\\.boot\\..*"
        ),
        // 排除auth模块中的Dev开头的配置类
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "com\\.lawfirm\\.auth\\.config\\.Dev.*"
        )
    }
)
@EnableScheduling
@EnableWebSecurity
@EnableMethodSecurity
public class LawFirmApiApplication {

    static {
        // 强制设置字符编码为UTF-8，解决中文乱码问题
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("spring.http.encoding.charset", "UTF-8");
        System.setProperty("spring.http.encoding.enabled", "true");
        System.setProperty("spring.http.encoding.force", "true");
        
        // 1. 禁用Flowable和工作流
        System.setProperty("flowable.enabled", "false");
        System.setProperty("lawfirm.workflow.enabled", "false");
        System.setProperty("flowable.check-process-definitions", "false");
        System.setProperty("flowable.database-schema-update", "false");
        
        // 2. 启用存储服务
        System.setProperty("lawfirm.storage.enabled", "true");
        
        // 3. 系统排除配置
        System.setProperty("spring.autoconfigure.exclude", 
            "org.flowable.spring.boot.FlowableAutoConfiguration," +
            "org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration," +
            "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration," +
            "org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration," +
            "com.lawfirm.core.workflow.config.FlowableConfig");
            
        // 4. 明确设置profile和简化安全配置
        System.setProperty("spring.profiles.active", "dev-mysql");
        System.setProperty("dev.auth.simplified-security", "true");
        System.setProperty("security.enable", "false");
        System.setProperty("spring.security.enabled", "false");
        System.setProperty("security.basic.enabled", "false");
        
        // 5. 添加JVM内存参数
        if (System.getProperty("java.vm.name") != null && System.getProperty("java.vm.name").contains("OpenJDK")) {
            // 如果是OpenJDK，添加特殊编码参数
            System.setProperty("sun.stdout.encoding", "UTF-8");
            System.setProperty("sun.stderr.encoding", "UTF-8");
        }
        
        // 6. 添加API文档相关属性
        System.setProperty("springdoc.api-docs.enabled", "true");
        System.setProperty("springdoc.swagger-ui.enabled", "true");
        System.setProperty("knife4j.enable", "true");
        System.setProperty("knife4j.production", "false");
        System.setProperty("springdoc.swagger-ui.path", "/doc.html");
        System.setProperty("springdoc.api-docs.path", "/v3/api-docs");
    }
    
    public static void main(String[] args) {
        // 验证环境默认编码
        if (!StandardCharsets.UTF_8.name().equals(System.getProperty("file.encoding"))) {
            log.warn("系统默认编码不是UTF-8，当前编码: {}", System.getProperty("file.encoding"));
            log.warn("这可能导致中文乱码问题，尝试强制设置UTF-8编码");
            System.setProperty("file.encoding", "UTF-8");
        }
        
        // 设置系统属性
        setupSystemProperties();
        
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
            System.err.println("应用启动失败: " + e.getMessage());
            e.printStackTrace();
            // 打印更详细的根因
            Throwable cause = e.getCause();
            while (cause != null) {
                System.err.println("故障原因: " + cause.getMessage());
                cause = cause.getCause();
            }
        }
    }
    
    /**
     * 设置系统级属性，用于确保应用在各环境中表现一致
     */
    private static void setupSystemProperties() {
        // 文件和UTF-8编码相关设置
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("spring.mandatory-file-encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        
        // 设置时区
        System.setProperty("user.timezone", "Asia/Shanghai");
        
        // 增强JVM安全性设置
        System.setProperty("java.security.egd", "file:/dev/./urandom");
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
            log.info("\n----------------------------------------------------------\n" +
                    "应用正在生产环境运行\n" +
                    "请确保已完成所有安全检查和性能调优\n" +
                    "----------------------------------------------------------");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}