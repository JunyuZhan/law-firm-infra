package com.lawfirm.common.message.handler.filter;

/**
 * 消息过滤器接口
 * 定义消息过滤的基本行为
 */
public interface MessageFilter {
    
    /**
     * 过滤消息内容
     *
     * @param content 原始内容
     * @return 过滤后的内容
     */
    String filter(String content);
    
    /**
     * 获取过滤器优先级
     * 数字越小优先级越高
     *
     * @return 优先级
     */
    int getOrder();
    
    /**
     * 是否启用该过滤器
     *
     * @return 是否启用
     */
    boolean isEnabled();
    
    /**
     * 获取过滤器类型
     *
     * @return 过滤器类型
     */
    String getType();
} 