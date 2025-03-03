package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统字典实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict")
public class SysDict extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 字典描述
     */
    @TableField("description")
    private String description;
} 