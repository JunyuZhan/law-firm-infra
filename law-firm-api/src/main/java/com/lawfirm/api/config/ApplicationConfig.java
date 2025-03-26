package com.lawfirm.api.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 应用配置处理器，用于在Spring Boot启动前设置系统属性
 */
@Component
public class ApplicationConfig implements EnvironmentPostProcessor {
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties props = new Properties();
        
        // 设置应用信息，避免直接使用系统用户名
        props.put("spring.application.user", "lawfirm-user");
        
        // 设置日志编码
        props.put("logging.charset.console", "UTF-8");
        props.put("logging.charset.file", "UTF-8");
        
        // 设置控制台输出颜色
        props.put("spring.output.ansi.enabled", "always");
        
        environment.getPropertySources().addFirst(new PropertiesPropertySource("applicationConfig", props));
    }
} 