package com.lawfirm.common.message.support.channel;

import com.lawfirm.common.message.support.sender.MessageSendResult;

/**
 * 消息通道接口
 * 定义消息发送通道的基本行为
 */
public interface MessageChannel {
    
    /**
     * 获取通道类型
     *
     * @return 通道类型
     */
    String getType();
    
    /**
     * 检查通道是否可用
     *
     * @return 是否可用
     */
    boolean isAvailable();
    
    /**
     * 发送消息
     *
     * @param content 消息内容
     * @param target 目标地址（如：邮箱、手机号等）
     * @return 发送结果
     */
    MessageSendResult send(String content, String target);
    
    /**
     * 批量发送消息
     *
     * @param content 消息内容
     * @param targets 目标地址列表
     * @return 发送结果
     */
    MessageSendResult batchSend(String content, String... targets);
} 