package com.lawfirm.common.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试工具类
 */
public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 执行GET请求
     */
    public static ResultActions performGet(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    /**
     * 执行POST请求
     */
    public static ResultActions performPost(MockMvc mockMvc, String url, Object body) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    /**
     * 执行PUT请求
     */
    public static ResultActions performPut(MockMvc mockMvc, String url, Object body) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    /**
     * 执行DELETE请求
     */
    public static ResultActions performDelete(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    /**
     * 构建带认证的请求
     */
    public static MockHttpServletRequestBuilder withAuth(MockHttpServletRequestBuilder request, String token) {
        return request.header("Authorization", "Bearer " + token);
    }

    /**
     * 将对象转换为JSON字符串
     */
    public static String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 从JSON字符串转换为对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }
} 