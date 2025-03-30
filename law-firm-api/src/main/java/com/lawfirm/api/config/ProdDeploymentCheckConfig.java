package com.lawfirm.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 生产环境部署检查配置
 * 用于在应用启动时检查生产环境配置是否符合最佳实践
 */
@Slf4j
@Configuration
@Profile("prod")
@Order(1)
public class ProdDeploymentCheckConfig implements CommandLineRunner {

    private final Environment environment;

    @Value("${spring.datasource.username:}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:}")
    private String datasourcePassword;

    @Value("${security.jwt.token-secret:}")
    private String jwtSecret;

    @Value("${server.port:8080}")
    private int serverPort;

    public ProdDeploymentCheckConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) {
        log.info("========== 生产环境部署检查开始 ==========");
        
        checkActiveProfiles();
        checkSecurity();
        checkDatabase();
        checkJwtConfiguration();
        checkServerConfiguration();
        
        log.info("========== 生产环境部署检查完成 ==========");
        
        // 打印应用访问信息
        printApplicationInfo();
    }
    
    private void checkActiveProfiles() {
        log.info("检查激活的配置文件: {}", Arrays.toString(environment.getActiveProfiles()));
        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            log.warn("警告: 生产环境配置未激活，当前配置为: {}", Arrays.toString(environment.getActiveProfiles()));
        }
    }
    
    private void checkSecurity() {
        // 检查TLS配置
        Boolean sslEnabled = environment.getProperty("server.ssl.enabled", Boolean.class, false);
        if (!sslEnabled) {
            log.warn("警告: SSL/TLS 未启用，建议在生产环境中启用HTTPS");
        }
        
        // 检查安全标头
        String securityFilter = environment.getProperty("security.filter.enabled", "false");
        if ("false".equals(securityFilter)) {
            log.warn("警告: 安全过滤器未启用，建议在生产环境中启用安全过滤器");
        }
        
        // 检查JVM安全配置
        String javaVersion = System.getProperty("java.version");
        log.info("Java版本: {}", javaVersion);
        
        // 检查安全提供程序
        List<String> providerInfos = new ArrayList<>();
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            providerInfos.add(provider.getName() + " (" + provider.getInfo() + ")");
        }
        log.info("安全提供程序: {}", providerInfos);
    }
    
    private void checkDatabase() {
        log.info("检查数据库配置...");
        
        // 检查数据库凭据是否使用环境变量
        if (datasourceUsername != null && datasourceUsername.equals("root")) {
            log.warn("警告: 数据库用户名使用默认值 'root'，建议使用自定义用户名");
        }
        
        if (datasourcePassword != null && datasourcePassword.equals("password")) {
            log.error("错误: 数据库密码使用默认值，请在生产环境中使用强密码");
        }
        
        // 检查数据库连接池配置
        Integer maxPoolSize = environment.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class, 10);
        if (maxPoolSize > 50) {
            log.warn("警告: 数据库连接池最大连接数 ({}) 过大，可能导致数据库连接资源耗尽", maxPoolSize);
        }
    }
    
    private void checkJwtConfiguration() {
        log.info("检查JWT配置...");
        
        // 检查JWT密钥
        if (jwtSecret != null && jwtSecret.equals("defaultsecretkey12345678901234567890")) {
            log.error("错误: JWT密钥使用默认值，请在生产环境中使用随机生成的强密钥");
        }
        
        // 检查JWT过期时间
        Integer tokenExpiration = environment.getProperty("security.jwt.token-expiration-seconds", Integer.class, 86400);
        if (tokenExpiration > 86400) {
            log.warn("警告: JWT令牌过期时间 ({} 秒) 过长，建议不超过24小时", tokenExpiration);
        }
    }
    
    private void checkServerConfiguration() {
        log.info("检查服务器配置...");
        
        // 检查Tomcat线程配置
        Integer maxThreads = environment.getProperty("server.tomcat.max-threads", Integer.class, 200);
        if (maxThreads < 50) {
            log.warn("警告: Tomcat最大线程数 ({}) 过小，可能影响并发处理能力", maxThreads);
        } else if (maxThreads > 500) {
            log.warn("警告: Tomcat最大线程数 ({}) 过大，可能导致资源耗尽", maxThreads);
        }
        
        // 检查会话超时
        String sessionTimeout = environment.getProperty("server.servlet.session.timeout", "30m");
        log.info("会话超时时间: {}", sessionTimeout);
    }
    
    private void printApplicationInfo() {
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String hostAddress = "localhost";
        
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("无法获取主机地址: {}", e.getMessage());
        }
        
        log.info("应用启动完成，可以通过以下地址访问:");
        log.info("本地访问: http://localhost:{}{}", serverPort, contextPath);
        log.info("局域网访问: http://{}:{}{}", hostAddress, serverPort, contextPath);
        
        Boolean apiDocsEnabled = environment.getProperty("springdoc.api-docs.enabled", Boolean.class, false);
        if (apiDocsEnabled) {
            log.info("API文档地址: http://{}:{}{}/api-docs", hostAddress, serverPort, contextPath);
            log.warn("警告: API文档在生产环境中已启用，请确保已配置适当的访问控制");
        }
    }
} 