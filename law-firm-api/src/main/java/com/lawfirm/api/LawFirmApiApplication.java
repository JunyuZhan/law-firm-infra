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
        // 1. Common模块（最先编译）
        "com.lawfirm.common.core",
        "com.lawfirm.common.util",
        "com.lawfirm.common.web",
        "com.lawfirm.common.data",
        "com.lawfirm.common.cache",
        "com.lawfirm.common.security",
        "com.lawfirm.common.log",
        "com.lawfirm.common.message",
        "com.lawfirm.common.test",

        // 2. Model模块（其次编译）
        "com.lawfirm.model.base",
        "com.lawfirm.model.organization",
        "com.lawfirm.model.personnel",
        "com.lawfirm.model.auth",
        "com.lawfirm.model.system",
        "com.lawfirm.model.log",
        "com.lawfirm.model.client",
        "com.lawfirm.model.document",
        "com.lawfirm.model.contract",
        "com.lawfirm.model.cases",
        "com.lawfirm.model.finance",
        "com.lawfirm.model.workflow",
        "com.lawfirm.model.storage",
        "com.lawfirm.model.search",
        "com.lawfirm.model.message",
        "com.lawfirm.model.knowledge",
        "com.lawfirm.model.ai",
        "com.lawfirm.model.schedule",
        "com.lawfirm.model.task",

        // 3. Core模块（再次编译）
        "com.lawfirm.core.audit",
        "com.lawfirm.core.storage",
        "com.lawfirm.core.search",
        "com.lawfirm.core.ai",
        "com.lawfirm.core.message",
        "com.lawfirm.core.workflow",

        // 4. 业务模块和API（最后编译）
        "com.lawfirm.system",
        "com.lawfirm.auth",
        "com.lawfirm.document",
        "com.lawfirm.personnel",
        "com.lawfirm.client",
        "com.lawfirm.cases",
        "com.lawfirm.contract",
        "com.lawfirm.finance",
        "com.lawfirm.knowledge",
        "com.lawfirm.schedule",
        "com.lawfirm.task",
        "com.lawfirm.api"
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