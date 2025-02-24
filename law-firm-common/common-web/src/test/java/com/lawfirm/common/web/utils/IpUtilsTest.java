package com.lawfirm.common.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IpUtilsTest {

    @Test
    void getIpAddr_ShouldReturnUnknown_WhenRequestIsNull() {
        assertEquals("unknown", IpUtils.getIpAddr(null));
    }

    @Test
    void getIpAddr_ShouldReturnFirstNonUnknownIp_FromHeaders() {
        // 准备测试数据
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("unknown");
        when(request.getHeader("Proxy-Client-IP")).thenReturn("192.168.1.1");
        when(request.getHeader("WL-Proxy-Client-IP")).thenReturn("192.168.1.2");

        // 验证结果
        assertEquals("192.168.1.1", IpUtils.getIpAddr(request));
    }

    @Test
    void getIpAddr_ShouldReturnRemoteAddr_WhenHeadersAreUnknown() {
        // 准备测试数据
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn("unknown");
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");

        // 验证结果
        assertEquals("192.168.1.1", IpUtils.getIpAddr(request));
    }

    @Test
    void getIpAddr_ShouldReturnFirstIp_WhenMultipleIpsInHeader() {
        // 准备测试数据
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("192.168.1.1,192.168.1.2");

        // 验证结果
        assertEquals("192.168.1.1", IpUtils.getIpAddr(request));
    }

    @Test
    void getLocalIP_ShouldReturnLocalHostAddress() {
        // 执行测试
        String localIP = IpUtils.getLocalIP();

        // 验证结果
        assertNotNull(localIP);
        assertTrue(IpUtils.isValidIP(localIP));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "192.168.1.1",
        "10.0.0.1",
        "172.16.0.1",
        "8.8.8.8"
    })
    void isValidIP_ShouldReturnTrue_ForValidIPs(String ip) {
        assertTrue(IpUtils.isValidIP(ip));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "192.168.1",
        "192.168.1.256",
        "192.168.1.1.1",
        "abc.def.ghi.jkl"
    })
    void isValidIP_ShouldReturnFalse_ForInvalidIPs(String ip) {
        assertFalse(IpUtils.isValidIP(ip));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "10.0.0.1",
        "172.16.0.1",
        "172.31.255.255",
        "192.168.1.1"
    })
    void isInternalIP_ShouldReturnTrue_ForInternalIPs(String ip) {
        assertTrue(IpUtils.isInternalIP(ip));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "8.8.8.8",
        "114.114.114.114",
        "172.32.0.1",
        "192.167.1.1"
    })
    void isInternalIP_ShouldReturnFalse_ForPublicIPs(String ip) {
        assertFalse(IpUtils.isInternalIP(ip));
    }

    @Test
    void isPublicIP_ShouldReturnOppositeOfIsInternalIP() {
        String internalIP = "192.168.1.1";
        String publicIP = "8.8.8.8";

        assertTrue(IpUtils.isPublicIP(publicIP));
        assertFalse(IpUtils.isPublicIP(internalIP));
    }

    @Test
    void getRealAddressByIP_ShouldReturnIPAddress() {
        String ip = "192.168.1.1";
        assertEquals(ip, IpUtils.getRealAddressByIP(ip));
    }

    @Test
    void getIpAddr_ShouldHandleLocalhost() {
        // 准备测试数据
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn("unknown");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // 执行测试
        String result = IpUtils.getIpAddr(request);

        // 验证结果
        assertNotNull(result);
        assertTrue(IpUtils.isValidIP(result));
    }
} 