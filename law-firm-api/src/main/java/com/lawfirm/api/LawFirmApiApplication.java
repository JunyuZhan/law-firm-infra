package com.lawfirm.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 律师事务所API应用入口类
 * 
 * 模块加载说明：
 * 1. Common模块：通过依赖自动加载，提供基础功能
 * 2. Core模块：使用自动配置机制加载，不通过profile配置引入
 * 3. 业务模块：通过spring.profiles.include在application.yml中引入各模块配置
 * 
 * 【Spring Boot版本规划】
 * 当前使用Spring Boot 3.x版本。
 * 计划在2024年Q4前升级到最新稳定版本，以确保安全和功能更新。
 * OSS支持截止日期：2024-12-31
 */
@Slf4j
@MapperScan({
    "com.lawfirm.model.personnel.mapper",
    "com.lawfirm.model.task.mapper",
    "com.lawfirm.model.document.mapper",
    "com.lawfirm.model.log.mapper",
    "com.lawfirm.model.system.mapper",
    "com.lawfirm.model.search.mapper",
    "com.lawfirm.model.auth.mapper",
    "com.lawfirm.model.client.mapper",
    "com.lawfirm.model.contract.mapper",
    "com.lawfirm.model.cases.mapper",
    "com.lawfirm.model.finance.mapper",
    "com.lawfirm.model.knowledge.mapper",
    "com.lawfirm.model.schedule.mapper",
    "com.lawfirm.model.organization.mapper",
    "com.lawfirm.model.archive.mapper",
    "com.lawfirm.model.storage.mapper",
    "com.lawfirm.model.workflow.mapper",
    "com.lawfirm.model.message.mapper",
    "com.lawfirm.model.ai.mapper"
})
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
        // 不使用JPA
        JpaRepositoriesAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        // 使用自定义Redis配置
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class,
        // 使用自定义安全配置
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    }
)
public class LawFirmApiApplication {

    /**
     * 系统属性常量
     */
    private static final String ENCODING_UTF8 = "UTF-8";
    private static final String PROPERTY_TRUE = "true";

    static {
        // 在静态块中设置系统属性，确保尽早生效
        
        // 设置文件编码
        System.setProperty("file.encoding", ENCODING_UTF8);
        System.setProperty("sun.jnu.encoding", ENCODING_UTF8);
        
        // 设置语言和区域
        System.setProperty("user.language", "zh");
        System.setProperty("user.country", "CN");
        System.setProperty("user.timezone", "Asia/Shanghai");
        
        // 设置日志编码
        System.setProperty("logging.charset.console", ENCODING_UTF8);
        System.setProperty("logging.charset.file", ENCODING_UTF8);
        System.setProperty("spring.output.ansi.enabled", "always");
        
        // 配置Jansi以支持ANSI颜色输出
        System.setProperty("log4j.skipJansi", "false");
        
        // 确保使用Spring Boot内置的logback配置
        System.clearProperty("logback.configurationFile");
        
        // 配置服务器编码
        configureServerEncoding();
        
        // 配置Spring MVC相关设置
        configureSpringMvc();
        
        // 检测环境变量判断是否为开发环境
        String activeProfile = System.getProperty("spring.profiles.active", System.getenv("SPRING_PROFILES_ACTIVE"));
        boolean isDevelopment = activeProfile == null || activeProfile.contains("dev");
    }

    public static void main(String[] args) {
        // 设置系统属性
        setSystemProperties();
        
        SpringApplication app = new SpringApplication(LawFirmApiApplication.class);
        Environment env = app.run(args).getEnvironment();
        
        // 输出应用程序信息
        printApplicationInfo(env);
    }
    
    /**
     * 配置服务器编码相关属性
     */
    private static void configureServerEncoding() {
        // 配置服务器编码
        System.setProperty("server.servlet.encoding.charset", ENCODING_UTF8);
        System.setProperty("server.servlet.encoding.enabled", PROPERTY_TRUE);
        System.setProperty("server.servlet.encoding.force", PROPERTY_TRUE);
        
        // 确保Tomcat使用UTF-8
        System.setProperty("server.tomcat.uri-encoding", ENCODING_UTF8);
    }
    
    /**
     * 配置Spring MVC相关属性
     */
    private static void configureSpringMvc() {
        // 配置Spring MVC编码
        System.setProperty("spring.http.encoding.charset", ENCODING_UTF8);
        System.setProperty("spring.http.encoding.enabled", PROPERTY_TRUE);
        System.setProperty("spring.http.encoding.force", PROPERTY_TRUE);
        System.setProperty("spring.mvc.charset", ENCODING_UTF8);
        
        // 禁用HandlerMappingIntrospector缓存，解决Spring Framework兼容性问题
        System.setProperty("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
        System.setProperty("spring.mvc.problemdetails.enabled", "false");
        System.setProperty("spring.mvc.servlet.path", "/");
        System.setProperty("spring.security.filter.order", "15");
    }
    
    /**
     * 设置必要的系统属性
     */
    private static void setSystemProperties() {
        // 允许覆盖Bean定义
        System.setProperty("spring.main.allow-bean-definition-overriding", PROPERTY_TRUE);
        
        // 启用延迟初始化以提高启动性能
        System.setProperty("spring.main.lazy-initialization", "false");
        
        // 允许循环引用（开发阶段）
        System.setProperty("spring.main.allow-circular-references", PROPERTY_TRUE);
        
        // 设置日志级别
        System.setProperty("org.springframework.boot.logging.LoggingSystem", "org.springframework.boot.logging.logback.LogbackLoggingSystem");
        
        // 清理可能导致排除错误的属性
        System.clearProperty("spring.autoconfigure.exclude");
        
        // 允许静态资源
        System.setProperty("spring.web.resources.add-mappings", PROPERTY_TRUE);
    }
    
    /**
     * 打印应用程序信息
     */
    private static void printApplicationInfo(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "/");
        if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        
        log.info("\n----------------------------------------------------------\n\t" +
                 "应用程序 '{}' 已启动! 访问地址:\n\t" +
                "本地: \t\t{}://localhost:{}{}\n\t" +
                "外部: \t\t{}://{}:{}{}\n\t" +
                "环境: \t\t{}\n\t" +
                "编码: \t\t{}\n\t" +
                "区域: \t\t{}\n" +
                "----------------------------------------------------------",
                "律师事务所管理系统",
                protocol,
                serverPort,
                contextPath,
                protocol,
                "localhost",
                serverPort,
                contextPath,
                env.getActiveProfiles().length == 0 ? "默认配置" : env.getActiveProfiles()[0],
                System.getProperty("file.encoding"),
                System.getProperty("user.language") + "_" + System.getProperty("user.country"));
                
        // 输出系统版本信息
        log.info("系统版本信息: \n\t" +
                "Spring版本: {}\n\t" +
                "Java版本: {}\n\t" +
                "操作系统: {} ({})",
                org.springframework.core.SpringVersion.getVersion(),
                System.getProperty("java.version"),
                System.getProperty("os.name"),
                System.getProperty("os.arch"));
    }
}