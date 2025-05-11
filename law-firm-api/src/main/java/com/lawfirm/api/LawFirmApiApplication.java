package com.lawfirm.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 律师事务所API应用入口类
 * 
 * 模块加载说明：
 * 1. Common模块：通过依赖自动加载，提供基础功能
 * 2. Core模块：使用自动配置机制加载，不通过profile配置引入
 * 3. 业务模块：通过spring.profiles.include在application.yml中引入各模块配置
 */
@Slf4j
@SpringBootApplication(
    scanBasePackages = {
        // 1. Common模块（基础设施层）
        "com.lawfirm.common.core",
        "com.lawfirm.common.util",
        "com.lawfirm.common.web",
        "com.lawfirm.common.data",
        "com.lawfirm.common.cache",
        "com.lawfirm.common.security",
        "com.lawfirm.common.log",
        "com.lawfirm.common.message",
        "com.lawfirm.common.test",

        // 2. Model模块（数据模型层）
        "com.lawfirm.model",

        // 3. Core模块（通过自动配置加载，不通过profile引入）
        "com.lawfirm.core",

        // 4. 业务模块（通过profile引入配置）
        "com.lawfirm.api",
        "com.lawfirm.auth",
        "com.lawfirm.system",
        "com.lawfirm.document",
        "com.lawfirm.personnel",
        "com.lawfirm.client",
        "com.lawfirm.cases",
        "com.lawfirm.contract",
        "com.lawfirm.finance",
        "com.lawfirm.knowledge",
        "com.lawfirm.schedule",
        "com.lawfirm.task",
        "com.lawfirm.analysis",
        "com.lawfirm.archive"
    },
    exclude = {
        // 1. 安全相关
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
        
        // 2. 数据库相关
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        
        // 3. Redis相关 - 禁用官方的自动配置，使用我们自己的配置
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
    }
)
public class LawFirmApiApplication {

    public static void main(String[] args) {
        // 设置系统属性，禁用工厂类型错误检查，解决"factoryBeanObjectType"错误
        System.setProperty("spring.main.allow-bean-definition-overriding", "true");
        System.setProperty("spring.main.lazy-initialization", "false");
        System.setProperty("spring.factories.ignore-errors", "true");
        
        // 启动应用
        SpringApplication application = new SpringApplication(LawFirmApiApplication.class);
        
        // 根据环境变量决定是否开启延迟初始化
        boolean isDockerEnv = Optional.ofNullable(System.getenv("SPRING_PROFILES_ACTIVE"))
                .map(profile -> profile.contains("docker"))
                .orElse(false);
                
        if (isDockerEnv) {
            log.info("检测到Docker环境，启用资源优化配置");
        }
        
        Environment env = application.run(args).getEnvironment();
        logApplicationStartup(env);
    }
    
    /**
     * 输出应用启动信息，包括访问地址、激活的配置文件等
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
}