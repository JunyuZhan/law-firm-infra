package com.lawfirm.model.system.entity.dict;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统字典项实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict_item")
public class SysDictItem extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @TableField("dict_id")
    private Long dictId;

    /**
     * 字典项标签
     */
    @TableField("label")
    private String label;

    /**
     * 字典项值
     */
    @TableField("value")
    private String value;

    /**
     * 字典项描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否默认（0-否，1-是）
     */
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 颜色类型
     */
    @TableField("color_type")
    private String colorType;

    /**
     * CSS样式
     */
    @TableField("css_class")
    private String cssClass;
} 