package com.lawfirm.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 * 提供简单的测试接口
 */
@RestController
public class TestController {

    /**
     * 简单测试接口
     */
    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "测试成功");
        result.put("time", System.currentTimeMillis());
        return result;
    }
    
    /**
     * 提供一个简单的首页
     */
    @GetMapping({"/", "/index"})
    public Map<String, Object> index() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "欢迎访问律师事务所管理系统");
        result.put("system", "Law Firm Management System");
        result.put("version", "1.0.0");
        return result;
    }
} 