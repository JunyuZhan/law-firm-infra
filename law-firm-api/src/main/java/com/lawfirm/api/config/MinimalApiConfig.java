package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 最小API配置
 * 只保留必要的Web和JSON配置
 */
@Configuration
public class MinimalApiConfig {

    /**
     * 配置跨域支持
     */
    @Bean(name = "apiCorsConfigurer")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
    
    /**
     * 配置Jackson序列化
     * 
     * 注意：Bean名称已改为apiObjectMapper，避免与JacksonConfig冲突
     * 且仅在没有ObjectMapper Bean时创建
     */
    @Bean("apiObjectMapper")
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper apiObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();
    }
} 