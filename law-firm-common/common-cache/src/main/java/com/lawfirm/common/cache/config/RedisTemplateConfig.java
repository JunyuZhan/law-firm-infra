package com.lawfirm.common.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis基础缓存模板配置类
 * 
 * 此配置类职责：
 * 1. 提供基础缓存服务所需的RedisTemplate
 * 2. 作为系统通用缓存的基础设施
 * 3. 不负责业务数据的存储
 */
@Slf4j
@Configuration("commonRedisTemplateConfig")
public class RedisTemplateConfig {

    /**
     * 通用缓存Redis模板
     * 用于系统级缓存，如会话、令牌等
     * 
     * 优先使用common-core模块提供的ObjectMapper实例，减少重复创建
     */
    @Bean(name = "commonCacheRedisTemplate")
    @Primary
    @ConditionalOnMissingBean(name = "commonCacheRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory,
            @Autowired(required = false) @Qualifier("commonCoreObjectMapper") ObjectMapper objectMapper) {
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // 如果存在commonCoreObjectMapper则使用，否则创建具有一致配置的新实例
        ObjectMapper mapper = null;
        if (objectMapper != null) {
            log.info("使用common-core模块提供的ObjectMapper用于Redis序列化");
            mapper = objectMapper.copy();
        } else {
            log.warn("未找到commonCoreObjectMapper，创建新的ObjectMapper实例用于Redis序列化");
            mapper = createRedisObjectMapper();
        }
        
        // 配置类型信息，确保Redis反序列化时能够正确恢复类型
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);
        
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        
        return template;
    }
    
    /**
     * 创建专用于Redis序列化的ObjectMapper
     * 配置与commonCoreObjectMapper保持一致
     */
    private ObjectMapper createRedisObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // 基础配置
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        // 日期时间处理
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        
        // 容错性配置
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        return mapper;
    }
}