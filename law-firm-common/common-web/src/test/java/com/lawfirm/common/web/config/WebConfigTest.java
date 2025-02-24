package com.lawfirm.common.web.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WebConfig.class)
@Import(WebConfig.class)
class WebConfigTest {

    @Autowired
    private WebConfig webConfig;

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
        verify(registration).allowedOriginPatterns("*");
        verify(registration).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        verify(registration).allowedHeaders("*");
        verify(registration).allowCredentials(true);
        verify(registration).maxAge(3600);
    }

    @Test
    void testMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        webConfig.configureMessageConverters(converters);
        
        assertFalse(converters.isEmpty());
        assertTrue(converters.get(0) instanceof MappingJackson2HttpMessageConverter);
        
        MappingJackson2HttpMessageConverter converter = 
            (MappingJackson2HttpMessageConverter) converters.get(0);
        assertTrue(converter.getSupportedMediaTypes()
            .contains(MediaType.APPLICATION_JSON));
    }

    @Test
    void testLongToStringConversion() throws JsonProcessingException {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        webConfig.configureMessageConverters(converters);
        
        MappingJackson2HttpMessageConverter converter = 
            (MappingJackson2HttpMessageConverter) converters.get(0);
        
        // 测试Long类型转换
        String json = converter.getObjectMapper()
            .writeValueAsString(123456789L);
        assertEquals("\"123456789\"", json);
    }
} 