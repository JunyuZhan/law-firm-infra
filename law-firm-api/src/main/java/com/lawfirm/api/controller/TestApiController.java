package com.lawfirm.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试API控制器
 */
@RestController
@RequestMapping("/api/test")
public class TestApiController {
    
    /**
     * 简单测试接口
     */
    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello from API");
        result.put("time", System.currentTimeMillis());
        return result;
    }
} 