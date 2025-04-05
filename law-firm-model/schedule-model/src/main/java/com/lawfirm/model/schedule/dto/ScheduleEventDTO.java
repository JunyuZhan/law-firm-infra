package com.lawfirm.model.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 日程事件数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 事件ID
     */
    private String eventId;
    
    /**
     * 事件类型
     */
    private String eventType;
    
    /**
     * 事件主题
     */
    private String subject;
    
    /**
     * 事件内容
     */
    private String content;
    
    /**
     * 事件发生时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 发送者ID
     */
    private Long senderId;
    
    /**
     * 发送者名称
     */
    private String senderName;
    
    /**
     * 目标对象ID
     */
    private Long targetId;
    
    /**
     * 目标对象类型
     */
    private String targetType;
    
    /**
     * 优先级
     */
    private Integer priority;
    
    /**
     * 是否需要确认回执
     */
    private Boolean needAcknowledge;
    
    /**
     * 事件状态
     */
    private Integer status;
    
    /**
     * 事件业务数据
     */
    private transient Map<String, Object> data;
    
    /**
     * 额外参数
     */
    private transient Map<String, Object> extraParams;
} 