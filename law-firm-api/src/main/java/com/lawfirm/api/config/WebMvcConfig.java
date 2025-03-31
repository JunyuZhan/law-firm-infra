package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * WebMvc配置类
 * 处理全局编码设置
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 配置字符编码过滤器
     * 确保所有请求和响应使用UTF-8编码
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registrationBean.setFilter(characterEncodingFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0);  // 最高优先级
        return registrationBean;
    }

    /**
     * 配置字符串消息转换器
     * 确保字符串转换使用UTF-8编码
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
    
    /**
     * 配置JSON消息转换器
     * 确保JSON响应使用UTF-8编码
     */
    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }

    /**
     * 添加自定义的消息转换器到转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        converters.add(jsonConverter());
    }

    /**
     * 配置静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("配置资源处理器，上下文路径: {}", contextPath);
        
        // 1. 基本静态资源
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", 
                                     "classpath:/META-INF/resources/", 
                                     "classpath:/public/");
                
        // 2. 所有API文档相关资源 - 确保优先加载
        registry.addResourceHandler("/knife4j/**", "/webjars/**", "/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/", 
                                     "classpath:/META-INF/resources/webjars/");
    }
    
    /**
     * 简化视图控制器，统一入口为knife4j文档
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 统一使用knife4j作为文档入口
        String docPath = "/knife4j/doc.html";
        
        // 根路径重定向到knife4j文档
        registry.addRedirectViewController("/", docPath);
        registry.addRedirectViewController("/doc.html", docPath);
        registry.addRedirectViewController("/swagger-ui.html", docPath);
        registry.addRedirectViewController("/docs", docPath);
        
        // 上下文路径重定向
        if (contextPath != null && !contextPath.isEmpty() && !"/".equals(contextPath)) {
            registry.addRedirectViewController(contextPath, contextPath + docPath);
            registry.addRedirectViewController(contextPath + "/", contextPath + docPath);
            registry.addRedirectViewController(contextPath + "/doc.html", contextPath + docPath);
            registry.addRedirectViewController(contextPath + "/swagger-ui.html", contextPath + docPath);
        }
    }
} 