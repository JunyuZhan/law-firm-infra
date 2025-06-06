package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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
     * 告警业务ID
     */
    private String alertId;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 告警类型
     */
    private String type;

    /**
     * 告警级别
     */
    private String level;

    /**
     * 告警消息
     */
    private String message;

    /**
     * 告警级别(1-警告,2-严重,3-紧急)
     */
    private Integer alertLevel;

    /**
     * 告警标题
     */
    private String alertTitle;

    /**
     * 告警内容
     */
    private String alertContent;

    /**
     * 告警时间
     */
    private LocalDateTime alertTime;

    /**
     * 处理状态
     */
    private String alertStatus;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理人姓名
     */
    private String handlerName;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理时间（兼容Date类型）
     */
    private Date handleTimeDate;

    /**
     * 处理结果
     */
    private String handleResult;

    // 便捷方法：设置处理时间（Date类型）
    public void setHandleTime(Date handleTime) {
        this.handleTimeDate = handleTime;
        if (handleTime != null) {
            this.handleTime = new java.sql.Timestamp(handleTime.getTime()).toLocalDateTime();
        }
    }

    // 便捷方法：获取处理时间（Date类型）
    public Date getHandleTimeAsDate() {
        if (this.handleTime != null) {
            return java.sql.Timestamp.valueOf(this.handleTime);
        }
        return this.handleTimeDate;
    }
} 