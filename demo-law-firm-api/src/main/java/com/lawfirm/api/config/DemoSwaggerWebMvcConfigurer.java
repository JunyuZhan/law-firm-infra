package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * API文档视图配置
 * 负责API文档的视图跳转，避免与HomeController等其他组件冲突
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration("demoSwaggerWebMvcConfigurer")
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true", matchIfMissing = false)
public class DemoSwaggerWebMvcConfigurer implements WebMvcConfigurer {
    
    @Value("${spring.application.name:律师事务所管理系统}")
    private String applicationName;
    
    @Value("${knife4j.setting.language:zh_cn}")
    private String language;
    
    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerPath;
    
    @Value("${knife4j.setting.enable-home-custom:false}")
    private boolean enableHomeCustom;
    
    @Value("${knife4j.setting.home-custom-path:/api/welcome}")
    private String homeCustomPath;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API文档页面跳转");
        
        // 完全移除根路径跳转，避免与欢迎页冲突
        log.info("根路径重定向已禁用，启用自定义首页: {}", enableHomeCustom);
        
        // 使用更明确的路径前缀，避免冲突
        registry.addViewController("/api-docs/swagger")
                .setViewName("redirect:" + swaggerPath);
        
        // 使用标准化的文档访问路径
        registry.addViewController("/api-docs/ui")
                .setViewName("redirect:/doc.html");
                
        // 添加自定义欢迎页路径
        if (enableHomeCustom && homeCustomPath != null && !homeCustomPath.isEmpty()) {
            log.info("配置Knife4j自定义首页路径: {}", homeCustomPath);
            registry.addViewController("/api/welcome")
                    .setViewName("forward:/welcome.html");
        }
                
        // 设置较低的优先级，避免覆盖其他更重要的视图控制器
        registry.setOrder(Ordered.LOWEST_PRECEDENCE);
    }
} 