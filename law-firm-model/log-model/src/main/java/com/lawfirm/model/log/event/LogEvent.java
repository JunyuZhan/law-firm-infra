package com.lawfirm.model.log.event;

import com.lawfirm.model.log.entity.base.BaseLog;
import lombok.Getter;

/**
 * 日志事件
 * 用于日志异步处理
 */
@Getter
public class LogEvent {

    /**
     * 事件ID
     */
    private final String eventId;

    /**
     * 日志实体
     */
    private final BaseLog log;

    /**
     * 事件时间戳
     */
    private final long timestamp;

    /**
     * 事件来源
     */
    private final String source;

    public LogEvent(String eventId, BaseLog log, String source) {
        this.eventId = eventId;
        this.log = log;
        this.source = source;
        this.timestamp = System.currentTimeMillis();
    }
} 