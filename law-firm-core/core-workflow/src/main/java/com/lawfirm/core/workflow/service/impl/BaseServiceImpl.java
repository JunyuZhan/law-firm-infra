package com.lawfirm.core.workflow.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lawfirm.common.util.json.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 基础服务实现类
 */
@RequiredArgsConstructor
public abstract class BaseServiceImpl {
    
    protected final JsonUtils jsonUtils;
    
    /**
     * 转换为JSON
     */
    protected String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JsonUtils.toJsonString(obj);
    }
    
    /**
     * 从JSON转换
     */
    protected <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return JsonUtils.fromJson(json, typeReference);
    }
    
    /**
     * 从JSON转换为Map
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> fromJson(String json) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return JsonUtils.fromJson(json, new TypeReference<Map<String, Object>>() {});
    }
} 