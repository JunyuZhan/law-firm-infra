package com.lawfirm.core.message.performance;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class MessageServicePerformanceTest {

    @Autowired
    private BusinessMessageService messageService;

    private static final int CONCURRENT_USERS = 10;
    private static final int MESSAGES_PER_USER = 100;
    private static final int TOTAL_MESSAGES = CONCURRENT_USERS * MESSAGES_PER_USER;
    private static final Duration EXPECTED_DURATION = Duration.ofSeconds(30);

    @Test
    void massMessageSending() {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_USERS);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 记录开始时间
        Instant start = Instant.now();

        // 模拟多个用户并发发送消息
        for (int userId = 1; userId <= CONCURRENT_USERS; userId++) {
            final long currentUserId = userId;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < MESSAGES_PER_USER; i++) {
                    MessageEntity message = createTestMessage(currentUserId);
                    messageService.sendMessage(message);
                }
            }, executorService);
            futures.add(future);
        }

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 计算总耗时
        Duration duration = Duration.between(start, Instant.now());
        
        // 计算性能指标
        double messagesPerSecond = TOTAL_MESSAGES / (duration.toMillis() / 1000.0);
        System.out.printf("Sent %d messages in %d ms (%.2f messages/second)%n",
                TOTAL_MESSAGES, duration.toMillis(), messagesPerSecond);

        // 验证性能符合预期
        assertTrue(duration.compareTo(EXPECTED_DURATION) < 0, 
                "Performance test took too long: " + duration);
        assertTrue(messagesPerSecond > 50, 
                "Message throughput too low: " + messagesPerSecond);

        // 关闭线程池
        executorService.shutdown();
    }

    @Test
    void messageReadingPerformance() {
        // 记录开始时间
        Instant start = Instant.now();

        // 批量查询消息
        for (int i = 0; i < 100; i++) {
            messageService.listMessages(1L, i, 10);
        }

        // 计算总耗时
        Duration duration = Duration.between(start, Instant.now());
        System.out.printf("Performed 100 page queries in %d ms%n", duration.toMillis());

        // 验证性能符合预期
        assertTrue(duration.compareTo(Duration.ofSeconds(5)) < 0,
                "Query performance test took too long: " + duration);
    }

    private MessageEntity createTestMessage(long userId) {
        MessageEntity message = new MessageEntity();
        message.setTitle("Performance Test Message");
        message.setContent("Test Content");
        message.setType(MessageType.SYSTEM_NOTICE);
        message.setReceiverId(userId);
        return message;
    }
} 