package com.lawfirm.common.message.handler.route;

import com.lawfirm.common.message.support.channel.MessageChannel;

/**
 * 消息路由接口
 * 定义消息路由的基本行为
 */
public interface MessageRouter {
    
    /**
     * 根据消息类型选择合适的通道
     *
     * @param messageType 消息类型
     * @return 消息通道
     */
    MessageChannel route(String messageType);
    
    /**
     * 注册消息通道
     *
     * @param channel 消息通道
     */
    void registerChannel(MessageChannel channel);
    
    /**
     * 移除消息通道
     *
     * @param channelType 通道类型
     */
    void removeChannel(String channelType);
    
    /**
     * 获取所有可用的通道
     *
     * @return 可用的通道列表
     */
    MessageChannel[] getAvailableChannels();
} 