package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统监控告警实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_monitor_alert")
public class SysMonitorAlert extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 告警ID
     */
    private String alertId;

    /**
     * 告警类型（CPU/Memory/Disk等）
     */
    private String type;

    /**
     * 告警级别（INFO/WARNING/ERROR）
     */
    private String level;

    /**
     * 告警信息
     */
    private String message;

    /**
     * 告警状态（PENDING待处理/HANDLING处理中/CLOSED已关闭）
     */
    private String alertStatus;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 处理结果
     */
    private String handleResult;
} 