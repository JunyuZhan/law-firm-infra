package com.lawfirm.common.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.util.SpringUtils;

/**
 * JSON工具类，基于Jackson
 */
@Slf4j
@Component("commonJsonUtils")
public class JsonUtils {

    // 静态引用，由@PostConstruct初始化
    private static volatile ObjectMapper staticObjectMapper;
    
    // 读写锁，确保线程安全
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = LOCK.writeLock();

    @Autowired
    @Qualifier("commonCoreObjectMapper")
    private ObjectMapper objectMapper;
    
    /**
     * 初始化静态ObjectMapper
     */
    @PostConstruct
    public void init() {
        WRITE_LOCK.lock();
        try {
            if (staticObjectMapper == null) {
                staticObjectMapper = objectMapper;
                log.info("JsonUtils初始化完成，注入commonCoreObjectMapper");
            }
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    /**
     * 获取ObjectMapper实例
     * 使用双重检查锁定模式确保线程安全
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper localMapper = staticObjectMapper;
        if (localMapper != null) {
            return localMapper;
        }
        
        WRITE_LOCK.lock();
        try {
            localMapper = staticObjectMapper;
            if (localMapper != null) {
                return localMapper;
            }
            
            // 从Spring容器获取或创建新实例
            try {
                // 从Spring容器获取ObjectMapper Bean
                ObjectMapper beanMapper = SpringUtils.getBean("commonCoreObjectMapper", ObjectMapper.class);
                staticObjectMapper = beanMapper;
                log.info("从Spring容器获取到ObjectMapper成功");
                return staticObjectMapper;
            } catch (Exception e) {
                log.warn("无法从Spring容器获取ObjectMapper，使用默认配置：{}", e.getMessage());
                ObjectMapper defaultMapper = new ObjectMapper();
                defaultMapper.findAndRegisterModules();
                staticObjectMapper = defaultMapper;
                return staticObjectMapper;
            }
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BusinessException("JSON序列化异常", e);
        }
    }
    
    /**
     * JSON字符串转对象
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(text, clazz);
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    /**
     * JSON字符串转对象
     */
    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(text, typeReference);
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    /**
     * JSON字符串转List
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (!StringUtils.hasText(text)) {
            return new ArrayList<>();
        }
        try {
            return getObjectMapper().readValue(text, getObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    /**
     * JSON字符串转Map
     */
    public static <K, V> Map<K, V> parseMap(String text, Class<K> keyClazz, Class<V> valueClazz) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(text, getObjectMapper().getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz));
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    /**
     * JSON字符串转Map，专门用于String, Object的Map
     */
    public static Map<String, Object> parseMap(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(text, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    /**
     * 将JSON字符串转换为指定类型
     */
    public static <T> T fromJson(String text, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(text, typeReference);
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
    
    public static String toPrettyJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Convert object to pretty JSON string failed", e);
            return null;
        }
    }
    
    public static JsonNode parseNode(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        try {
            return getObjectMapper().readTree(text);
        } catch (Exception e) {
            throw new BusinessException("JSON反序列化异常", e);
        }
    }
} 