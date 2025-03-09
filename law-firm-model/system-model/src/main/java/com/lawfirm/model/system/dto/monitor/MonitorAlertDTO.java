package com.lawfirm.model.system.dto.monitor;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 监控告警DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MonitorAlertDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 告警ID
     */
    private String alertId;

    /**
     * 告警名称
     */
    private String alertName;

    /**
     * 告警级别（INFO/WARNING/ERROR/CRITICAL）
     */
    private String level;

    /**
     * 告警类型
     */
    private String type;

    /**
     * 告警目标
     */
    private String target;

    /**
     * 告警内容
     */
    private String content;

    /**
     * 告警时间
     */
    private LocalDateTime alertTime;

    /**
     * 告警状态（PENDING/ACKNOWLEDGED/RESOLVED/CLOSED）
     */
    private String status;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 告警规则
     */
    private String rule;

    /**
     * 阈值
     */
    private String threshold;

    /**
     * 当前值
     */
    private String currentValue;

    /**
     * 是否自动恢复
     */
    private Boolean autoRecover;

    /**
     * 恢复时间
     */
    private LocalDateTime recoverTime;
} 