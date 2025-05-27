package com.lawfirm.core.ai.event;

import com.lawfirm.model.ai.enums.AIOperationTypeEnum;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * AI事件基类
 * 用于处理AI操作的事件通知
 */
public class AIEvent extends ApplicationEvent {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型
     */
    private final AIOperationTypeEnum operationType;
    
    /**
     * 操作ID
     */
    private final String operationId;
    
    /**
     * 操作时间
     */
    private final LocalDateTime operationTime;
    
    /**
     * 操作状态
     * success: 成功
     * failed: 失败
     * processing: 处理中
     */
    private final String status;
    
    /**
     * 操作结果
     * 标记为transient因为Map中的Object可能不可序列化
     */
    private final transient Map<String, Object> result;
    
    /**
     * 错误信息（如果有）
     */
    private final String errorMessage;
    
    /**
     * 操作耗时（毫秒）
     */
    private final long duration;
    
    /**
     * 操作参数
     * 标记为transient因为Map中的Object可能不可序列化
     */
    private final transient Map<String, Object> parameters;
    
    /**
     * 用户ID
     */
    private final String userId;
    
    /**
     * 模型ID
     */
    private final String modelId;
    
    /**
     * 构造函数
     * 
     * @param source 事件源
     * @param operationType 操作类型
     * @param operationId 操作ID
     * @param status 操作状态
     * @param result 操作结果
     * @param errorMessage 错误信息
     * @param duration 操作耗时
     * @param parameters 操作参数
     * @param userId 用户ID
     * @param modelId 模型ID
     */
    public AIEvent(Object source, 
                  AIOperationTypeEnum operationType,
                  String operationId,
                  String status,
                  Map<String, Object> result,
                  String errorMessage,
                  long duration,
                  Map<String, Object> parameters,
                  String userId,
                  String modelId) {
        super(source);
        this.operationType = operationType;
        this.operationId = operationId;
        this.operationTime = LocalDateTime.now();
        this.status = status;
        this.result = result != null ? new HashMap<>(result) : null;
        this.errorMessage = errorMessage;
        this.duration = duration;
        this.parameters = parameters != null ? new HashMap<>(parameters) : null;
        this.userId = userId;
        this.modelId = modelId;
    }
    
    /**
     * 创建成功事件
     */
    public static AIEvent createSuccessEvent(Object source,
                                           AIOperationTypeEnum operationType,
                                           String operationId,
                                           Map<String, Object> result,
                                           long duration,
                                           Map<String, Object> parameters,
                                           String userId,
                                           String modelId) {
        return new AIEvent(source, operationType, operationId, "success", result, null, duration, parameters, userId, modelId);
    }
    
    /**
     * 创建失败事件
     */
    public static AIEvent createFailedEvent(Object source,
                                          AIOperationTypeEnum operationType,
                                          String operationId,
                                          String errorMessage,
                                          long duration,
                                          Map<String, Object> parameters,
                                          String userId,
                                          String modelId) {
        return new AIEvent(source, operationType, operationId, "failed", null, errorMessage, duration, parameters, userId, modelId);
    }
    
    /**
     * 创建处理中事件
     */
    public static AIEvent createProcessingEvent(Object source,
                                              AIOperationTypeEnum operationType,
                                              String operationId,
                                              Map<String, Object> parameters,
                                              String userId,
                                              String modelId) {
        return new AIEvent(source, operationType, operationId, "processing", null, null, 0, parameters, userId, modelId);
    }
    
    // Getters
    public AIOperationTypeEnum getOperationType() {
        return operationType;
    }
    
    public String getOperationId() {
        return operationId;
    }
    
    public LocalDateTime getOperationTime() {
        return operationTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Map<String, Object> getResult() {
        return result;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getModelId() {
        return modelId;
    }
    
    /**
     * 是否是成功事件
     */
    public boolean isSuccess() {
        return "success".equals(status);
    }
    
    /**
     * 是否是失败事件
     */
    public boolean isFailed() {
        return "failed".equals(status);
    }
    
    /**
     * 是否是处理中事件
     */
    public boolean isProcessing() {
        return "processing".equals(status);
    }
    
    @Override
    public String toString() {
        return String.format("AIEvent{operationType=%s, operationId='%s', status='%s', duration=%d}",
                operationType, operationId, status, duration);
    }
} 