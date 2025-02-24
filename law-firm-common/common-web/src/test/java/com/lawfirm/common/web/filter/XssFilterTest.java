package com.lawfirm.common.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class XssFilterTest {

    private XssFilter xssFilter;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        xssFilter = new XssFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testXssFiltering() throws Exception {
        // 设置包含XSS攻击的参数
        request.setParameter("normalParam", "normal value");
        request.setParameter("xssParam", "<script>alert('xss')</script>");
        
        // 执行过滤
        xssFilter.doFilter(request, response, filterChain);
        
        // 捕获被包装的请求
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));
        
        // 获取被包装的请求
        HttpServletRequest wrappedRequest = requestCaptor.getValue();
        
        // 验证过滤后的参数
        String filteredValue = wrappedRequest.getParameter("xssParam");
        assertNotNull(filteredValue);
        assertFalse(filteredValue.contains("<script>"));
        assertTrue(filteredValue.contains("alert"));
        
        // 验证普通参数未被过滤
        assertEquals("normal value", wrappedRequest.getParameter("normalParam"));
    }

    @Test
    void testNormalParameterNotFiltered() throws Exception {
        // 设置普通参数
        String normalValue = "This is a normal value";
        request.setParameter("param", normalValue);
        
        // 执行过滤
        xssFilter.doFilter(request, response, filterChain);
        
        // 捕获被包装的请求
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));
        
        // 获取被包装的请求并验证参数未被改变
        HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertEquals(normalValue, wrappedRequest.getParameter("param"));
    }
} 