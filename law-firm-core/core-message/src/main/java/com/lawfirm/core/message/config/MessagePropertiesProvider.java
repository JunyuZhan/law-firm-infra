package com.lawfirm.core.message.config;

/**
 * 消息配置提供者接口
 * 业务层实现此接口以提供消息服务配置
 */
public interface MessagePropertiesProvider {
    
    /**
     * 获取消息服务配置
     * 
     * @return 消息配置属性
     */
    MessageProperties getMessageProperties();
} 