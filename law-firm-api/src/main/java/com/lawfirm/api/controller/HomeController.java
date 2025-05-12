package com.lawfirm.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页控制器
 * 提供欢迎页和API文档入口
 */
@Controller
public class HomeController {

    /**
     * 首页欢迎页面
     * 
     * @return 欢迎信息
     */
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<html><body style='font-family: Arial, sans-serif; text-align: center; padding: 50px;'>"
            + "<h1>欢迎使用律师事务所管理系统</h1>"
            + "<p>系统已成功启动，以下是可用的接口文档链接：</p>"
            + "<div style='margin: 20px; padding: 10px;'>"
            + "<a href='/swagger-ui.html' style='text-decoration: none; background-color: #4CAF50; color: white; padding: 10px 20px; margin: 10px; border-radius: 4px;'>Swagger UI</a>"
            + "<a href='/v3/api-docs' style='text-decoration: none; background-color: #2196F3; color: white; padding: 10px 20px; margin: 10px; border-radius: 4px;'>OpenAPI 文档</a>"
            + "</div>"
            + "</body></html>";
    }
    
    /**
     * 重定向到Swagger UI
     * 
     * @return 重定向路径
     */
    @GetMapping("/api")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
} 