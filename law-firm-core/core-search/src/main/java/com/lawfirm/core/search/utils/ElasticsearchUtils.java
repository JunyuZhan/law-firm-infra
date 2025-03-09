package com.lawfirm.core.search.utils;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ES工具类
 */
public class ElasticsearchUtils {
    
    /**
     * 生成映射
     */
    public static TypeMapping generateMapping(Class<?> clazz) {
        Map<String, Property> properties = new HashMap<>();
        
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                continue; // 跳过ID字段，ES会自动处理
            }
            
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            
            Property property = buildProperty(fieldType);
            if (property != null) {
                properties.put(fieldName, property);
            }
        }
        
        return TypeMapping.of(t -> t.properties(properties));
    }
    
    private static Property buildProperty(Class<?> fieldType) {
        if (String.class.equals(fieldType)) {
            return Property.of(p -> p.text(t -> t
                .analyzer("ik_max_word")
                .searchAnalyzer("ik_smart")
            ));
        } else if (Long.class.equals(fieldType) || long.class.equals(fieldType)) {
            return Property.of(p -> p.long_(l -> l));
        } else if (Integer.class.equals(fieldType) || int.class.equals(fieldType)) {
            return Property.of(p -> p.integer(i -> i));
        } else if (Double.class.equals(fieldType) || double.class.equals(fieldType)) {
            return Property.of(p -> p.double_(d -> d));
        } else if (Float.class.equals(fieldType) || float.class.equals(fieldType)) {
            return Property.of(p -> p.float_(f -> f));
        } else if (Boolean.class.equals(fieldType) || boolean.class.equals(fieldType)) {
            return Property.of(p -> p.boolean_(b -> b));
        } else if (LocalDateTime.class.equals(fieldType)) {
            return Property.of(p -> p.date(d -> d.format("yyyy-MM-dd'T'HH:mm:ss")));
        } else if (LocalDate.class.equals(fieldType)) {
            return Property.of(p -> p.date(d -> d.format("yyyy-MM-dd")));
        }
        
        return null;
    }
} 