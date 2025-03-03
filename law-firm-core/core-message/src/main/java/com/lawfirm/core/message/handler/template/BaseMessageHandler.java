package com.lawfirm.core.message.handler.template;

import com.lawfirm.core.message.exception.MessageProcessException;
import com.lawfirm.core.message.utils.MessageLogUtils;

public abstract class BaseMessageHandler<T> {
    
    public final void handle(String messageId, T message) {
        try {
            // 1. 前置处理
            MessageLogUtils.logMessageProcess(messageId, "PRE_PROCESS");
            preProcess(messageId, message);

            // 2. 业务处理
            MessageLogUtils.logMessageProcess(messageId, "PROCESSING");
            doHandle(messageId, message);

            // 3. 后置处理
            MessageLogUtils.logMessageProcess(messageId, "POST_PROCESS");
            postProcess(messageId, message);

            // 4. 完成处理
            MessageLogUtils.logMessageProcess(messageId, "COMPLETED");
        } catch (Exception e) {
            MessageLogUtils.logMessageError(messageId, "处理失败", e);
            throw new MessageProcessException("消息处理失败: " + e.getMessage(), e);
        }
    }

    /**
     * 前置处理
     */
    protected void preProcess(String messageId, T message) {
        // 默认空实现，子类可以覆盖
    }

    /**
     * 具体的业务处理逻辑
     */
    protected abstract void doHandle(String messageId, T message);

    /**
     * 后置处理
     */
    protected void postProcess(String messageId, T message) {
        // 默认空实现，子类可以覆盖
    }
} 