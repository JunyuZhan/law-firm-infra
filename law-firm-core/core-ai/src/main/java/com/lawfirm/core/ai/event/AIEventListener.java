package com.lawfirm.core.ai.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
        // 1. 更新操作统计
        if (aiEventStatService != null) {
            aiEventStatService.updateStatus(event.getOperationId(), "SUCCESS");
        }
        // 2. 发送成功通知
        if (alertService != null) {
            alertService.sendAlert("AI操作成功", "操作已成功完成", event.getOperationId());
        }
        // 3. 触发后续流程（如有需要）
        // 例如：调用后续业务服务、发布事件等
        // if (nextProcessService != null) {
        //     nextProcessService.handleAfterSuccess(event);
        // }
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
        // 1. 记录错误日志（已实现）
        // 2. 发送失败告警（可对接消息/告警服务）
        if (alertService != null) {
            alertService.sendAlert("AI操作失败", event.getErrorMessage(), event.getOperationId());
        }
        // 3. 触发重试机制（可选，预留重试服务接口）
        if (retryService != null) {
            retryService.retry(event);
        }
        // 4. 更新失败统计（如写入数据库/缓存）
        if (aiEventStatService != null) {
            aiEventStatService.recordFailure(String.valueOf(event.getOperationType()), event.getOperationId());
        }
    }
    
    /**
     * 处理处理中事件
     */
    private void handleProcessingEvent(AIEvent event) {
        logger.info("处理进行中事件: operationId={}, type={}",
                event.getOperationId(),
                event.getOperationType());
        // 1. 更新操作状态（如写入数据库/缓存）
        if (aiEventStatService != null) {
            aiEventStatService.updateStatus(event.getOperationId(), "PROCESSING");
        }
        // 2. 记录处理进度
        logger.info("AI操作处理中: operationId={}, type={}", event.getOperationId(), event.getOperationType());
        // 3. 设置超时监控（可预留钩子）
        if (timeoutMonitorService != null) {
            timeoutMonitorService.monitor(event.getOperationId(), String.valueOf(event.getOperationType()));
        }
    }

    // ========== 依赖服务接口预留 ==========
    @Autowired(required = false)
    @Qualifier("aiAlertService")
    private AlertService alertService;
    @Autowired(required = false)
    @Qualifier("aiRetryService")
    private RetryService retryService;
    @Autowired(required = false)
    @Qualifier("aiEventStatService")
    private AIEventStatService aiEventStatService;
    @Autowired(required = false)
    @Qualifier("aiTimeoutMonitorService")
    private TimeoutMonitorService timeoutMonitorService;

    public interface AlertService {
        void sendAlert(String title, String content, String operationId);
    }
    public interface RetryService {
        void retry(AIEvent event);
    }
    public interface AIEventStatService {
        void recordFailure(String operationType, String operationId);
        void updateStatus(String operationId, String status);
    }
    public interface TimeoutMonitorService {
        void monitor(String operationId, String operationType);
    }
} 