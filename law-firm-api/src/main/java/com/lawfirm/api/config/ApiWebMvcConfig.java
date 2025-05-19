package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
 * Web MVC配置类
 * 处理资源映射、视图控制器和兼容性问题
 * 
 * @author LawFirm Dev Team
 */
@Slf4j
@Configuration("apiWebMvcConfig")
@Order(90) // 确保优先于common-web模块的配置(默认100)
public class ApiWebMvcConfig implements WebMvcConfigurer {
    
    /**
     * 禁用MVC路径匹配器缓存，解决版本兼容性问题
     */
    @Bean("mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        log.info("配置HandlerMappingIntrospector，解决版本兼容性问题");
        return new HandlerMappingIntrospector();
    }
    
    /**
     * 添加资源处理器
     * 统一处理API文档和静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("配置API文档和静态资源处理器");
        
        // API文档资源 - Knife4j和SpringDoc
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui/");

        // knife4j专用资源        
        registry.addResourceHandler("/knife4j/**")
                .addResourceLocations("classpath:/META-INF/resources/knife4j/");
        
        // v3/api-docs统一处理
        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        // 静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
    /**
     * 添加视图控制器
     * 提供API文档的多种访问路径
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API文档视图控制器");
        
        // API文档数据路径
        registry.addRedirectViewController("/api-docs", "/v3/api-docs");
        
        // Knife4j路径
        registry.addViewController("/doc.html").setViewName("doc.html");
        
        // 简化访问路径
        registry.addRedirectViewController("/api", "/doc.html");
        
        // 添加诊断端点重定向
        registry.addRedirectViewController("/api/doc-config", "/api/doc-config");
    }
    
    /**
     * 配置内容协商
     * 确保API文档相关资源返回正确的内容类型
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // API文档默认使用application/json
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("html", MediaType.TEXT_HTML)
            // 明确指定API文档相关路径的内容类型
            .mediaType("yaml", MediaType.valueOf("application/yaml"))
            .favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(false);
    }
    
    /**
     * 配置HTTP消息转换器
     * 确保正确处理JSON响应
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // API层不再配置转换器，使用common-web中的配置
        // 避免与common-web模块的配置冲突
    }
} 