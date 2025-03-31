package com.lawfirm.api.config.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生产环境部署检查配置类
 * 用于在应用启动时执行一系列安全检查，确保生产环境符合安全最佳实践
 */
@Slf4j
@Configuration
@Profile("prod")
@Order(1)
public class DeploymentCheckConfig implements CommandLineRunner {

    private final Environment environment;

    @Value("${spring.datasource.username:}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Value("${JWT_SECRET:}")
    private String jwtSecret;

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${logging.file.path:/var/log/law-firm}")
    private String logPath;

    public DeploymentCheckConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) {
        log.info("======= 生产环境部署安全检查开始 =======");
        boolean hasIssues = false;

        hasIssues |= checkActiveProfiles();
        hasIssues |= checkSecurity();
        hasIssues |= checkSystemEnvironment();
        hasIssues |= checkLogDirectory();
        hasIssues |= writeDeploymentRecord();

        if (hasIssues) {
            log.warn("======= 生产环境安全检查完成，发现潜在问题 =======");
        } else {
            log.info("======= 生产环境安全检查完成，未发现问题 =======");
        }
    }

    /**
     * 检查激活的配置文件
     */
    private boolean checkActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        boolean hasDevProfile = Arrays.stream(activeProfiles).anyMatch(profile -> 
                profile.startsWith("dev") || "local".equals(profile) || "test".equals(profile));
        
        if (hasDevProfile) {
            log.error("安全警告: 检测到开发环境配置文件激活: {}", String.join(", ", activeProfiles));
            return true;
        }
        
        if (!Arrays.asList(activeProfiles).contains("prod")) {
            log.warn("注意: 未检测到prod配置文件激活，当前配置文件: {}", String.join(", ", activeProfiles));
            return true;
        }
        
        log.info("配置文件检查通过: {}", String.join(", ", activeProfiles));
        return false;
    }

    /**
     * 检查安全配置
     */
    private boolean checkSecurity() {
        boolean hasIssues = false;
        
        // 检查JWT配置
        String jwtSecretEnv = System.getenv("JWT_SECRET");
        if (jwtSecretEnv == null || jwtSecretEnv.isEmpty()) {
            log.warn("安全警告: JWT_SECRET环境变量未设置，应使用环境变量而非配置文件");
            hasIssues = true;
        }
        
        // 检查数据库凭据
        if ((dbUsername != null && dbUsername.equals("root")) || 
            (dbPassword != null && (dbPassword.length() < 8 || dbPassword.equalsIgnoreCase("password")))) {
            log.warn("安全警告: 数据库凭据不符合安全要求");
            hasIssues = true;
        }
        
        // 检查安全提供者
        List<String> providers = Arrays.stream(Security.getProviders())
                                      .map(Provider::getName)
                                      .collect(Collectors.toList());
        log.info("安全提供者: {}", String.join(", ", providers));
        
        return hasIssues;
    }

    /**
     * 检查系统环境
     */
    private boolean checkSystemEnvironment() {
        boolean hasIssues = false;
        
        // 检查端口
        if (serverPort == 8080) {
            log.warn("安全建议: 应用使用默认端口(8080)，建议更改为非标准端口");
            hasIssues = true;
        }
        
        // 检查Java版本
        String javaVersion = System.getProperty("java.version");
        log.info("Java版本: {}", javaVersion);
        
        // 检查操作系统
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        log.info("操作系统: {} {}", osName, osVersion);
        
        return hasIssues;
    }

    /**
     * 检查日志目录
     */
    private boolean checkLogDirectory() {
        boolean hasIssues = false;
        
        // 检查日志目录权限
        Path logDirPath = Paths.get(logPath);
        File logDir = logDirPath.toFile();
        
        if (!logDir.exists()) {
            try {
                Files.createDirectories(logDirPath);
                log.info("已创建日志目录: {}", logPath);
            } catch (Exception e) {
                log.error("无法创建日志目录: {}, 错误: {}", logPath, e.getMessage());
                hasIssues = true;
            }
        } else if (!logDir.canWrite()) {
            log.error("日志目录没有写入权限: {}", logPath);
            hasIssues = true;
        } else {
            log.info("日志目录检查通过: {}", logPath);
        }
        
        return hasIssues;
    }

    /**
     * 写入部署记录
     */
    private boolean writeDeploymentRecord() {
        try {
            Path deploymentLogPath = Paths.get(logPath, "deployment.log");
            LocalDateTime now = LocalDateTime.now();
            String deploymentRecord = String.format("[%s] 应用部署: 版本=%s, 环境=%s, 端口=%d\n",
                    now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    environment.getProperty("app.version", "未知"),
                    String.join(",", environment.getActiveProfiles()),
                    serverPort);
            
            Files.write(deploymentLogPath, deploymentRecord.getBytes(), 
                    Files.exists(deploymentLogPath) 
                        ? java.nio.file.StandardOpenOption.APPEND 
                        : java.nio.file.StandardOpenOption.CREATE);
            
            log.info("部署记录已写入: {}", deploymentLogPath);
            return false;
        } catch (Exception e) {
            log.error("无法写入部署记录: {}", e.getMessage());
            return true;
        }
    }
} 