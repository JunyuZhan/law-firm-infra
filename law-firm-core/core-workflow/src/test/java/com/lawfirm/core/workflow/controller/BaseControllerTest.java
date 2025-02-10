package com.lawfirm.core.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Controller测试基类
 */
public abstract class BaseControllerTest {
    
    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    /**
     * 执行GET请求
     */
    protected ResultActions performGet(String urlTemplate, Object... uriVars) throws Exception {
        return mockMvc.perform(get(urlTemplate, uriVars));
    }
    
    /**
     * 执行POST请求
     */
    protected ResultActions performPost(String urlTemplate, Object request, Object... uriVars) throws Exception {
        return mockMvc.perform(post(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
    
    /**
     * 执行PUT请求
     */
    protected ResultActions performPut(String urlTemplate, Object request, Object... uriVars) throws Exception {
        return mockMvc.perform(put(urlTemplate, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
    
    /**
     * 执行DELETE请求
     */
    protected ResultActions performDelete(String urlTemplate, Object... uriVars) throws Exception {
        return mockMvc.perform(delete(urlTemplate, uriVars));
    }
    
    /**
     * 执行文件上传请求
     */
    protected ResultActions performMultipart(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
} 