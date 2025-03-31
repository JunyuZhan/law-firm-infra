package com.lawfirm.common.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 公共模块 Web MVC 配置
 */
@Configuration(value = "commonWebConfig")
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
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
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Long类型转String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }
} 