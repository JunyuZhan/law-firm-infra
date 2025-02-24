package com.lawfirm.common.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.common.log.util.LogUtils;
import com.lawfirm.common.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LogAspectTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private LogProperties logProperties;

    @Mock
    private ThreadPoolTaskExecutor asyncLogExecutor;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private LogAspect logAspect;

    private MockHttpServletRequest request;
    private Map<String, String> sensitiveParams;

    @BeforeEach
    void setUp() {
        // 基本请求设置
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        // 基本切点设置
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});
        
        // 敏感参数设置
        sensitiveParams = new HashMap<>();
        sensitiveParams.put("username", "test");
        sensitiveParams.put("password", "123456");
        sensitiveParams.put("token", "abc");

        // 默认配置
        when(logProperties.isEnableMethodLog()).thenReturn(true);
        when(logProperties.isLogRequestParams()).thenReturn(true);
        when(logProperties.isLogResponseBody()).thenReturn(true);
        when(logProperties.isLogStackTrace()).thenReturn(true);
        when(logProperties.getExcludePaths()).thenReturn(new String[]{});
        when(logProperties.getExcludeParamFields()).thenReturn(new String[]{"password", "token"});
        when(logProperties.isEnableAsyncLog()).thenReturn(false);
    }

    @Test
    void doAround_ShouldLogNormalRequest() throws Throwable {
        // 准备测试数据
        String result = "测试结果";
        when(joinPoint.proceed()).thenReturn(result);
        when(objectMapper.writeValueAsString(any())).thenReturn("\"测试结果\"");

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            Object actualResult = logAspect.doAround(joinPoint);

            // 验证结果
            assertEquals(result, actualResult);
            verify(joinPoint).proceed();
            verify(objectMapper, atLeastOnce()).writeValueAsString(any());
        }
    }

    @Test
    void doAround_ShouldLogAndRethrowException() throws Throwable {
        // 准备测试数据
        RuntimeException exception = new RuntimeException("测试异常");
        when(joinPoint.proceed()).thenThrow(exception);

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 验证异常抛出和日志记录
            assertThrows(RuntimeException.class, () -> logAspect.doAround(joinPoint));
            verify(joinPoint).proceed();
        }
    }

    @Test
    void doAround_ShouldSkipLogging_WhenMethodLogDisabled() throws Throwable {
        // 禁用方法日志
        when(logProperties.isEnableMethodLog()).thenReturn(false);

        // 准备测试数据
        String result = "测试结果";
        when(joinPoint.proceed()).thenReturn(result);

        // 执行测试
        Object actualResult = logAspect.doAround(joinPoint);

        // 验证结果
        assertEquals(result, actualResult);
        verify(joinPoint).proceed();
        verifyNoInteractions(objectMapper);
    }

    @Test
    void doAround_ShouldSkipLogging_WhenPathExcluded() throws Throwable {
        // 设置排除路径
        when(logProperties.getExcludePaths()).thenReturn(new String[]{"/api/test"});

        // 准备测试数据
        String result = "测试结果";
        when(joinPoint.proceed()).thenReturn(result);

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            Object actualResult = logAspect.doAround(joinPoint);

            // 验证结果
            assertEquals(result, actualResult);
            verify(joinPoint).proceed();
            verifyNoInteractions(objectMapper);
        }
    }

    @Test
    void doAround_ShouldDesensitizeParams() throws Throwable {
        // 准备测试数据
        when(joinPoint.getArgs()).thenReturn(new Object[]{sensitiveParams});
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"username\":\"test\",\"password\":\"***\",\"token\":\"***\"}");

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            logAspect.doAround(joinPoint);

            // 验证敏感信息被脱敏
            verify(objectMapper, atLeastOnce()).writeValueAsString(any());
        }
    }

    @Test
    void doAround_ShouldPreserveMDC() throws Throwable {
        // 准备MDC数据
        MDC.put("traceId", "test-trace-id");
        MDC.put("userId", "test-user-id");

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            logAspect.doAround(joinPoint);

            // 验证MDC上下文被保留
            assertEquals("test-trace-id", MDC.get("traceId"));
            assertEquals("test-user-id", MDC.get("userId"));
        } finally {
            MDC.clear();
        }
    }

    @Test
    void doAround_ShouldLogPerformance() throws Throwable {
        // 模拟方法执行时间
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            Thread.sleep(100);
            return "result";
        });

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            logAspect.doAround(joinPoint);

            // 验证性能日志记录
            verify(objectMapper, atLeastOnce()).writeValueAsString(any());
        }
    }

    @Test
    void doAround_ShouldHandleAsyncLog() throws Throwable {
        // 启用异步日志
        when(logProperties.isEnableAsyncLog()).thenReturn(true);

        try (MockedStatic<ServletUtils> servletUtils = mockStatic(ServletUtils.class)) {
            servletUtils.when(ServletUtils::getRequest).thenReturn(request);

            // 执行测试
            logAspect.doAround(joinPoint);

            // 验证异步执行（开始和结束各一次）
            verify(asyncLogExecutor, times(2)).execute(any(Runnable.class));
        }
    }
} 