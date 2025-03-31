package com.lawfirm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API文档控制器
 * <p>
 * 提供对Knife4j和Swagger文档的访问，确保响应内容正确
 * </p>
 */
@Slf4j
@Controller("ApiDocController")
@RequiredArgsConstructor
@RequestMapping("/knife4j")
public class ApiDocController {

    /**
     * 处理Knife4j文档请求，确保响应设置正确
     */
    @GetMapping("/knife4j-ui-refresh")
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleKnife4jRequest(
            HttpServletRequest request,
            HttpServletResponse response) {
        
        log.info("处理Knife4j刷新请求，确保字符集正确");
        
        // 设置正确的响应头
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        
        // 如果有其他响应头设置，移除可能影响JSON解析的响应头
        for (String headerName : new String[]{"Content-Encoding", "Transfer-Encoding"}) {
            if (response.getHeader(headerName) != null) {
                response.setHeader(headerName, null);
            }
        }
        
        // 返回简单JSON响应
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("success", "true"));
    }
    
    /**
     * 处理API文档请求
     */
    @GetMapping("/v3/api-docs/debug")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleApiDocsDebug(
            @RequestParam(value = "group", required = false) String group,
            HttpServletResponse response) {
        
        log.info("处理API文档调试请求，组: {}", group);
        
        // 设置正确的响应头
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        
        // 返回简单的调试信息
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "success", true,
                        "group", Optional.ofNullable(group).orElse("default"),
                        "status", "ok"
                ));
    }
} 