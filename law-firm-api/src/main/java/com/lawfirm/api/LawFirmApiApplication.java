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
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

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
        
        String activeProfiles = String.join(", ", env.getActiveProfiles());
        activeProfiles = activeProfiles.isEmpty() ? "default" : activeProfiles;
        
        log.info("\n----------------------------------------------------------\n" +
                "应用 '{}' 已启动! 访问地址:\n" +
                "本地: \t\t{}://localhost:{}{}\n" +
                "外部: \t\t{}://{}:{}{}\n" +
                "环境: \t\t{}\n" +
                "----------------------------------------------------------",
                env.getProperty("spring.application.name", "律师事务所管理系统"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                activeProfiles);
    }
}