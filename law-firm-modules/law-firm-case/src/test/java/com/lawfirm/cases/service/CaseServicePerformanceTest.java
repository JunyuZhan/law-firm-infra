package com.lawfirm.cases.service;

import com.lawfirm.cases.service.impl.CaseServiceImpl;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.query.CaseQuery;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CaseServicePerformanceTest {

    @Autowired
    private CaseService caseService;

    private static final int CONCURRENT_USERS = 10;
    private static final int REQUESTS_PER_USER = 100;
    private static final int TOTAL_REQUESTS = CONCURRENT_USERS * REQUESTS_PER_USER;

    @Test
    void testConcurrentQueries() throws InterruptedException {
        // 准备测试数据
        List<Case> testCases = prepareTestData();
        
        // 创建线程池和计数器
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_USERS);
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 提交并发任务
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_USER; j++) {
                    try {
                        // 执行查询操作
                        List<CaseDetailVO> cases = caseService.findByLawyer("test-lawyer", new CaseQuery());
                        assertNotNull(cases);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        
        // 等待所有任务完成
        boolean completed = latch.await(1, TimeUnit.MINUTES);
        assertTrue(completed, "Performance test did not complete within the timeout");
        
        // 计算执行时间和TPS
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double tps = TOTAL_REQUESTS / (totalTime / 1000.0);
        
        // 验证性能指标
        assertTrue(tps >= 100, "TPS should be at least 100, actual: " + tps);
        assertTrue(totalTime <= 30000, "Total time should be less than 30 seconds");
        
        // 关闭线程池
        executor.shutdown();
    }

    @Test
    void testConcurrentStatusUpdates() throws InterruptedException {
        // 准备测试数据
        List<Case> testCases = prepareTestData();
        
        // 创建线程池和计数器
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENT_USERS);
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 提交并发任务
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_USER; j++) {
                    try {
                        // 执行状态更新操作
                        caseService.validateCase(1L, "test-operator");
                    } catch (Exception e) {
                        // 忽略并发更新异常
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        
        // 等待所有任务完成
        boolean completed = latch.await(1, TimeUnit.MINUTES);
        assertTrue(completed, "Performance test did not complete within the timeout");
        
        // 计算执行时间和TPS
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double tps = TOTAL_REQUESTS / (totalTime / 1000.0);
        
        // 验证性能指标
        assertTrue(tps >= 50, "TPS should be at least 50 for updates, actual: " + tps);
        assertTrue(totalTime <= 30000, "Total time should be less than 30 seconds");
        
        // 关闭线程池
        executor.shutdown();
    }

    private List<Case> prepareTestData() {
        List<Case> testCases = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Case testCase = new Case();
            testCase.setId((long) i);
            testCase.setCaseNumber("TEST-" + i);
            testCase.setCaseName("测试案件" + i);
            testCase.setCaseStatus(CaseStatusEnum.DRAFT);
            testCase.setPrincipalId("test-lawyer");
            testCases.add(testCase);
        }
        return testCases;
    }
} 