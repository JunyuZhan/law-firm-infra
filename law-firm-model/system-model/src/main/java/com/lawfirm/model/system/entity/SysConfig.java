package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统配置实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfig extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 配置键名
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 配置值
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 配置类型
     */
    @TableField("config_type")
    private String configType;

    /**
     * 配置描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否系统内置（0-否，1-是）
     */
    @TableField("is_system")
    private Integer isSystem;
} 