package com.lawfirm.core.ai.service.impl;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.config.AIConfig;
import com.lawfirm.core.ai.exception.AIException;
import com.lawfirm.model.ai.service.QAService;
import com.lawfirm.model.ai.dto.QuestionDTO;
import com.lawfirm.model.ai.entity.Conversation;
import com.lawfirm.model.ai.entity.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 问答服务实现类
 */
@Slf4j
@Component("aiQAServiceImpl")
@RequiredArgsConstructor
public class QAServiceImpl implements QAService {
    
    private static final Logger logger = LoggerFactory.getLogger(QAServiceImpl.class);
    
    @Autowired
    private AIProviderFactory providerFactory;
    
    @Autowired
    private AIConfig aiConfig;
    
    // 简单的内存存储对话会话，实际项目中应该使用数据库
    private final Map<String, List<Map<String, String>>> conversations = new ConcurrentHashMap<>();
    
    @Override
    public String getLegalAnswer(String question) {
        logger.info("获取法律问题答案: {}", question);
        
        try {
            // 获取默认AI提供者
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建请求选项
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.5); // 降低随机性，提高精确度
            
            // 构建提示词，引导AI以法律专业角度回答
            String prompt = "作为一名法律专业人士，请回答以下法律问题：\n" + question;
            
            // 发送请求并获取回答
            String answer = provider.sendTextRequest(prompt, options);
            
            return answer;
        } catch (Exception e) {
            logger.error("获取法律问题答案失败", e);
            throw new AIException("获取法律问题答案失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> getLegalAnswerWithReferences(String question) {
        logger.info("获取法律问题答案（带引用）: {}", question);
        
        try {
            // 获取默认AI提供者
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建请求选项
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3); // 更低的随机性，提高精确度
            
            // 构建提示词，要求AI给出引用法条
            String prompt = "作为一名法律专业人士，请回答以下法律问题，并提供相关法律条文或案例引用：\n" + question;
            
            // 发送请求并获取回答
            String fullAnswer = provider.sendTextRequest(prompt, options);
            
            // 解析回答和引用（实际项目中需要更复杂的解析逻辑）
            Map<String, Object> result = new HashMap<>();
            result.put("answer", fullAnswer);
            result.put("references", extractReferences(fullAnswer));
            
            return result;
        } catch (Exception e) {
            logger.error("获取法律问题答案（带引用）失败", e);
            throw new AIException("获取法律问题答案（带引用）失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Map<String, Object>> searchRelatedCases(String query, int limit) {
        logger.info("检索相关案例: {}, 限制数量: {}", query, limit);
        
        try {
            // 这里应该调用案例检索服务
            // 此处简单模拟返回结果
            List<Map<String, Object>> cases = new ArrayList<>();
            
            // 模拟几个案例
            for (int i = 1; i <= Math.min(limit, 5); i++) {
                Map<String, Object> caseInfo = new HashMap<>();
                caseInfo.put("id", "CASE-" + (10000 + i));
                caseInfo.put("title", "相关案例 #" + i + " - " + query);
                caseInfo.put("court", "某法院");
                caseInfo.put("date", "2023-0" + i + "-01");
                caseInfo.put("summary", "这是一个与\"" + query + "\"相关的案例摘要...");
                caseInfo.put("relevance", 0.9 - (i * 0.1));
                
                cases.add(caseInfo);
            }
            
            return cases;
        } catch (Exception e) {
            logger.error("检索相关案例失败", e);
            throw new AIException("检索相关案例失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String createConversation(String userId) {
        logger.info("创建对话会话, 用户ID: {}", userId);
        
        try {
            // 生成会话ID
            String conversationId = UUID.randomUUID().toString();
            
            // 初始化会话历史
            List<Map<String, String>> history = new ArrayList<>();
            conversations.put(conversationId, history);
            
            // 添加系统消息
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "我是律所AI助手，可以回答您的法律问题。");
            systemMessage.put("timestamp", String.valueOf(System.currentTimeMillis()));
            history.add(systemMessage);
            
            logger.info("已创建对话会话: {}", conversationId);
            return conversationId;
        } catch (Exception e) {
            logger.error("创建对话会话失败", e);
            throw new AIException("创建对话会话失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String sendMessage(String conversationId, String message) {
        logger.info("发送消息到会话: {}", conversationId);
        
        try {
            // 获取会话历史
            List<Map<String, String>> history = conversations.get(conversationId);
            if (history == null) {
                throw new AIException("会话不存在: " + conversationId);
            }
            
            // 添加用户消息
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", message);
            userMessage.put("timestamp", String.valueOf(System.currentTimeMillis()));
            history.add(userMessage);
            
            // 获取默认AI提供者
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 将历史转换为AI提供者需要的格式
            Map<String, Object>[] messages = convertHistoryToMessages(history);
            
            // 发送聊天请求
            String response = provider.sendChatRequest(messages, null);
            
            // 添加AI回复
            Map<String, String> aiMessage = new HashMap<>();
            aiMessage.put("role", "assistant");
            aiMessage.put("content", response);
            aiMessage.put("timestamp", String.valueOf(System.currentTimeMillis()));
            history.add(aiMessage);
            
            return response;
        } catch (Exception e) {
            logger.error("发送消息失败", e);
            throw new AIException("发送消息失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Map<String, String>> getConversationHistory(String conversationId) {
        logger.info("获取会话历史: {}", conversationId);
        
        List<Map<String, String>> history = conversations.get(conversationId);
        if (history == null) {
            throw new AIException("会话不存在: " + conversationId);
        }
        
        return new ArrayList<>(history);
    }
    
    @Override
    public boolean endConversation(String conversationId) {
        logger.info("结束会话: {}", conversationId);
        
        if (!conversations.containsKey(conversationId)) {
            logger.warn("尝试结束不存在的会话: {}", conversationId);
            return false;
        }
        
        conversations.remove(conversationId);
        logger.info("已结束会话: {}", conversationId);
        return true;
    }
    
    /**
     * 从回答中提取引用
     * 
     * @param answer 完整回答
     * @return 引用列表
     */
    private List<String> extractReferences(String answer) {
        List<String> references = new ArrayList<>();
        
        // 简单实现，查找"《"和"》"之间的内容作为引用
        // 实际项目中可能需要更复杂的规则或者NLP模型
        int startIndex = 0;
        while (true) {
            int start = answer.indexOf("《", startIndex);
            if (start == -1) break;
            
            int end = answer.indexOf("》", start);
            if (end == -1) break;
            
            String reference = answer.substring(start, end + 1);
            references.add(reference);
            
            startIndex = end + 1;
        }
        
        return references;
    }
    
    /**
     * 将会话历史转换为AI提供者需要的格式
     * 
     * @param history 会话历史
     * @return 转换后的消息数组
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object>[] convertHistoryToMessages(List<Map<String, String>> history) {
        return history.stream()
                .map(msg -> {
                    Map<String, Object> message = new HashMap<>();
                    message.put("role", msg.get("role"));
                    message.put("content", msg.get("content"));
                    return message;
                })
                .toArray(Map[]::new);
    }
} 