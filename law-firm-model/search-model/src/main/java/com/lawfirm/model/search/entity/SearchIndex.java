package com.lawfirm.model.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.search.enums.IndexTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索索引实体
 */
@Data
@TableName("search_index")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchIndex extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 索引名称
     */
    @TableField("index_name")
    private String indexName;

    /**
     * 索引类型
     */
    @TableField("index_type")
    private IndexTypeEnum indexType;

    /**
     * 分片数
     */
    @TableField("number_of_shards")
    private Integer numberOfShards = 1;

    /**
     * 副本数
     */
    @TableField("number_of_replicas")
    private Integer numberOfReplicas = 1;

    /**
     * 刷新间隔
     */
    @TableField("refresh_interval")
    private Integer refreshInterval = 1;

    /**
     * 索引字段
     */
    @TableField(exist = false)
    private transient List<SearchField> fields = new ArrayList<>();

    /**
     * 索引别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 索引描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否启用
     */
    @TableField("enabled")
    private Boolean enabled = true;

    /**
     * 索引设置（JSON格式）
     */
    @TableField("settings")
    private String settings;

    /**
     * 索引映射（JSON格式）
     */
    @TableField("mappings")
    private String mappings;
} 