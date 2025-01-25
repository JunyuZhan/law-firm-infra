package com.lawfirm.document.service;

import com.lawfirm.document.DocumentApplication;
import com.lawfirm.model.document.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = DocumentApplication.class)
@Sql("/sql/document-test-data.sql")
class DocumentPreviewPerformanceTest {

    @Autowired
    private DocumentPreviewService previewService;

    @Autowired
    private DocumentService documentService;

    private static final int CONCURRENT_USERS = 50;
    private static final int REQUESTS_PER_USER = 20;
    private static final long MAX_RESPONSE_TIME_MS = 500; // 最大响应时间500ms

    private ExecutorService executorService;
    private List<Document> testDocuments;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(CONCURRENT_USERS);
        testDocuments = documentService.list(); // 获取测试数据
        assertFalse(testDocuments.isEmpty(), "测试数据不能为空");
    }

    @Test
    void generatePreviewUrl_Performance() {
        // 准备并发任务
        List<CompletableFuture<Long>> futures = new ArrayList<>();
        
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                long totalTime = 0;
                for (int j = 0; j < REQUESTS_PER_USER; j++) {
                    Document doc = getRandomDocument();
                    long start = System.currentTimeMillis();
                    try {
                        previewService.generatePreviewUrl(doc.getId());
                        long end = System.currentTimeMillis();
                        totalTime += (end - start);
                    } catch (Exception e) {
                        fail("生成预览URL失败: " + e.getMessage());
                    }
                }
                return totalTime / REQUESTS_PER_USER; // 计算平均响应时间
            }, executorService);
            futures.add(future);
        }

        // 等待所有任务完成并计算统计信息
        List<Long> responseTimes = futures.stream()
            .map(CompletableFuture::join)
            .toList();

        // 计算平均响应时间
        double avgResponseTime = responseTimes.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);

        // 计算95%响应时间
        List<Long> sortedTimes = new ArrayList<>(responseTimes);
        sortedTimes.sort(Long::compareTo);
        int index95 = (int) Math.ceil(sortedTimes.size() * 0.95) - 1;
        long percentile95 = sortedTimes.get(index95);

        log.info("性能测试结果:");
        log.info("并发用户数: {}", CONCURRENT_USERS);
        log.info("每用户请求数: {}", REQUESTS_PER_USER);
        log.info("平均响应时间: {}ms", String.format("%.2f", avgResponseTime));
        log.info("95%响应时间: {}ms", percentile95);

        // 验证性能指标
        assertTrue(avgResponseTime < MAX_RESPONSE_TIME_MS, 
            String.format("平均响应时间(%.2fms)超过最大限制(%dms)", avgResponseTime, MAX_RESPONSE_TIME_MS));
    }

    @Test
    void generateThumbnail_Performance() {
        // 准备并发任务
        List<CompletableFuture<Long>> futures = new ArrayList<>();
        
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                long totalTime = 0;
                for (int j = 0; j < REQUESTS_PER_USER; j++) {
                    Document doc = getRandomDocument();
                    long start = System.currentTimeMillis();
                    try {
                        previewService.generateThumbnail(doc.getId(), 200, 200);
                        long end = System.currentTimeMillis();
                        totalTime += (end - start);
                    } catch (Exception e) {
                        // 忽略不支持缩略图的文档类型
                        if (!e.getMessage().contains("不支持的文件类型")) {
                            fail("生成缩略图失败: " + e.getMessage());
                        }
                    }
                }
                return totalTime / REQUESTS_PER_USER;
            }, executorService);
            futures.add(future);
        }

        // 等待所有任务完成并计算统计信息
        List<Long> responseTimes = futures.stream()
            .map(CompletableFuture::join)
            .toList();

        double avgResponseTime = responseTimes.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);

        List<Long> sortedTimes = new ArrayList<>(responseTimes);
        sortedTimes.sort(Long::compareTo);
        int index95 = (int) Math.ceil(sortedTimes.size() * 0.95) - 1;
        long percentile95 = sortedTimes.get(index95);

        log.info("缩略图生成性能测试结果:");
        log.info("并发用户数: {}", CONCURRENT_USERS);
        log.info("每用户请求数: {}", REQUESTS_PER_USER);
        log.info("平均响应时间: {}ms", String.format("%.2f", avgResponseTime));
        log.info("95%响应时间: {}ms", percentile95);

        assertTrue(avgResponseTime < MAX_RESPONSE_TIME_MS * 2, // 缩略图生成允许更长的响应时间
            String.format("平均响应时间(%.2fms)超过最大限制(%dms)", avgResponseTime, MAX_RESPONSE_TIME_MS * 2));
    }

    private Document getRandomDocument() {
        int index = (int) (Math.random() * testDocuments.size());
        return testDocuments.get(index);
    }
} 