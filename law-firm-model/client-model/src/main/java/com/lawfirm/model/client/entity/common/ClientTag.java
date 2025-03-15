package com.lawfirm.model.client.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户标签实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("client_tag")
public class ClientTag extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField("tag_name")
    private String tagName;

    /**
     * 标签颜色
     */
    @TableField("color")
    private String color;

    /**
     * 标签类型
     */
    @TableField("tag_type")
    private String tagType;
    
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 状态（0-禁用，1-启用）
     */
    @TableField("status")
    private Integer status;
} 