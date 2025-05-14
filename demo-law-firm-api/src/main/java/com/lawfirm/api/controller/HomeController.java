package com.lawfirm.api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 首页控制器
 * 提供欢迎页和错误处理
 */
@Controller
@Slf4j
public class HomeController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 首页欢迎页面
     * 
     * @return 欢迎信息
     */
    // 注释此方法，让Spring Boot原始欢迎页面生效
    /*
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String htmlContent = "<html><head>" 
            + "<meta charset=\"UTF-8\">"
            + "<title>律师事务所管理系统</title>"
            + "<style>"
            + "body { font-family: 'Microsoft YaHei', Arial, sans-serif; text-align: center; padding: 50px; background-color: #f5f5f5; }"
            + "h1 { color: #333; }"
            + "p { color: #666; }"
            + ".button-container { margin: 20px; }"
            + ".button { text-decoration: none; padding: 10px 20px; margin: 10px; border-radius: 4px; display: inline-block; }"
            + ".green { background-color: #4CAF50; color: white; }"
            + ".blue { background-color: #2196F3; color: white; }"
            + "</style>"
            + "</head><body>"
            + "<h1>欢迎使用律师事务所管理系统</h1>"
            + "<p>系统已成功启动</p>"
            + "</body></html>";
            
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(htmlContent);
    }
    */
    
    /**
     * 处理错误页面
     * 
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 错误信息页面
     */
    @RequestMapping(value = ERROR_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        // 获取错误状态码
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            statusCode = response.getStatus();
        }
        
        // 获取错误信息
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "未知错误";
            
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
        }
        
        // 获取异常类型
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        String exceptionType = throwable != null ? throwable.getClass().getName() : "无异常信息";
        
        // 获取请求URI
        String requestURI = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        if (requestURI == null) {
            requestURI = request.getRequestURI();
        }
        
        // 记录详细的错误信息
        log.error("错误处理: 状态码={}, URI={}, 错误信息={}, 异常类型={}", 
            statusCode, requestURI, errorMessage, exceptionType);
        
        // 生成友好的错误页面
        String htmlContent = "<html><head>"
            + "<meta charset=\"UTF-8\">"
            + "<title>系统错误</title>"
            + "<style>"
            + "body { font-family: 'Microsoft YaHei', Arial, sans-serif; text-align: center; padding: 50px; background-color: #f5f5f5; }"
            + "h1 { color: #d32f2f; }"
            + "p { color: #666; margin: 10px 0; }"
            + ".error-box { background-color: white; border-radius: 8px; padding: 20px; margin: 20px auto; max-width: 600px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }"
            + ".error-code { font-size: 48px; color: #d32f2f; margin: 0; }"
            + ".error-message { font-size: 18px; margin: 10px 0 20px 0; }"
            + ".button { text-decoration: none; background-color: #4CAF50; color: white; padding: 10px 20px; border-radius: 4px; display: inline-block; }"
            + ".details { text-align: left; background-color: #f9f9f9; padding: 10px; border-radius: 4px; margin-top: 20px; }"
            + ".details p { text-align: left; font-family: monospace; margin: 5px 0; }"
            + "</style>"
            + "</head><body>"
            + "<div class='error-box'>"
            + "<h1>系统错误</h1>"
            + "<p class='error-code'>" + statusCode + "</p>"
            + "<p class='error-message'>" + errorMessage + "</p>"
            + "<a href='/' class='button'>返回首页</a>"
            + "<div class='details'>"
            + "<p><strong>请求路径:</strong> " + requestURI + "</p>"
            + "<p><strong>状态码:</strong> " + statusCode + "</p>"
            + "<p><strong>错误类型:</strong> " + exceptionType + "</p>"
            + "</div>"
            + "</div>"
            + "</body></html>";
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        
        HttpStatus responseStatus = HttpStatus.valueOf(statusCode);
        return ResponseEntity.status(responseStatus)
            .headers(headers)
            .body(htmlContent);
    }
} 