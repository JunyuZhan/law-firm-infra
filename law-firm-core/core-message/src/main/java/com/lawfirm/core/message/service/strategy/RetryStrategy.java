package com.lawfirm.core.message.service.strategy;

import com.lawfirm.core.message.config.MessageConfig;
import com.lawfirm.core.message.exception.MessageException;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 消息重试策略
 */
@Component
@RequiredArgsConstructor
public class RetryStrategy {

    private final MessageConfig messageConfig;
    private final RetryTemplate retryTemplate;

    /**
     * 执行带重试的消息操作
     */
    public void execute(String messageId, BaseMessage message, Consumer<BaseMessage> action) {
        retryTemplate.execute(context -> {
            action.accept(message);
            return null;
        }, context -> {
            throw new MessageException("消息处理失败，已重试" + context.getRetryCount() + "次");
        });
    }
}