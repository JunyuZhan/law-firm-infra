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
     * 日程ID
     */
    private Long scheduleId;
    
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
     * 事件地点
     */
    private String location;
    
    /**
     * 事件描述
     */
    private String description;
    
    /**
     * 事件发生时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
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
    
    /**
     * 兼容getTitle方法，与旧代码兼容
     * @return 事件主题
     */
    public String getTitle() {
        return this.subject;
    }
    
    /**
     * 兼容setTitle方法，与旧代码兼容
     * @param title 事件主题
     */
    public void setTitle(String title) {
        this.subject = title;
    }
    
    /**
     * 兼容getId方法，与旧代码兼容
     * @return 事件ID
     */
    public String getId() {
        return this.eventId;
    }
    
    /**
     * 兼容setId方法，与旧代码兼容
     * @param id 事件ID
     */
    public void setId(String id) {
        this.eventId = id;
    }
    
    /**
     * 兼容getType方法，与旧代码兼容
     * @return 事件类型
     */
    public Integer getType() {
        if (this.eventType == null) {
            return null;
        }
        try {
            return Integer.parseInt(this.eventType);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 兼容setType方法，与旧代码兼容
     * @param type 事件类型
     */
    public void setType(Integer type) {
        if (type != null) {
            this.eventType = String.valueOf(type);
        }
    }
    
    /**
     * 兼容getLocationDescription方法，与旧代码兼容
     * @return 位置描述
     */
    public String getLocationDescription() {
        return this.location;
    }
}