package com.lawfirm.common.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.List;

/**
 * 公共模块 Web MVC 配置
 */
@Configuration(value = "commonWebConfig")
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${cors.allowed-origins:*}") // 从配置读取，默认为*
    private String[] allowedOrigins;
    
    @Autowired(required = false)
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;
    
    /**
     * 提供一个备用的ObjectMapper Bean
     * 当没有找到qualifier为objectMapper的Bean时使用
     */
    @Bean("webObjectMapper")
    @ConditionalOnMissingBean(name = "objectMapper")
    public ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();
    }
    
    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins) // 使用配置值
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Knife4j 资源映射
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false);
                
        registry.addResourceHandler("/doc.html", "/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .resourceChain(false);
                
        registry.addResourceHandler("/swagger-resources/**", "/v3/api-docs/**", "/knife4j/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .resourceChain(false);
    }

    /**
     * RestTemplate Bean提供方法
     * 供各模块使用的标准HTTP客户端
     */
    @Bean(name = "commonRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 消息转换器配置
     * 使用注入的ObjectMapper而不是创建新实例
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        
        // 使用注入的ObjectMapper的副本，避免修改全局配置
        // 如果注入失败，使用新创建的实例
        ObjectMapper converterObjectMapper = (objectMapper != null) ? 
            objectMapper.copy() : defaultObjectMapper();
        
        // Long类型转String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        converterObjectMapper.registerModule(simpleModule);
        
        jackson2HttpMessageConverter.setObjectMapper(converterObjectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }
} 