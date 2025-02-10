package com.lawfirm.core.message.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 消息系统监控指标
 */
@Slf4j
@Component
public class MessageMetrics {

    private final Counter totalMessagesSent;
    private final Counter messagesSentSuccess;
    private final Counter messagesSentFailed;
    private final Counter totalTemplateMessagesSent;
    private final Counter templateMessagesSentSuccess; 
    private final Counter templateMessagesSentFailed;
    private final Counter totalSystemNoticesSent;
    private final Timer messageSendLatency;
    private final Timer templateMessageSendLatency;
    private final Timer systemNoticeSendLatency;
    private final Timer messageReadLatency;
    private final Timer messageQueryLatency;

    public MessageMetrics(MeterRegistry registry) {
        // 消息发送计数器
        this.totalMessagesSent = Counter.builder("message.sent.total")
                .description("消息发送总数")
                .register(registry);
                
        this.messagesSentSuccess = Counter.builder("message.sent.success")
                .description("消息发送成功数")
                .register(registry);
                
        this.messagesSentFailed = Counter.builder("message.sent.failed") 
                .description("消息发送失败数")
                .register(registry);

        // 模板消息计数器
        this.totalTemplateMessagesSent = Counter.builder("message.template.sent.total")
                .description("模板消息发送总数")
                .register(registry);
                
        this.templateMessagesSentSuccess = Counter.builder("message.template.sent.success")
                .description("模板消息发送成功数")
                .register(registry);
                
        this.templateMessagesSentFailed = Counter.builder("message.template.sent.failed")
                .description("模板消息发送失败数")
                .register(registry);

        // 系统通知计数器
        this.totalSystemNoticesSent = Counter.builder("message.notice.sent.total")
                .description("系统通知发送总数")
                .register(registry);

        // 延迟计时器
        this.messageSendLatency = Timer.builder("message.send.latency")
                .description("消息发送延迟")
                .register(registry);
                
        this.templateMessageSendLatency = Timer.builder("message.template.send.latency")
                .description("模板消息发送延迟")
                .register(registry);
                
        this.systemNoticeSendLatency = Timer.builder("message.notice.send.latency")
                .description("系统通知发送延迟")
                .register(registry);
                
        this.messageReadLatency = Timer.builder("message.read.latency")
                .description("消息读取延迟")
                .register(registry);
                
        this.messageQueryLatency = Timer.builder("message.query.latency")
                .description("消息查询延迟")
                .register(registry);
    }

    /**
     * 记录普通消息发送
     */
    public void recordMessageSend(boolean success, long startTime) {
        totalMessagesSent.increment();
        if (success) {
            messagesSentSuccess.increment();
        } else {
            messagesSentFailed.increment();
        }
        messageSendLatency.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 记录模板消息发送
     */
    public void recordTemplateMessageSend(boolean success, long startTime) {
        totalTemplateMessagesSent.increment();
        if (success) {
            templateMessagesSentSuccess.increment();
        } else {
            templateMessagesSentFailed.increment();
        }
        templateMessageSendLatency.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 记录系统通知发送
     */
    public void recordSystemNoticeSend(long startTime) {
        totalSystemNoticesSent.increment();
        systemNoticeSendLatency.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 记录消息读取延迟
     */
    public void recordMessageRead(long startTime) {
        messageReadLatency.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 记录消息查询延迟
     */
    public void recordMessageQuery(long startTime) {
        messageQueryLatency.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }
} 