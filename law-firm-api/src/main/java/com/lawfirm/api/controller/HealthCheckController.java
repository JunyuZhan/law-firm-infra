package com.lawfirm.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的健康检查控制器
 */
@RestController
public class HealthCheckController {

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "服务运行正常");
        return response;
    }
    
    /**
     * 版本信息接口
     */
    @GetMapping("/version")
    public Map<String, Object> version() {
        Map<String, Object> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("name", "律师事务所管理系统");
        return response;
    }
} 