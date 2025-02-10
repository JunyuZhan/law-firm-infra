package com.lawfirm.common.message.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageMetricsTest {

    private MessageMetrics messageMetrics;
    private MeterRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new SimpleMeterRegistry();
        messageMetrics = new MessageMetrics();
        messageMetrics.setMeterRegistry(registry);
    }

    @Test
    void shouldRecordMessageSend() {
        messageMetrics.recordMessageSend(System.currentTimeMillis() - 100);

        assertEquals(1, messageMetrics.getTotalMessageCount());
        assertEquals(1, messageMetrics.getSuccessMessageCount());
    }

    @Test
    void shouldRecordMessageSendFailure() {
        messageMetrics.recordMessageSendFailure(System.currentTimeMillis() - 100);

        assertEquals(1, messageMetrics.getFailureMessageCount());
    }

    @Test
    void shouldRecordMessageRetry() {
        messageMetrics.recordMessageRetry();
        messageMetrics.recordMessageRetry();

        assertEquals(2, messageMetrics.getRetryMessageCount());
    }

    @Test
    void shouldRecordMessageRead() {
        messageMetrics.recordMessageRead();
        messageMetrics.recordMessageRead();
        messageMetrics.recordMessageRead();

        assertEquals(3, messageMetrics.getReadMessageCount());
    }

    @Test
    void shouldRecordMessageRecall() {
        messageMetrics.recordMessageRecall();

        assertEquals(1, messageMetrics.getRecallMessageCount());
    }

    @Test
    void shouldRecordMessageProcess() {
        long startTime = System.currentTimeMillis() - 200;
        messageMetrics.recordMessageProcess(startTime);

        // Timer metrics are recorded
        assertEquals(1, registry.get("message.process.time").timer().count());
    }

    @Test
    void shouldAccumulateMetrics() {
        // Record multiple events
        messageMetrics.recordMessageSend(System.currentTimeMillis() - 100);
        messageMetrics.recordMessageSend(System.currentTimeMillis() - 100);
        messageMetrics.recordMessageSendFailure(System.currentTimeMillis() - 100);
        messageMetrics.recordMessageRetry();
        messageMetrics.recordMessageRead();
        messageMetrics.recordMessageRecall();

        // Verify accumulated counts
        assertEquals(2, messageMetrics.getTotalMessageCount());
        assertEquals(2, messageMetrics.getSuccessMessageCount());
        assertEquals(1, messageMetrics.getFailureMessageCount());
        assertEquals(1, messageMetrics.getRetryMessageCount());
        assertEquals(1, messageMetrics.getReadMessageCount());
        assertEquals(1, messageMetrics.getRecallMessageCount());
    }
} 