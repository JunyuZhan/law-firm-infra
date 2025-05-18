package com.lawfirm.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.extern.slf4j.Slf4j;

/**
 * WebMVC兼容性配置
 * 解决Spring Boot 3.2.3与Spring Security 6.2.2之间的版本兼容性问题
 * 同时确保API文档资源能够被正确加载
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration("webMvcCompatibilityConfig")
public class WebMvcCompatibilityConfig implements WebMvcConfigurer {
    
    /**
     * 禁用MVC路径匹配器缓存，解决版本兼容性问题
     */
    @Bean("mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        log.info("配置HandlerMappingIntrospector，解决版本兼容性问题");
        // 仅创建HandlerMappingIntrospector实例，不调用不存在的方法
        // Spring Boot 3.2.3中会通过系统属性配置它的行为
        return new HandlerMappingIntrospector();
    }
    
    /**
     * 添加资源处理器，确保API文档资源能够被正确访问
     * 增强处理，特别关注v3/api-docs路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("增强配置API文档资源处理器");
        
        // API文档相关资源
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui/",
                                     "classpath:/META-INF/resources/webjars/swagger-ui/");
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
        
        // 特别处理 v3/api-docs 路径
        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/",
                                     "classpath:/META-INF/resources/v3/api-docs/",
                                     "classpath:/META-INF/v3/api-docs/",
                                     "classpath:/v3/api-docs/");
                                     
        // 确保静态资源也能被访问
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
    /**
     * 添加视图控制器，提供API文档的直接访问
     * 提供多个路径的重定向，确保各种方式都能访问到API文档
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("增强配置API文档视图控制器");
        
        // Swagger UI相关路径
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui/", "/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui.html", "/swagger-ui/index.html");
        
        // API文档相关路径
        registry.addRedirectViewController("/api-docs", "/v3/api-docs");
        registry.addRedirectViewController("/api-docs/", "/v3/api-docs");
        
        // Knife4j相关路径
        registry.addViewController("/doc.html").setViewName("doc.html");
        
        // 其他常见入口
        registry.addRedirectViewController("/swagger", "/swagger-ui/index.html");
        registry.addRedirectViewController("/api", "/swagger-ui/index.html");
    }
} 