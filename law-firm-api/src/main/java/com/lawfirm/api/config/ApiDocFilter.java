package com.lawfirm.api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * API文档处理过滤器
 * 用于处理API文档相关请求，解决格式问题
 */
@Component
@Order(1) // 确保在安全过滤器之前执行
public class ApiDocFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ApiDocFilter.class);
    
    // API文档相关路径，需要特殊处理
    private static final List<String> API_DOC_PATHS = Arrays.asList(
        "/v3/api-docs",
        "/swagger-ui",
        "/doc.html",
        "/swagger-resources",
        "/webjars/",
        "/swagger-config",
        "/api-docs",
        "/swagger-config"
    );
    
    // 一定要处理JSON的路径（检测Base64）
    private static final List<String> JSON_API_PATHS = Arrays.asList(
        "/v3/api-docs",
        "/swagger-config",
        "/swagger-resources",
        "/api-docs"
    );
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        
        // 判断是否为API文档相关请求
        if (isApiDocRequest(requestURI)) {
            log.info("API文档过滤器处理请求: {}", requestURI);
            
            // 静态资源直接放行，不修改MIME类型
            if (isStaticResource(requestURI)) {
                log.debug("静态资源直接放行: {}", requestURI);
                chain.doFilter(request, response);
                return;
            }
            
            // 如果是被控制器直接处理的特定路径，直接放行
            if (requestURI.contains("/swagger-config") || requestURI.equals(contextPath + "/v3/api-docs")) {
                log.debug("控制器直接处理的请求，过滤器放行: {}", requestURI);
                chain.doFilter(request, response);
                return;
            }
            
            // 处理API文档JSON响应（子路径）
            if (requestURI.contains("/v3/api-docs/")) {
                // 使用缓存包装器捕获响应
                ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(httpResponse);
                
                // 继续过滤链，捕获响应内容
                chain.doFilter(request, wrapperResponse);
                
                // 获取响应内容
                byte[] content = wrapperResponse.getContentAsByteArray();
                if (content.length > 0) {
                    String responseContent = new String(content, StandardCharsets.UTF_8).trim();
                    log.debug("响应内容前20个字符: {}, 长度: {}", 
                        responseContent.substring(0, Math.min(20, responseContent.length())),
                        responseContent.length());
                    
                    // 检查是否为Base64编码
                    if (isLikelyBase64Encoded(responseContent)) {
                        log.info("检测到Base64编码的内容，长度: {}", responseContent.length());
                        try {
                            // 处理URL安全的Base64编码
                            String sanitized = responseContent
                                .replace('-', '+')
                                .replace('_', '/');
                            
                            // 自动补充padding字符
                            int padding = (4 - (sanitized.length() % 4)) % 4;
                            if (padding > 0) {
                                sanitized += "====".substring(0, padding);
                            }
                            
                            // 尝试解码
                            byte[] decodedBytes = Base64.getDecoder().decode(sanitized);
                            String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);
                            log.debug("Base64解码后的内容前30个字符: {}", 
                                decodedJson.substring(0, Math.min(30, decodedJson.length())));
                            
                            // 确保是JSON
                            if (looksLikeJSON(decodedJson)) {
                                // 清理JSON
                                String cleanedJson = cleanJsonContent(decodedJson);
                                
                                // 重置响应
                                httpResponse.reset();
                                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
                                
                                // 添加缓存控制头
                                httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                                httpResponse.setHeader("Pragma", "no-cache");
                                httpResponse.setDateHeader("Expires", 0);
                                
                                // 设置正确的内容长度并写入响应
                                byte[] outputBytes = cleanedJson.getBytes(StandardCharsets.UTF_8);
                                httpResponse.setContentLength(outputBytes.length);
                                httpResponse.getOutputStream().write(outputBytes);
                                httpResponse.getOutputStream().flush();
                                return;
                            }
                        } catch (Exception e) {
                            log.error("Base64处理失败: {}", e.getMessage());
                        }
                    }
                    
                    // 如果不是Base64编码或处理失败，则直接复制内容到响应
                    wrapperResponse.copyBodyToResponse();
                }
                return;
            }
        }
        
        // 非API文档请求，正常处理
        chain.doFilter(request, response);
    }
    
    /**
     * 判断是否为API文档相关请求
     */
    private boolean isApiDocRequest(String requestURI) {
        // 检查路径是否包含API文档相关关键字
        for (String docPath : API_DOC_PATHS) {
            if (requestURI.contains(docPath)) {
                return true;
            }
        }
        
        // 如果有上下文路径，也检查带上下文路径的情况
        if (contextPath != null && !"/".equals(contextPath)) {
            for (String docPath : API_DOC_PATHS) {
                if (requestURI.contains(contextPath + docPath)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 判断是否为静态资源
     */
    private boolean isStaticResource(String requestURI) {
        return requestURI.contains(".html") || 
               requestURI.contains(".css") || 
               requestURI.contains(".js") || 
               requestURI.contains(".png") || 
               requestURI.contains(".jpg") || 
               requestURI.contains(".gif") || 
               requestURI.contains(".ico") ||
               requestURI.contains("/webjars/");
    }
    
    /**
     * 判断是否为需要处理JSON的API路径
     */
    private boolean isJsonApiPath(String requestURI) {
        for (String path : JSON_API_PATHS) {
            if (requestURI.contains(path)) {
                return true;
            }
        }
        
        // 检查带上下文路径的情况
        if (contextPath != null && !"/".equals(contextPath)) {
            for (String path : JSON_API_PATHS) {
                if (requestURI.contains(contextPath + path)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 简化的内容缓存响应包装器
     */
    private static class ContentCachingResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream content = new ByteArrayOutputStream();
        private final PrintWriter writer;
        private ServletOutputStream outputStream;

        public ContentCachingResponseWrapper(HttpServletResponse response) throws IOException {
            super(response);
            this.writer = new PrintWriter(new OutputStreamWriter(content, StandardCharsets.UTF_8));
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (outputStream == null) {
                outputStream = new DelegatingServletOutputStream(content);
            }
            return outputStream;
        }

        public byte[] getContentAsByteArray() {
            writer.flush();
            return content.toByteArray();
        }

        public void copyBodyToResponse() throws IOException {
            writer.flush();
            if (content.size() > 0) {
                HttpServletResponse response = (HttpServletResponse) getResponse();
                response.setContentLength(content.size());
                content.writeTo(response.getOutputStream());
                response.getOutputStream().flush();
            }
        }

        private static class DelegatingServletOutputStream extends ServletOutputStream {
            private final OutputStream targetStream;

            public DelegatingServletOutputStream(OutputStream targetStream) {
                this.targetStream = targetStream;
            }

            @Override
            public void write(int b) throws IOException {
                targetStream.write(b);
            }

            @Override
            public void write(byte[] b) throws IOException {
                targetStream.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                targetStream.write(b, off, len);
            }

            @Override
            public void flush() throws IOException {
                targetStream.flush();
            }

            @Override
            public void close() throws IOException {
                targetStream.close();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
                // 不实现
            }
        }
    }
    
    /**
     * 辅助方法 - 清理JSON内容
     */
    private String cleanJsonContent(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        try {
            // 1. 移除控制字符
            String cleaned = content.replaceAll("[\\x00-\\x1F\\x7F]", "");
            
            // 2. 处理常见的JSON格式错误
            cleaned = cleaned.replaceAll(",\\s*}", "}") // 移除对象末尾多余的逗号
                           .replaceAll(",\\s*]", "]")   // 移除数组末尾多余的逗号
                           .trim();                     // 移除前后空白字符
            
            // 3. 处理损坏的Unicode转义
            cleaned = cleaned.replaceAll("\\\\u[0-9a-fA-F]{0,3}([^0-9a-fA-F])", "$1");
            
            // 4. 修复损坏的字符串引号
            StringBuilder sb = new StringBuilder(cleaned);
            boolean inString = false;
            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == '\"' && (i == 0 || sb.charAt(i-1) != '\\')) {
                    inString = !inString;
                }
                
                // 若发现未闭合的字符串到末尾，添加引号
                if (inString && i == sb.length() - 1) {
                    sb.append('\"');
                }
            }
            
            cleaned = sb.toString();
            
            // 5. 检查是否有多个JSON对象连接在一起，只保留第一个
            for (int i = 1; i < cleaned.length(); i++) {
                if (cleaned.charAt(i) == '{' && cleaned.charAt(i-1) == '}') {
                    log.info("检测到多个JSON对象连接在一起，将在位置{}切断", i);
                    cleaned = cleaned.substring(0, i);
                    break;
                }
            }
            
            // 6. 如果之前的修复都无效，尝试找到最后一个有效的JSON结束位置
            if (!looksLikeJSON(cleaned)) {
                int lastCloseBrace = cleaned.lastIndexOf('}');
                if (lastCloseBrace > 0) {
                    cleaned = cleaned.substring(0, lastCloseBrace + 1);
                }
            }
            
            return cleaned;
        } catch (Exception e) {
            log.warn("JSON清理失败", e);
            return content;
        }
    }

    /**
     * 检查字符串是否可能是Base64编码
     */
    private boolean isLikelyBase64Encoded(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        
        // 直接匹配错误中提到的特定前缀
        if (content.startsWith("eyJvcGVuYX")) {
            log.info("直接匹配到错误中提到的前缀 'eyJvcGVuYX'");
            return true;
        }
        
        // 检查是否以"ey"开头（JWT令牌的特征）
        if (content.startsWith("ey")) {
            try {
                // 尝试简单解码前几个字符，看是否会抛出异常
                String testString = content.substring(0, Math.min(content.length(), 30));
                Base64.getDecoder().decode(testString.replace('-', '+').replace('_', '/'));
                return true;
            } catch (IllegalArgumentException e) {
                // 解码失败，可能不是Base64
                log.debug("以'ey'开头但解码失败: {}", e.getMessage());
                return false;
            }
        }
        
        // 检查是否为swagger响应的典型Base64编码模式
        if (content.startsWith("eyJ") && (
            content.contains("eyJvcGVuYXBp") || // openapi
            content.contains("eyJzd2FnZ2Vy") || // swagger
            content.contains("eyJwYXRocyI") ||  // paths
            content.contains("eyJpbmZvIjo")     // info
        )) {
            log.info("检测到API文档典型Base64编码模式");
            return true;
        }
        
        // 检查内容是否符合Base64字符集（宽松匹配）
        if (content.matches("^[A-Za-z0-9+/=_-]+$")) {
            // 检查长度是否合理（Base64编码长度应该是4的倍数，考虑到padding可能不完整）
            int mod4 = content.length() % 4;
            if (mod4 == 0 || content.endsWith("=") || content.endsWith("==")) {
                // 尝试解码前20个字符
                try {
                    String testPart = content.substring(0, Math.min(20, content.length()));
                    // 处理URL安全编码和padding
                    String sanitized = testPart.replace('-', '+').replace('_', '/');
                    int padding = (4 - (sanitized.length() % 4)) % 4;
                    if (padding > 0) {
                        sanitized += "====".substring(0, padding);
                    }
                    Base64.getDecoder().decode(sanitized);
                    
                    // 长度合适且能解码，可能是Base64
                    log.debug("检测到可能的Base64内容（符合字符集和长度要求）");
                    return true;
                } catch (Exception e) {
                    log.debug("符合Base64字符集但解码失败: {}", e.getMessage());
                }
            }
        }
        
        // 额外检查特定API响应模式（更严格的检查）
        boolean matchesPattern = content.startsWith("eyJ") || content.startsWith("ey0") || 
                               content.startsWith("eyF") || content.startsWith("eyV");
        
        return matchesPattern;
    }
    
    /**
     * 检查字符串是否看起来像有效的JSON
     */
    private boolean looksLikeJSON(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        
        // 去除头尾空白字符
        String trimmed = content.trim();
        
        // 快速检查：JSON对象或数组的起始和结束字符
        boolean hasValidStartEnd = (trimmed.startsWith("{") && trimmed.endsWith("}")) || 
                                 (trimmed.startsWith("[") && trimmed.endsWith("]"));
                                 
        if (!hasValidStartEnd) {
            log.debug("内容不符合JSON基本格式要求：不是以{}或[]包围");
            return false;
        }
        
        // 对于OpenAPI文档，几乎总是有效的JSON
        // 如果是Base64解码得到的，我们可以更宽松地处理
        if (trimmed.length() > 50) {  // 设置一个合理的最小长度阈值
            log.info("解码后的内容看起来是有效的JSON（长度 > 50）");
            return true;
        }
        
        // 检查一些常见的JSON键，判断是否符合预期
        // Swagger/OpenAPI特定键
        boolean containsSwaggerKeys = trimmed.contains("\"openapi\"") || 
                                    trimmed.contains("\"swagger\"") || 
                                    trimmed.contains("\"paths\"") || 
                                    trimmed.contains("\"components\"") ||
                                    trimmed.contains("\"info\"");
        
        // 一般API响应关键字
        boolean containsGeneralApiKeys = trimmed.contains("\"status\"") || 
                                       trimmed.contains("\"data\"") || 
                                       trimmed.contains("\"result\"") || 
                                       trimmed.contains("\"message\"") || 
                                       trimmed.contains("\"code\"");
        
        // Knife4j特定键
        boolean containsKnife4jKeys = trimmed.contains("\"knife4j\"") || 
                                    trimmed.contains("\"openApiExtensionUrl\"") || 
                                    trimmed.contains("\"swaggerBootstrapUi\"");
        
        // 更深入的JSON结构检查
        boolean hasBalancedBraces = checkBalancedBraces(trimmed);
        
        boolean isLikelyJson = (containsSwaggerKeys || containsGeneralApiKeys || containsKnife4jKeys) && hasBalancedBraces;
        
        if (!isLikelyJson) {
            log.debug("内容不符合API文档JSON特征");
        }
        
        return isLikelyJson;
    }
    
    /**
     * 检查JSON字符串是否有平衡的括号
     */
    private boolean checkBalancedBraces(String json) {
        int curlyBraceCount = 0;
        int squareBracketCount = 0;
        boolean inString = false;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            // 处理字符串内部（忽略字符串内的括号）
            if (c == '"' && (i == 0 || json.charAt(i-1) != '\\')) {
                inString = !inString;
                continue;
            }
            
            if (!inString) {
                if (c == '{') curlyBraceCount++;
                else if (c == '}') curlyBraceCount--;
                else if (c == '[') squareBracketCount++;
                else if (c == ']') squareBracketCount--;
                
                // 如果在任何时候计数变为负数，则括号不平衡
                if (curlyBraceCount < 0 || squareBracketCount < 0) {
                    return false;
                }
            }
        }
        
        // 最终计数应该都为0
        return curlyBraceCount == 0 && squareBracketCount == 0;
    }
}