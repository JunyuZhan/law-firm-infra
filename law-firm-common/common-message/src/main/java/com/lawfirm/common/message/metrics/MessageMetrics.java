package com.lawfirm.common.message.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 消息监控指标
 */
@Slf4j
public class MessageMetrics {
    
    private Counter totalMessageCounter;
    private Counter successMessageCounter;
    private Counter failureMessageCounter;
    private Counter retryMessageCounter;
    private Counter readMessageCounter;
    private Counter recallMessageCounter;
    private Timer messageSendTimer;
    private Timer messageProcessTimer;
    
    @Autowired
    public void setMeterRegistry(MeterRegistry registry) {
        this.totalMessageCounter = Counter.builder("message.total")
                .description("Total number of messages")
                .register(registry);
                
        this.successMessageCounter = Counter.builder("message.success")
                .description("Number of successfully sent messages")
                .register(registry);
                
        this.failureMessageCounter = Counter.builder("message.failure")
                .description("Number of failed messages")
                .register(registry);
                
        this.retryMessageCounter = Counter.builder("message.retry")
                .description("Number of retried messages")
                .register(registry);
                
        this.readMessageCounter = Counter.builder("message.read")
                .description("Number of read messages")
                .register(registry);
                
        this.recallMessageCounter = Counter.builder("message.recall")
                .description("Number of recalled messages")
                .register(registry);
                
        this.messageSendTimer = Timer.builder("message.send.time")
                .description("Time taken to send messages")
                .register(registry);
                
        this.messageProcessTimer = Timer.builder("message.process.time")
                .description("Time taken to process messages")
                .register(registry);
    }
    
    public void recordMessageSend(long startTime) {
        totalMessageCounter.increment();
        successMessageCounter.increment();
        messageSendTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }
    
    public void recordMessageSendFailure(long startTime) {
        failureMessageCounter.increment();
        messageSendTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }
    
    public void recordMessageRetry() {
        retryMessageCounter.increment();
    }
    
    public void recordMessageRead() {
        readMessageCounter.increment();
    }
    
    public void recordMessageRecall() {
        recallMessageCounter.increment();
    }
    
    public void recordMessageProcess(long startTime) {
        messageProcessTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    }
    
    public long getTotalMessageCount() {
        return (long) totalMessageCounter.count();
    }
    
    public long getSuccessMessageCount() {
        return (long) successMessageCounter.count();
    }
    
    public long getFailureMessageCount() {
        return (long) failureMessageCounter.count();
    }
    
    public long getRetryMessageCount() {
        return (long) retryMessageCounter.count();
    }
    
    public long getReadMessageCount() {
        return (long) readMessageCounter.count();
    }
    
    public long getRecallMessageCount() {
        return (long) recallMessageCounter.count();
    }
} 