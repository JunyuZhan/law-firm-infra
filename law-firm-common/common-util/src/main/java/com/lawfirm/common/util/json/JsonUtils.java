package com.lawfirm.common.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lawfirm.common.util.BaseUtils;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * JSON工具类
 */
@Slf4j
public class JsonUtils extends BaseUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 对象转JSON字符串
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON失败: {}", object, e);
            return null;
        }
    }
    
    /**
     * JSON字符串转对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JSON转对象失败: {}", json, e);
            return null;
        }
    }
    
    /**
     * JSON字符串转List
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("JSON转List失败: {}", json, e);
            return null;
        }
    }
    
    /**
     * JSON字符串转Map
     */
    public static Map<String, Object> parseMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("JSON转Map失败: {}", json, e);
            return null;
        }
    }
    
    public static String toPrettyJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Convert object to pretty JSON string failed", e);
            return null;
        }
    }
    
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("JSON反序列化失败", e);
            return null;
        }
    }
    
    public static JsonNode parseNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("JSON解析失败", e);
            return null;
        }
    }
} 