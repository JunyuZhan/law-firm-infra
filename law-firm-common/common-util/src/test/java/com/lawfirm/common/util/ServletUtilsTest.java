package com.lawfirm.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class ServletUtilsTest extends BaseUtilTest {

    @Test
    void getRequest_ShouldReturnCurrentRequest() {
        // 准备测试数据
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        try {
            // 执行测试
            HttpServletRequest result = ServletUtils.getRequest();

            // 验证结果
            assertNotNull(result);
            assertEquals(request, result);
        } finally {
            // 清理测试环境
            RequestContextHolder.resetRequestAttributes();
        }
    }

    @Test
    void getRequest_ShouldThrowException_WhenNoRequestContext() {
        // 确保没有 Request 上下文
        RequestContextHolder.resetRequestAttributes();

        // 验证异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            ServletUtils::getRequest);
        assertEquals("当前线程中不存在Request上下文", exception.getMessage());
    }

    @Test
    void getLoginUserId_ShouldReturnUserId_WhenUserLoggedIn() {
        // 模拟已登录用户
        String username = "testUser";
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        try {
            // 执行测试
            String userId = ServletUtils.getLoginUserId();

            // 验证结果
            assertEquals(username, userId);
        } finally {
            // 清理测试环境
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void getLoginUserId_ShouldReturnAnonymous_WhenUserNotLoggedIn() {
        // 确保没有认证信息
        SecurityContextHolder.clearContext();

        // 执行测试
        String userId = ServletUtils.getLoginUserId();

        // 验证结果
        assertEquals("anonymous", userId);
    }

    @Test
    void getLoginUserId_ShouldReturnAnonymous_WhenAnonymousUser() {
        // 模拟匿名用户
        Authentication auth = new AnonymousAuthenticationToken(
            "key", 
            "anonymousUser",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        try {
            // 执行测试
            String userId = ServletUtils.getLoginUserId();

            // 验证结果
            assertEquals("anonymous", userId);
        } finally {
            // 清理测试环境
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void getRequestParams_ShouldReturnJsonString() {
        // 准备测试数据
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("name", "张三");
        request.addParameter("age", "25");
        request.addParameter("email", "zhangsan@example.com");

        // 执行测试
        String result = ServletUtils.getRequestParams(request);

        // 验证结果
        assertTrue(result.contains("\"name\":\"张三\""));
        assertTrue(result.contains("\"age\":\"25\""));
        assertTrue(result.contains("\"email\":\"zhangsan@example.com\""));
    }

    @Test
    void getRequestParams_ShouldReturnEmptyJson_WhenNoParameters() {
        // 准备测试数据
        MockHttpServletRequest request = new MockHttpServletRequest();

        // 执行测试
        String result = ServletUtils.getRequestParams(request);

        // 验证结果
        assertEquals("{}", result);
    }
} 