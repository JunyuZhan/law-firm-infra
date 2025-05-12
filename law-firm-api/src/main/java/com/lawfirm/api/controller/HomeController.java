package com.lawfirm.api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 首页控制器
 * 提供欢迎页和API文档入口，同时处理错误页面
 */
@Controller
public class HomeController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 首页欢迎页面
     * 
     * @return 欢迎信息
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String htmlContent = "<html><head>" 
            + "<meta charset=\"UTF-8\">"
            + "<title>律师事务所管理系统</title>"
            + "</head><body style='font-family: Arial, sans-serif; text-align: center; padding: 50px;'>"
            + "<h1>欢迎使用律师事务所管理系统</h1>"
            + "<p>系统已成功启动，以下是可用的接口文档链接：</p>"
            + "<div style='margin: 20px; padding: 10px;'>"
            + "<a href='/swagger-ui.html' style='text-decoration: none; background-color: #4CAF50; color: white; padding: 10px 20px; margin: 10px; border-radius: 4px;'>Swagger UI</a>"
            + "<a href='/v3/api-docs' style='text-decoration: none; background-color: #2196F3; color: white; padding: 10px 20px; margin: 10px; border-radius: 4px;'>OpenAPI 文档</a>"
            + "</div>"
            + "</body></html>";
            
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(htmlContent);
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
    
    /**
     * 处理错误页面 - 修正内容类型和响应处理
     * 
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 错误信息页面
     */
    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            statusCode = response.getStatus();
        }
        
        String errorMessage = "未知错误";
        if (statusCode == 400) {
            errorMessage = "无效的请求 - 请检查您的输入";
        } else if (statusCode == 401) {
            errorMessage = "未授权访问 - 请先登录";
        } else if (statusCode == 403) {
            errorMessage = "禁止访问此资源 - 权限不足";
        } else if (statusCode == 404) {
            errorMessage = "请求的资源不存在 - 请检查URL";
        } else if (statusCode == 500) {
            errorMessage = "服务器内部错误 - 请联系管理员";
        }
        
        String htmlContent = "<html><head>"
            + "<meta charset=\"UTF-8\">"
            + "<title>系统错误</title>"
            + "</head><body style='font-family: Arial, sans-serif; text-align: center; padding: 50px;'>"
            + "<h1>系统错误</h1>"
            + "<p>很抱歉，处理您的请求时发生错误。</p>"
            + "<p>错误代码: " + statusCode + " - " + errorMessage + "</p>"
            + "<div style='margin: 20px; padding: 10px;'>"
            + "<a href='/' style='text-decoration: none; background-color: #4CAF50; color: white; padding: 10px 20px; margin: 10px; border-radius: 4px;'>返回首页</a>"
            + "</div>"
            + "</body></html>";
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
            
        return ResponseEntity.status(statusCode)
            .headers(headers)
            .body(htmlContent);
    }
}