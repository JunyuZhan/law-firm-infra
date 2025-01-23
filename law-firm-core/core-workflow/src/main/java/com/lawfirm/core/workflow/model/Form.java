package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 表单模型
 */
@Data
@Accessors(chain = true)
@Document(collection = "forms")
public class Form {
    
    /**
     * 表单ID
     */
    @Id
    private String id;
    
    /**
     * 表单Key
     */
    private String key;
    
    /**
     * 表单名称
     */
    private String name;
    
    /**
     * 表单描述
     */
    private String description;
    
    /**
     * 表单分类
     */
    private String category;
    
    /**
     * 表单版本
     */
    private int version;
    
    /**
     * 表单内容
     */
    private String content;
    
    /**
     * 表单配置
     */
    private Map<String, Object> config;
    
    /**
     * 表单字段列表
     */
    private List<FormField> fields;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 表单字段
     */
    @Data
    @Accessors(chain = true)
    public static class FormField {
        
        /**
         * 字段ID
         */
        private String id;
        
        /**
         * 字段名称
         */
        private String name;
        
        /**
         * 字段类型
         */
        private String type;
        
        /**
         * 字段标签
         */
        private String label;
        
        /**
         * 默认值
         */
        private String defaultValue;
        
        /**
         * 是否必填
         */
        private boolean required;
        
        /**
         * 是否只读
         */
        private boolean readonly;
        
        /**
         * 字段验证规则
         */
        private String validation;
        
        /**
         * 字段选项(用于下拉框、单选框等)
         */
        private List<FormFieldOption> options;
    }
    
    /**
     * 表单字段选项
     */
    @Data
    @Accessors(chain = true)
    public static class FormFieldOption {
        
        /**
         * 选项值
         */
        private String value;
        
        /**
         * 选项标签
         */
        private String label;
    }
} 