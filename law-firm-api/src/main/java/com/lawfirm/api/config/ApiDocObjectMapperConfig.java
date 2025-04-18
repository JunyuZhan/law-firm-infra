package com.lawfirm.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.text.SimpleDateFormat;

/**
 * API文档对象映射器配置
 * 
 * 为API文档提供专用的ObjectMapper，避免与应用程序中的ObjectMapper冲突。
 * 解决由于存在多个ObjectMapper Bean导致的冲突问题
 */
@Configuration
public class ApiDocObjectMapperConfig {

    /**
     * 配置文档专用的ObjectMapper
     * 
     * 不使用@Primary注解，避免覆盖其他模块中可能存在的ObjectMapper
     */
    @Bean("apiDocObjectMapper")
    @ConditionalOnMissingBean(name = "apiDocObjectMapper")
    public ObjectMapper apiDocObjectMapper() {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .createXmlMapper(false)
                .build();
        
        // 配置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        
        // 注册Java 8时间模块
        objectMapper.registerModule(new JavaTimeModule());
        
        // 序列化配置
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // 反序列化配置
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        return objectMapper;
    }

    /**
     * API文档专用的Jackson2ObjectMapperBuilder
     */
    @Bean("apiDocObjectMapperBuilder")
    @ConditionalOnMissingBean(name = "apiDocObjectMapperBuilder")
    public Jackson2ObjectMapperBuilder apiDocObjectMapperBuilder(@Qualifier("apiDocObjectMapper") ObjectMapper objectMapper) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.configure(objectMapper);
        return builder;
    }
} 