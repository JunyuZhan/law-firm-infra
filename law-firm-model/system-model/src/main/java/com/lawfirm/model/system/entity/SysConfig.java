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
    @TableField(exist = false)
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
    @TableField(exist = false)
    private String configType;

    /**
     * 配置描述
     */
    @TableField("remark")
    private String description;

    /**
     * 是否系统内置（0-否，1-是）
     * 注：此字段当前数据库中不存在，待数据库升级后启用
     */
    @TableField(exist = false)
    private Integer isSystem;
    
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    
    /**
     * 版本号
     * 注：此字段当前数据库中不存在，待数据库升级后启用
     */
    @TableField(exist = false)
    private Integer version;
    
    /**
     * 状态（0-禁用，1-启用）
     * 注：此字段当前数据库中不存在，待数据库升级后启用
     */
    @TableField(exist = false)
    private Integer status;
    
    /**
     * 排序号
     * 注：此字段当前数据库中不存在，待数据库升级后启用
     */
    @TableField(exist = false)
    private Integer sort;
}