package com.lawfirm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API文档配置类
 * 专门处理Swagger/Knife4j相关资源和路径映射
 */
@Configuration
public class ApiDocConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 添加静态资源处理器
     * 确保Knife4j相关资源路径正确映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = contextPath;
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        
        // 处理不带context-path的路径
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        // 处理带context-path的路径
        if (!"/".equals(path)) {
            registry.addResourceHandler(path + "/doc.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler(path + "/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
            registry.addResourceHandler(path + "/swagger-resources/**")
                    .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
            registry.addResourceHandler(path + "/swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
            registry.addResourceHandler(path + "/v3/api-docs/**")
                    .addResourceLocations("classpath:/META-INF/resources/");
        }
    }

    /**
     * 添加视图控制器
     * 为Knife4j文档页面配置路径映射
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 简化访问路径
        registry.addViewController("/doc")
                .setViewName("redirect:/doc.html");
        
        if (!"/".equals(contextPath)) {
            registry.addViewController(contextPath + "/doc")
                    .setViewName("redirect:" + contextPath + "/doc.html");
        }
    }
} 