package com.lawfirm.api.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.core.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 健康检查控制器
 * 提供系统状态检查功能和统一响应测试
 */
@Slf4j
@RestController
public class HealthCheckController {

    /**
     * 健康检查端点
     * 返回系统基本状态
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        log.info("执行健康检查");
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "系统运行正常");
        result.put("timestamp", System.currentTimeMillis());
        result.put("application", "律师事务所管理系统");
        
        return result;
    }
    
    /**
     * 测试统一响应格式
     * 
     * @return 直接返回Map对象
     */
    @GetMapping("/api/test/response")
    public Map<String, Object> testResponse() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "测试数据");
        data.put("time", LocalDateTime.now().toString());
        return data;
    }
    
    /**
     * 测试返回CommonResult
     * 
     * @return 直接返回CommonResult对象
     */
    @GetMapping("/api/test/common-result")
    public CommonResult<Map<String, Object>> testCommonResult() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "CommonResult测试数据");
        data.put("time", LocalDateTime.now().toString());
        return CommonResult.success(data);
    }
    
    /**
     * 测试业务异常处理
     * 
     * @param code 错误码
     * @return 不会返回，会抛出异常
     */
    @GetMapping("/api/test/business-error/{code}")
    public Map<String, Object> testBusinessError(@PathVariable int code) {
        throw new BusinessException(code, "测试业务异常，错误码: " + code);
    }
    
    /**
     * 测试系统异常处理
     * 
     * @return 不会返回，会抛出异常
     */
    @GetMapping("/api/test/system-error")
    public Map<String, Object> testSystemError() {
        throw new RuntimeException("测试系统异常");
    }
} 