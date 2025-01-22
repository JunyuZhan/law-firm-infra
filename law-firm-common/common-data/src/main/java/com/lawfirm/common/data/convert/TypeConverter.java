package com.lawfirm.common.data.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通用类型转换器
 */
@Component
public class TypeConverter {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 对象转换
     */
    public <T> T convert(Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        return objectMapper.convertValue(source, targetType);
    }

    /**
     * 对象转换（支持泛型）
     */
    public <T> T convert(Object source, TypeReference<T> typeReference) {
        if (source == null) {
            return null;
        }
        return objectMapper.convertValue(source, typeReference);
    }

    /**
     * 列表转换
     */
    public <S, T> List<T> convertList(List<S> sourceList, Class<T> targetType) {
        if (sourceList == null) {
            return null;
        }
        return objectMapper.convertValue(sourceList, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, targetType));
    }

    /**
     * 列表转换（支持泛型）
     */
    public <T> List<T> convertList(List<?> sourceList, TypeReference<List<T>> typeReference) {
        if (sourceList == null) {
            return null;
        }
        return objectMapper.convertValue(sourceList, typeReference);
    }
} 