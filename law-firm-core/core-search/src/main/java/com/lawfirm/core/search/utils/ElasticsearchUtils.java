package com.lawfirm.core.search.utils;

import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import com.lawfirm.common.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.lawfirm.model.search.entity.SearchField;
import com.lawfirm.model.search.entity.SearchIndex;
import com.lawfirm.model.search.enums.FieldTypeEnum;
import lombok.experimental.UtilityClass;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ES工具类
 */
@UtilityClass
public class ElasticsearchUtils {

    /**
     * 生成索引映射
     */
    public TypeMapping generateMapping(Class<?> clazz) {
        Assert.notNull(clazz, "类型不能为空");
        
        // 获取字段信息
        List<SearchField> fields = getFields(clazz);
        
        // 构建映射
        Map<String, Object> mapping = buildMapping(fields);
        
        // 转换为TypeMapping
        return TypeMapping.of(builder -> 
            builder.withJson(new StringReader(JsonUtils.toJsonString(mapping)))
        );
    }

    /**
     * 获取字段信息
     */
    private List<SearchField> getFields(Class<?> clazz) {
        List<SearchField> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            com.lawfirm.model.search.annotation.SearchField annotation = 
                field.getAnnotation(com.lawfirm.model.search.annotation.SearchField.class);
            if (annotation != null) {
                SearchField searchField = new SearchField();
                searchField.setFieldName(field.getName());
                searchField.setFieldType(FieldTypeEnum.valueOf(annotation.type().toUpperCase()));
                searchField.setAnalyzer(annotation.analyzer());
                searchField.setBoost(annotation.boost());
                fields.add(searchField);
            }
        }
        return fields;
    }

    /**
     * 构建映射
     */
    private Map<String, Object> buildMapping(List<SearchField> fields) {
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        
        for (SearchField field : fields) {
            Map<String, Object> fieldMapping = new HashMap<>();
            fieldMapping.put("type", field.getFieldType().getCode().toLowerCase());
            if (StringUtils.isNotBlank(field.getAnalyzer())) {
                fieldMapping.put("analyzer", field.getAnalyzer());
            }
            if (field.getBoost() != 1.0f) {
                fieldMapping.put("boost", field.getBoost());
            }
            properties.put(field.getFieldName(), fieldMapping);
        }
        
        mapping.put("properties", properties);
        return mapping;
    }

    /**
     * 生成索引名称
     */
    public String generateIndexName(SearchIndex index) {
        Assert.notNull(index, "索引信息不能为空");
        Assert.hasText(index.getIndexName(), "索引名称不能为空");
        
        String name = index.getIndexName().toLowerCase();
        if (StringUtils.isNotBlank(index.getAlias())) {
            name = String.format("%s_%s", name, index.getAlias().toLowerCase());
        }
        
        return name;
    }
} 