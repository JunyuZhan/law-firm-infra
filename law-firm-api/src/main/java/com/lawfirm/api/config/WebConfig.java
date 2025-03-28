package com.lawfirm.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Web配置类
 * 处理静态资源和文档路径
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    /**
     * 提供ObjectMapper Bean
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    /**
     * 提供Jackson消息转换器
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }
    
    /**
     * 提供String消息转换器
     */
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setWriteAcceptCharset(false);
        return converter;
    }

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
    
    /**
     * 配置CORS跨域请求
     * 允许所有来源访问API文档相关路径
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false) // 不允许发送cookies
                .maxAge(3600); // 预检请求结果缓存1小时
    }
    
    /**
     * 配置内容协商
     * 确保API文档响应为JSON类型
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    /**
     * 配置HTTP消息转换器
     * 确保API文档响应可以正确转换为JSON
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter(objectMapper()));
    }
}