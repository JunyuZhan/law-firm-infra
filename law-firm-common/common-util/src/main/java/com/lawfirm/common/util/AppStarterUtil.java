package com.lawfirm.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Optional;

/**
 * 应用启动工具类
 * <p>
 * 提供简化的应用启动方法和环境检查功能
 */
@Slf4j
public class AppStarterUtil {

    /**
     * 简化的应用启动方法
     *
     * @param applicationClass 应用启动类
     * @param args             启动参数
     * @return 应用上下文环境
     */
    public static Environment start(Class<?> applicationClass, String[] args) {
        // 检测当前激活的配置文件
        String activeProfile = detectActiveProfile(args);
        log.info("检测到当前激活的环境配置: {}", activeProfile);

        // 启动应用
        SpringApplication app = new SpringApplication(applicationClass);
        Environment env = app.run(args).getEnvironment();
        
        // 输出启动信息
        logApplicationStartup(env);
        
        return env;
    }
    
    /**
     * 检测当前激活的配置文件
     *
     * @param args 启动参数
     * @return 当前激活的配置文件，如果未指定则返回"develop"
     */
    private static String detectActiveProfile(String[] args) {
        // 首先检查命令行参数
        String profile = Arrays.stream(args)
                .filter(arg -> arg.contains("--spring.profiles.active="))
                .findFirst()
                .map(arg -> arg.split("=")[1])
                .orElse(null);
                
        // 如果命令行没有指定，则检查环境变量
        if (!StringUtils.hasText(profile)) {
            profile = System.getenv("SPRING_PROFILES_ACTIVE");
        }
        
        // 如果环境变量也没有指定，则使用默认值
        if (!StringUtils.hasText(profile)) {
            profile = "develop";
        }
        
        return profile;
    }
    
    /**
     * 输出应用启动信息
     *
     * @param env 应用环境
     */
    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                .map(key -> "https")
                .orElse("http");
                
        String serverPort = env.getProperty("server.port", "8080");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(path -> !path.isEmpty())
                .orElse("/");
                
        if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("无法确定主机地址", e);
        }
        
        log.info("\n----------------------------------------------------------\n\t" +
                "应用 '{}' 正在运行! 访问地址:\n\t" +
                "本地: \t\t{}://localhost:{}{}\n\t" +
                "外部: \t\t{}://{}:{}{}\n\t" +
                "配置: \t\t{}\n\t" +
                "环境: \t\t{}\n" +
                "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                env.getActiveProfiles(),
                env.getProperty("spring.profiles.active"));
    }
    
    /**
     * 检查数据库连接
     *
     * @param url      数据库URL
     * @param username 用户名
     * @param password 密码
     * @return 连接是否成功
     */
    public static boolean checkDatabaseConnection(String url, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                return connection.isValid(5);
            }
        } catch (Exception e) {
            log.error("数据库连接失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 从环境中获取数据库连接参数
     *
     * @param env Spring环境
     * @return 数据库连接参数数组 [url, username, password]
     */
    public static String[] getDatabaseConnectionParams(Environment env) {
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        
        return new String[]{url, username, password};
    }
} 