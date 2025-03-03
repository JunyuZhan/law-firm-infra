package com.lawfirm.model.search.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.search.enums.IndexTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IndexVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引类型
     */
    private IndexTypeEnum indexType;

    /**
     * 分片数
     */
    private Integer numberOfShards;

    /**
     * 副本数
     */
    private Integer numberOfReplicas;

    /**
     * 刷新间隔（秒）
     */
    private Integer refreshInterval;

    /**
     * 字段列表
     */
    private transient List<FieldVO> fields = new ArrayList<>();

    /**
     * 索引别名
     */
    private String alias;

    /**
     * 索引描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 配置信息
     */
    private String settings;

    /**
     * 映射信息
     */
    private String mappings;

    /**
     * 文档数量
     */
    private Long docCount;

    /**
     * 存储大小（字节）
     */
    private Long storeSize;

    /**
     * 主分片大小（字节）
     */
    private Long primaryStoreSize;
} 