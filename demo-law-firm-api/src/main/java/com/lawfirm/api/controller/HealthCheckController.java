package com.lawfirm.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的健康检查控制器
 */
@RestController
@RequestMapping("/api")
@Tag(name = "系统健康检查", description = "提供系统健康状态和版本信息的API")
public class HealthCheckController {

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    @Operation(summary = "健康状态检查", description = "获取当前系统运行状态")
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
    @Operation(summary = "版本信息", description = "获取系统当前版本信息")
    public Map<String, Object> version() {
        Map<String, Object> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("name", "律师事务所管理系统");
        response.put("buildTime", "2024-05-12");
        return response;
    }
} 