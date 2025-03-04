package com.lawfirm.model.ai.service;

import java.util.List;
import java.util.Map;

/**
 * 问答服务接口
 * 提供智能问答、知识库检索、对话式交互等功能
 */
public interface QAService {
    
    /**
     * 获取法律问题的回答
     * 
     * @param question 法律问题
     * @return 回答内容
     */
    String getLegalAnswer(String question);
    
    /**
     * 获取法律问题的回答（带引用）
     * 
     * @param question 法律问题
     * @return 回答内容及引用来源
     */
    Map<String, Object> getLegalAnswerWithReferences(String question);
    
    /**
     * 检索相关案例
     * 
     * @param query 查询内容
     * @param limit 最大返回数量
     * @return 相关案例列表
     */
    List<Map<String, Object>> searchRelatedCases(String query, int limit);
    
    /**
     * 创建对话会话
     * 
     * @param userId 用户ID
     * @return 会话ID
     */
    String createConversation(String userId);
    
    /**
     * 在会话中发送消息
     * 
     * @param conversationId 会话ID
     * @param message 用户消息
     * @return AI回复
     */
    String sendMessage(String conversationId, String message);
    
    /**
     * 获取会话历史
     * 
     * @param conversationId 会话ID
     * @return 会话历史记录
     */
    List<Map<String, String>> getConversationHistory(String conversationId);
    
    /**
     * 结束会话
     * 
     * @param conversationId 会话ID
     * @return 是否成功结束
     */
    boolean endConversation(String conversationId);
} 