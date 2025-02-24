package com.lawfirm.model.log.event;

/**
 * 日志事件监听器接口
 */
public interface LogEventListener {

    /**
     * 处理日志事件
     *
     * @param event 日志事件
     */
    void onLogEvent(LogEvent event);

    /**
     * 是否支持该事件
     *
     * @param event 日志事件
     * @return 是否支持
     */
    boolean supports(LogEvent event);
} 