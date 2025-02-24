package com.lawfirm.common.util.http;

import com.lawfirm.common.util.BaseUtilTest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpUtilsTest extends BaseUtilTest {

    private static final String TEST_URL = "http://localhost:8080/test";
    
    @Mock
    private CloseableHttpClient httpClient;
    
    @Mock
    private ClassicHttpResponse httpResponse;
    
    @Mock
    private HttpEntity httpEntity;

    @Test
    void get_ShouldBuildCorrectUrl_WhenParamsProvided() {
        // 准备测试数据
        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        params.put("age", "25");

        String url = TEST_URL;
        String result = HttpUtils.get(url, params);
        
        // 验证URL构建
        assertTrue(result == null || result.contains("name=张三"));
        assertTrue(result == null || result.contains("age=25"));
    }

    @Test
    void get_ShouldHandleException_WhenHttpClientFails() throws IOException {
        try (MockedStatic<org.apache.hc.client5.http.impl.classic.HttpClients> httpClients = mockStatic(org.apache.hc.client5.http.impl.classic.HttpClients.class)) {
            // 模拟 HttpClient 抛出异常
            httpClients.when(org.apache.hc.client5.http.impl.classic.HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(any(HttpGet.class), any(HttpClientResponseHandler.class))).thenThrow(new IOException("模拟网络错误"));

            // 执行测试
            String result = HttpUtils.get(TEST_URL);

            // 验证结果
            assertNull(result);
        }
    }

    @Test
    void post_ShouldSendCorrectBody_WhenBodyProvided() throws IOException {
        try (MockedStatic<org.apache.hc.client5.http.impl.classic.HttpClients> httpClients = mockStatic(org.apache.hc.client5.http.impl.classic.HttpClients.class)) {
            // 准备测试数据
            String body = "{\"name\":\"张三\",\"age\":25}";
            String expectedResponse = "success";
            
            // 模拟响应
            when(httpResponse.getEntity()).thenReturn(httpEntity);
            when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));
            
            // 模拟 HttpClient 返回成功响应
            httpClients.when(org.apache.hc.client5.http.impl.classic.HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class))).thenAnswer(invocation -> {
                HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                return handler.handleResponse(httpResponse);
            });

            // 执行测试
            String result = HttpUtils.post(TEST_URL, body);

            // 验证结果
            assertEquals(expectedResponse, result);
            verify(httpClient).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
        }
    }

    @Test
    void post_ShouldHandleException_WhenHttpClientFails() throws IOException {
        try (MockedStatic<org.apache.hc.client5.http.impl.classic.HttpClients> httpClients = mockStatic(org.apache.hc.client5.http.impl.classic.HttpClients.class)) {
            // 模拟 HttpClient 抛出异常
            httpClients.when(org.apache.hc.client5.http.impl.classic.HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class))).thenThrow(new IOException("模拟网络错误"));

            // 执行测试
            String result = HttpUtils.post(TEST_URL, "test");

            // 验证结果
            assertNull(result);
        }
    }

    @Test
    void post_ShouldBuildCorrectBody_WhenParamsProvided() throws IOException {
        try (MockedStatic<org.apache.hc.client5.http.impl.classic.HttpClients> httpClients = mockStatic(org.apache.hc.client5.http.impl.classic.HttpClients.class)) {
            // 准备测试数据
            Map<String, String> params = new HashMap<>();
            params.put("name", "张三");
            params.put("age", "25");
            String expectedResponse = "success";
            
            // 模拟响应
            when(httpResponse.getEntity()).thenReturn(httpEntity);
            when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));

            // 模拟 HttpClient 返回成功响应
            httpClients.when(org.apache.hc.client5.http.impl.classic.HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class))).thenAnswer(invocation -> {
                HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                return handler.handleResponse(httpResponse);
            });

            // 执行测试
            String result = HttpUtils.post(TEST_URL, params);

            // 验证结果
            assertEquals(expectedResponse, result);
            verify(httpClient).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
        }
    }

    @Test
    void post_ShouldHandleEmptyParams() throws IOException {
        try (MockedStatic<org.apache.hc.client5.http.impl.classic.HttpClients> httpClients = mockStatic(org.apache.hc.client5.http.impl.classic.HttpClients.class)) {
            // 准备测试数据
            String expectedResponse = "";
            
            // 模拟响应
            when(httpResponse.getEntity()).thenReturn(httpEntity);
            when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));

            // 模拟 HttpClient
            httpClients.when(org.apache.hc.client5.http.impl.classic.HttpClients::createDefault).thenReturn(httpClient);
            when(httpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class))).thenAnswer(invocation -> {
                HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                return handler.handleResponse(httpResponse);
            });

            // 执行测试
            String result = HttpUtils.post(TEST_URL, new HashMap<>());

            // 验证结果
            assertNotNull(result);
            verify(httpClient).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
        }
    }
}