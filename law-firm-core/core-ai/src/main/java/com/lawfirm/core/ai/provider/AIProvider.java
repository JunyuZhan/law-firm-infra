package com.lawfirm.core.ai.provider;

import java.util.Map;

/**
 * AI服务提供者接口
 * 定义不同AI服务提供者的统一接口
 */
public interface AIProvider {
    
    /**
     * 获取提供者名称
     * 
     * @return 提供者名称
     */
    String getName();
    
    /**
     * 初始化提供者
     * 
     * @param config 配置信息
     * @return 是否初始化成功
     */
    boolean initialize(Map<String, Object> config);
    
    /**
     * 发送文本请求并获取回复
     * 
     * @param prompt 提示文本
     * @param options 选项参数
     * @return AI回复
     */
    String sendTextRequest(String prompt, Map<String, Object> options);
    
    /**
     * 发送聊天请求并获取回复
     * 
     * @param messages 消息列表
     * @param options 选项参数
     * @return AI回复
     */
    String sendChatRequest(Map<String, Object>[] messages, Map<String, Object> options);
    
    /**
     * 创建嵌入向量
     * 
     * @param text 待处理文本
     * @return 嵌入向量结果
     */
    float[] createEmbedding(String text);
    
    /**
     * 获取提供者模型列表
     * 
     * @return 可用模型列表
     */
    String[] getAvailableModels();
    
    /**
     * 检查提供者状态
     * 
     * @return 状态信息
     */
    Map<String, Object> checkStatus();
    
    /**
     * 关闭提供者
     */
    void shutdown();
} 