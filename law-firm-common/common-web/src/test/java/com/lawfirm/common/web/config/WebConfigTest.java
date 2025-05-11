package com.lawfirm.common.web.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * WebConfig单元测试
 * 不依赖Spring上下文，使用纯单元测试方式
 */
class WebConfigTest {

    private WebConfig webConfig;

    @BeforeEach
    void setUp() {
        // 创建WebConfig实例
        webConfig = new WebConfig();
        
        // 设置allowedOrigins属性
        ReflectionTestUtils.setField(webConfig, "allowedOrigins", new String[]{"*"});
    }

    @Test
    void testCorsConfiguration() {
        // 创建模拟对象
        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration registration = mock(CorsRegistration.class);
        
        // 配置模拟行为
        when(registry.addMapping("/**")).thenReturn(registration);
        when(registration.allowedOriginPatterns(any(String[].class))).thenReturn(registration);
        when(registration.allowedMethods(any(String[].class))).thenReturn(registration);
        when(registration.allowedHeaders(any(String[].class))).thenReturn(registration);
        when(registration.allowCredentials(true)).thenReturn(registration);
        when(registration.maxAge(3600)).thenReturn(registration);
        
        // 执行配置
        webConfig.addCorsMappings(registry);
        
        // 验证配置
        verify(registry).addMapping("/**");
        verify(registration).allowedOriginPatterns(any(String[].class));
        verify(registration).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        verify(registration).allowedHeaders("*");
        verify(registration).allowCredentials(true);
        verify(registration).maxAge(3600);
    }

    @Test
    void testMessageConverters() {
        // 初始化转换器列表
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        
        // 调用测试方法
        webConfig.configureMessageConverters(converters);
        
        // 验证结果
        assertFalse(converters.isEmpty());
        assertTrue(converters.get(0) instanceof MappingJackson2HttpMessageConverter);
        
        MappingJackson2HttpMessageConverter converter = 
            (MappingJackson2HttpMessageConverter) converters.get(0);
        assertTrue(converter.getSupportedMediaTypes()
            .contains(MediaType.APPLICATION_JSON));
            
        // 测试Long类型转换为字符串
        ObjectMapper objectMapper = converter.getObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(123456789L);
            assertEquals("\"123456789\"", json);
        } catch (JsonProcessingException e) {
            fail("JSON转换失败: " + e.getMessage());
        }
    }
    
    @Test
    void testRestTemplateBean() {
        // 测试RestTemplate Bean创建方法
        RestTemplate restTemplate = webConfig.restTemplate();
        assertNotNull(restTemplate);
    }
} 