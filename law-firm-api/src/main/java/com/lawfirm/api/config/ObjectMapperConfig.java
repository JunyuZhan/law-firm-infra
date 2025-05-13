package com.lawfirm.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * ObjectMapper配置类
 * 用于解决系统中多个@Primary ObjectMapper冲突问题
 */
@Slf4j
@Configuration
public class ObjectMapperConfig {

    /**
     * 主要ObjectMapper实例
     * 作为系统中唯一一个标记为@Primary的ObjectMapper实例
     */
    @Bean
    @Primary
    @Qualifier("primaryObjectMapper")
    public ObjectMapper primaryObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置Jackson处理
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        log.info("创建主要ObjectMapper实例，解决多个@Primary冲突");
        return objectMapper;
    }
} 