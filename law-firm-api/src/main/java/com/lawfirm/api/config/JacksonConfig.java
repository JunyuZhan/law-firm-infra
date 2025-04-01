package com.lawfirm.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * API文档专用Jackson配置
 * <p>
 * 处理API文档的JSON序列化问题
 * </p>
 */
@Slf4j
@Configuration("apiDocJacksonConfig") // 使用不同的bean名称避免冲突
public class JacksonConfig {

    /**
     * 创建API文档专用的ObjectMapper，避免与通用配置冲突
     */
    @Bean(name = "apiDocObjectMapper") // 改名避免冲突
    public ObjectMapper apiDocObjectMapper(Jackson2ObjectMapperBuilder builder) { // 方法名改为apiDocObjectMapper避免冲突
        log.info("创建API文档专用ObjectMapper");
        
        // 使用建造者模式创建ObjectMapper
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        
        // 序列化配置
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        // 反序列化配置
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        
        // 时间模块
        objectMapper.registerModule(new JavaTimeModule());
        
        // 注册处理循环引用的模块
        SimpleModule circularModule = new SimpleModule("CircularReferenceModule");
        objectMapper.registerModule(circularModule);
        
        // 保持SNAKE_CASE命名风格一致
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        
        log.info("API文档专用ObjectMapper创建完成");
        return objectMapper;
    }
} 