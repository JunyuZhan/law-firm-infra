package com.lawfirm.core.ai.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * AI事件监听器
 * 用于异步处理AI操作事件
 */
@Component
public class AIEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(AIEventListener.class);
    
    /**
     * 处理AI事件
     * 使用@Async注解实现异步处理
     * 
     * @param event AI事件
     */
    @Async
    @EventListener
    public void handleAIEvent(AIEvent event) {
        logger.info("接收到AI事件: {}", event);
        
        try {
            // 根据事件状态进行不同处理
            if (event.isSuccess()) {
                handleSuccessEvent(event);
            } else if (event.isFailed()) {
                handleFailedEvent(event);
            } else if (event.isProcessing()) {
                handleProcessingEvent(event);
            }
            
            // 记录事件处理完成
            logger.info("AI事件处理完成: operationId={}, type={}, status={}",
                    event.getOperationId(),
                    event.getOperationType(),
                    event.getStatus());
                    
        } catch (Exception e) {
            logger.error("处理AI事件失败: " + event, e);
        }
    }
    
    /**
     * 处理成功事件
     */
    private void handleSuccessEvent(AIEvent event) {
        logger.info("处理成功事件: operationId={}, type={}, duration={}ms",
                event.getOperationId(),
                event.getOperationType(),
                event.getDuration());
                
        // TODO: 可以添加更多成功事件的处理逻辑
        // 例如：
        // 1. 更新操作统计
        // 2. 发送成功通知
        // 3. 触发后续流程
    }
    
    /**
     * 处理失败事件
     */
    private void handleFailedEvent(AIEvent event) {
        logger.error("处理失败事件: operationId={}, type={}, error={}",
                event.getOperationId(),
                event.getOperationType(),
                event.getErrorMessage());
                
        // TODO: 可以添加更多失败事件的处理逻辑
        // 例如：
        // 1. 记录错误日志
        // 2. 发送失败告警
        // 3. 触发重试机制
        // 4. 更新失败统计
    }
    
    /**
     * 处理处理中事件
     */
    private void handleProcessingEvent(AIEvent event) {
        logger.info("处理进行中事件: operationId={}, type={}",
                event.getOperationId(),
                event.getOperationType());
                
        // TODO: 可以添加更多处理中事件的处理逻辑
        // 例如：
        // 1. 更新操作状态
        // 2. 记录处理进度
        // 3. 设置超时监控
    }
} 