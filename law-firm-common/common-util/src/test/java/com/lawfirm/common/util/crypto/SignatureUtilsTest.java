package com.lawfirm.common.util.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SignatureUtilsTest {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private static final String TEST_CONTENT = "Hello World!";

    @BeforeEach
    void setUp() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    @Test
    void testSignAndVerify() {
        byte[] data = TEST_CONTENT.getBytes(StandardCharsets.UTF_8);
        
        // 测试正常签名
        byte[] signature = SignatureUtils.sign(data, privateKey);
        assertNotNull(signature);
        
        // 测试正常验签
        boolean verified = SignatureUtils.verify(data, signature, publicKey);
        assertTrue(verified);
        
        // 测试数据被篡改的情况
        byte[] tamperedData = "Tampered Data".getBytes(StandardCharsets.UTF_8);
        verified = SignatureUtils.verify(tamperedData, signature, publicKey);
        assertFalse(verified);
    }

    @Test
    void testSignAndVerifyWithInvalidKeys() throws Exception {
        byte[] data = TEST_CONTENT.getBytes(StandardCharsets.UTF_8);
        
        // 测试私钥为null的情况
        byte[] signature = SignatureUtils.sign(data, null);
        assertNull(signature);
        
        // 生成一个不匹配的密钥对
        KeyPair anotherKeyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        
        // 测试使用不匹配的公钥验签
        signature = SignatureUtils.sign(data, privateKey);
        boolean verified = SignatureUtils.verify(data, signature, anotherKeyPair.getPublic());
        assertFalse(verified);
    }

    @Test
    void testSignAndVerifyHex() {
        // 测试正常的十六进制签名
        String signature = SignatureUtils.signHex(TEST_CONTENT, privateKey);
        assertNotNull(signature);
        
        // 测试正常的十六进制验签
        boolean verified = SignatureUtils.verifyHex(TEST_CONTENT, signature, publicKey);
        assertTrue(verified);
        
        // 测试内容被篡改的情况
        verified = SignatureUtils.verifyHex("Tampered Content", signature, publicKey);
        assertFalse(verified);
        
        // 测试签名被篡改的情况
        verified = SignatureUtils.verifyHex(TEST_CONTENT, signature + "abc", publicKey);
        assertFalse(verified);
    }

    // 大数据量测试
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testLargeDataSignAndVerify() {
        // 生成1MB的测试数据
        StringBuilder largeData = new StringBuilder();
        for (int i = 0; i < 1024 * 1024; i++) {
            largeData.append('a');
        }
        
        byte[] data = largeData.toString().getBytes(StandardCharsets.UTF_8);
        
        // 测试签名性能
        long startTime = System.currentTimeMillis();
        byte[] signature = SignatureUtils.sign(data, privateKey);
        long signTime = System.currentTimeMillis() - startTime;
        
        assertNotNull(signature);
        
        // 测试验签性能
        startTime = System.currentTimeMillis();
        boolean verified = SignatureUtils.verify(data, signature, publicKey);
        long verifyTime = System.currentTimeMillis() - startTime;
        
        assertTrue(verified);
        
        // 输出性能数据
        System.out.printf("Large data (1MB) - Sign time: %dms, Verify time: %dms%n", 
                signTime, verifyTime);
    }

    // 并发测试
    @Test
    void testConcurrentSignAndVerify() throws InterruptedException, ExecutionException {
        int threadCount = 10;
        int operationsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Future<Boolean>> futures = new ArrayList<>();

        // 创建并发任务
        for (int i = 0; i < threadCount; i++) {
            Future<Boolean> future = executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        String content = TEST_CONTENT + j;
                        byte[] data = content.getBytes(StandardCharsets.UTF_8);
                        
                        byte[] signature = SignatureUtils.sign(data, privateKey);
                        if (!SignatureUtils.verify(data, signature, publicKey)) {
                            return false;
                        }
                    }
                    return true;
                } finally {
                    latch.countDown();
                }
            });
            futures.add(future);
        }

        // 等待所有任务完成
        latch.await(30, TimeUnit.SECONDS);
        executor.shutdown();

        // 验证所有任务的结果
        for (Future<Boolean> future : futures) {
            assertTrue(future.get());
        }
    }

    // 性能测试
    @Test
    void testSignAndVerifyPerformance() {
        int iterations = 1000;
        byte[] data = TEST_CONTENT.getBytes(StandardCharsets.UTF_8);

        // 预热
        for (int i = 0; i < 100; i++) {
            byte[] signature = SignatureUtils.sign(data, privateKey);
            SignatureUtils.verify(data, signature, publicKey);
        }

        // 测试签名性能
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            SignatureUtils.sign(data, privateKey);
        }
        long signTime = System.currentTimeMillis() - startTime;

        // 测试验签性能
        byte[] signature = SignatureUtils.sign(data, privateKey);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            SignatureUtils.verify(data, signature, publicKey);
        }
        long verifyTime = System.currentTimeMillis() - startTime;

        // 输出性能数据
        System.out.printf("Performance test (%d iterations) - Average sign time: %.2fms, Average verify time: %.2fms%n",
                iterations, (double) signTime / iterations, (double) verifyTime / iterations);
    }

    // 边界测试
    @ParameterizedTest
    @ValueSource(strings = {
        "",                         // 空字符串
        " ",                        // 空格
        "\n",                       // 换行符
        "\t",                       // 制表符
        "!@#$%^&*()_+",            // 特殊字符
        "测试中文签名验证",          // 中文字符
        "🎉🌟🎨"                    // Emoji字符
    })
    void testBoundaryValues(String input) {
        // 测试签名
        String signature = SignatureUtils.signHex(input, privateKey);
        assertNotNull(signature);
        
        // 测试验签
        boolean verified = SignatureUtils.verifyHex(input, signature, publicKey);
        assertTrue(verified);
    }
} 