package com.lawfirm.archive.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 档案分类实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("archive_category")
public class ArchiveCategory extends BaseEntity {
    
    /**
     * 分类编码
     */
    private String categoryCode;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 分类层级
     */
    private Integer level;
    
    /**
     * 排序号
     */
    private Integer sortOrder;
    
    /**
     * 分类描述
     */
    private String description;
} 