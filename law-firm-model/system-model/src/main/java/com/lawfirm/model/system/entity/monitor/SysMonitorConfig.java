package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统监控配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_monitor_config")
public class SysMonitorConfig extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 监控类型（CPU/Memory/Disk等）
     */
    private String type;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 阈值
     */
    private Double threshold;

    /**
     * 采集间隔（秒）
     */
    private Integer intervalSeconds;

    /**
     * 是否启用（0否/1是）
     */
    private Boolean enabled;

    /**
     * 描述
     */
    private String description;
} 