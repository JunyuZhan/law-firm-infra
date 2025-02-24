package com.lawfirm.model.knowledge.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 标签实体
 */
@Data
@Entity
@Table(name = "knowledge_tag")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tag extends ModelBaseEntity {

    /**
     * 标签名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 标签编码（用于URL）
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 标签描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 使用次数
     */
    @Column(name = "use_count")
    private Long useCount = 0L;

    /**
     * 排序权重
     */
    @Column(name = "weight")
    private Integer weight = 0;

    /**
     * 是否推荐
     */
    @Column(name = "recommended")
    private Boolean recommended = false;
} 