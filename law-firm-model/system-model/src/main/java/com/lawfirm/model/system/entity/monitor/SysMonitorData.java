package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统监控数据实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_monitor_data")
public class SysMonitorData extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 监控类型（CPU/Memory/Disk等）
     */
    private String type;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 监控项标题
     */
    private String title;

    /**
     * 监控值
     */
    private Double value;

    /**
     * 单位
     */
    private String unit;

    /**
     * 阈值
     */
    private Double threshold;

    /**
     * 描述
     */
    private String description;

    /**
     * 采集时间
     */
    private Date collectTime;
} 