package com.lawfirm.model.search.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.search.enums.IndexTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索索引实体
 */
@Data
@Entity
@Table(name = "search_index")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SearchIndex extends ModelBaseEntity {

    /**
     * 索引名称
     */
    @Column(name = "index_name", nullable = false)
    private String indexName;

    /**
     * 索引类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "index_type", nullable = false)
    private IndexTypeEnum indexType;

    /**
     * 分片数
     */
    @Column(name = "number_of_shards", nullable = false)
    private Integer numberOfShards = 1;

    /**
     * 副本数
     */
    @Column(name = "number_of_replicas", nullable = false)
    private Integer numberOfReplicas = 1;

    /**
     * 刷新间隔（秒）
     */
    @Column(name = "refresh_interval", nullable = false)
    private Integer refreshInterval = 1;

    /**
     * 字段列表
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id")
    private List<SearchField> fields = new ArrayList<>();

    /**
     * 索引别名
     */
    @Column(name = "alias")
    private String alias;

    /**
     * 索引描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    /**
     * 配置信息（JSON格式）
     */
    @Column(name = "settings", columnDefinition = "text")
    private String settings;

    /**
     * 映射信息（JSON格式）
     */
    @Column(name = "mappings", columnDefinition = "text")
    private String mappings;
} 