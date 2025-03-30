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
     * 添加静态资源处理器 - 简化配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String apiPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        log.info("配置静态资源映射，上下文路径: {}", apiPrefix);
                
        // 通用静态资源
        registry.addResourceHandler(apiPrefix + "/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/public/");
    }
    
    /**
     * 添加视图控制器映射 - 简化配置
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // API文档页面的重定向配置将在专门的API文档配置类中实现
    }
} 