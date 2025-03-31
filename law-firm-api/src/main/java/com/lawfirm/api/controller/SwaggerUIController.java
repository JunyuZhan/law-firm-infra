package com.lawfirm.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * Swagger UI控制器
 * <p>
 * 提供API文档入口
 * </p>
 */
@Slf4j
@Controller("swaggerUIController")
public class SwaggerUIController {
    
    /**
     * API文档入口
     */
    @GetMapping({"/api-docs", "/docs", "/api/docs"})
    public String docs() {
        log.info("重定向到Swagger UI...");
        return "redirect:/swagger-ui.html";
    }
} 