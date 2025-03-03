package com.lawfirm.model.search.dto.index;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.search.enums.FieldTypeEnum;
import com.lawfirm.model.search.enums.IndexTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IndexCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 索引名称
     */
    @NotBlank(message = "索引名称不能为空")
    private String indexName;

    /**
     * 索引类型
     */
    @NotNull(message = "索引类型不能为空")
    private IndexTypeEnum indexType;

    /**
     * 分片数
     */
    private Integer numberOfShards = 1;

    /**
     * 副本数
     */
    private Integer numberOfReplicas = 1;

    /**
     * 刷新间隔（秒）
     */
    private Integer refreshInterval = 1;

    /**
     * 字段列表
     */
    private transient List<FieldDTO> fields = new ArrayList<>();

    /**
     * 索引别名
     */
    private String alias;

    /**
     * 索引描述
     */
    private String description;

    /**
     * 配置信息（JSON格式）
     */
    private String settings;

    /**
     * 映射信息（JSON格式）
     */
    private String mappings;

    /**
     * 字段DTO
     */
    @Data
    @Accessors(chain = true)
    public static class FieldDTO {
        /**
         * 字段名称
         */
        @NotBlank(message = "字段名称不能为空")
        private String fieldName;

        /**
         * 字段类型
         */
        @NotNull(message = "字段类型不能为空")
        private FieldTypeEnum fieldType;

        /**
         * 是否分词
         */
        private Boolean analyzed = false;

        /**
         * 是否存储
         */
        private Boolean stored = true;

        /**
         * 是否索引
         */
        private Boolean indexed = true;

        /**
         * 分词器
         */
        private String analyzer;

        /**
         * 搜索分词器
         */
        private String searchAnalyzer;

        /**
         * 权重
         */
        private Float boost = 1.0f;

        /**
         * 复制到字段
         */
        private String copyTo;

        /**
         * 忽略大小写
         */
        private Boolean ignoreCase = false;

        /**
         * 是否必填
         */
        private Boolean required = false;

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 字段描述
         */
        private String description;
    }
} 