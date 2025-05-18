package com.lawfirm.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.api.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应处理器
 * 确保所有API响应使用统一的格式
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    public GlobalResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 获取类名和包名进行检查
        String className = returnType.getContainingClass().getName();
        String packageName = returnType.getContainingClass().getPackageName();
        
        log.debug("检查类是否需要响应包装: {} (package: {})", className, packageName);
        
        // API文档请求强制返回false(不进行任何包装)
        // 检查当前上下文中的请求对象
        try {
            Object requestObj = org.springframework.web.context.request.RequestContextHolder
                .getRequestAttributes()
                .getAttribute("org.springframework.web.util.ServletRequestAttributes.REQUEST", 0);
            
            if (requestObj instanceof jakarta.servlet.http.HttpServletRequest) {
                jakarta.servlet.http.HttpServletRequest request = (jakarta.servlet.http.HttpServletRequest) requestObj;
                String path = request.getRequestURI();
                String apiDocHeader = request.getHeader("X-API-DOC-REQUEST");
                
                // 通过路径或特殊标记识别API文档请求
                if ("true".equals(apiDocHeader) || isApiDocPath(path)) {
                    log.info("API文档请求，不进行响应包装: {}", path);
                    return false;
                }
            }
        } catch (Exception e) {
            log.warn("检查请求对象异常，可能在无请求上下文环境中调用", e);
        }
        
        // 明确排除API文档相关的转换器
        boolean isApiDocConverter = false;
        if (converterType != null) {
            String converterName = converterType.getName();
            isApiDocConverter = converterName.contains("OpenAPI") || 
                               converterName.contains("Swagger") || 
                               converterName.contains("ApiDoc") ||
                               converterName.contains("springdoc") ||
                               converterName.contains("SpringDoc") ||
                               (converterName.contains("Application") && converterName.contains("Json"));
            
            if (isApiDocConverter) {
                log.debug("API文档转换器，不进行响应包装: {}", converterName);
                return false;
            }
        }
        
        // 排除已经是CommonResult类型的响应和特定控制器
        boolean isCommonResult = CommonResult.class.isAssignableFrom(returnType.getParameterType());
        boolean isResponseEntityType = ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
        boolean isErrorController = className.contains("ErrorController");
        boolean isHealthController = className.contains("HealthCheckController");
        boolean isRestExceptionHandler = className.contains("RestExceptionHandler");
        boolean isOpenApiClass = className.contains("OpenAPI") || 
                                className.contains("ApiDoc") || 
                                className.contains("Swagger") ||
                                className.contains("springdoc") ||
                                className.contains("SpringDoc");
        
        // API文档相关包排除 - 增强包名判断逻辑
        boolean isApiDocPackage = packageName.startsWith("org.springdoc") || 
                                 packageName.startsWith("io.swagger") || 
                                 packageName.startsWith("springfox") ||
                                 packageName.startsWith("com.github.xiaoymin.knife4j") ||
                                 packageName.contains("swagger") ||
                                 packageName.contains("openapi") ||
                                 packageName.contains("springdoc") ||
                                 packageName.contains("knife4j");
        
        // 不处理以下情况:
        // 1. 已经是标准CommonResult格式的响应 
        // 2. RestExceptionHandler处理的响应
        // 3. 错误控制器和健康检查控制器的响应
        // 4. API文档相关的响应或类
        return !isCommonResult && !isResponseEntityType && !isErrorController && 
               !isHealthController && !isRestExceptionHandler && !isApiDocPackage && !isOpenApiClass;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        // 修复变量重复定义问题
        String path = request.getURI().getPath();
        String effectiveContextPath = contextPath.equals("/") ? "" : contextPath;
        
        // 构建完整路径（兼容contextPath配置）
        String fullPath = path.startsWith(effectiveContextPath) ? 
                         path.substring(effectiveContextPath.length()) : 
                         path;

        // 调试日志（需在application-dev.yml开启TRACE级别）
        log.trace("处理响应: {} | ContextPath: {}", fullPath, effectiveContextPath);

        // API文档路径检测 - 增强规则确保不会包装API文档响应
        if (isApiDocPath(fullPath)) {
            log.debug("API文档相关路径，不进行响应包装: {}", fullPath);
            // 确保API文档响应设置正确的Content-Type
            if (fullPath.contains("/v3/api-docs") || fullPath.contains("api-docs")) {
                log.info("设置API文档响应Content-Type为APPLICATION_JSON");
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            }
            return body;
        }
        
        // 额外的API文档检测 - 防止遗漏
        if (fullPath.contains("api-docs") || 
            fullPath.contains("swagger") || 
            fullPath.contains("knife4j") || 
            fullPath.contains("openapi") ||
            fullPath.contains("springdoc") ||
            fullPath.contains("v3/api") ||
            fullPath.contains("doc.html")) {
            log.debug("检测到额外的API文档路径: {}", fullPath);
            log.info("设置额外API文档路径响应Content-Type为APPLICATION_JSON");
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return body;
        }
        
        // 检查请求头中是否包含API文档相关标识
        String userAgent = request.getHeaders().getFirst("User-Agent");
        String accept = request.getHeaders().getFirst("Accept");
        
        if ((userAgent != null && (userAgent.contains("swagger") || userAgent.contains("openapi"))) ||
            (accept != null && accept.contains("application/json") && 
             (fullPath.contains("/v3/") || fullPath.contains("/api/")))) {
            log.debug("检测到API文档请求头标识，不进行响应包装");
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return body;
        }
        
        // 确保 contextPath 在使用前被正确初始化 (如果应用启动时 contextPath 可能还未注入)
        // 但通常Spring会在调用此方法前完成所有依赖注入
        String currentContextPath = this.contextPath != null ? this.contextPath : "/";
        if (currentContextPath.equals("/")) { // 避免出现 "//api/v3/api-docs" 这样的路径
            currentContextPath = "";
        }
        
        // 静态资源和Actuator路径不包装
        if (path.startsWith("/webjars") || 
            path.startsWith("/static") || 
            path.startsWith("/assets") ||
            path.startsWith("/actuator")) {
            return body;
        }
        
        // HTML类型响应不包装
        if (MediaType.TEXT_HTML.equals(selectedContentType) ||
            (selectedContentType != null && selectedContentType.toString().contains("text/html"))) {
            log.debug("HTML类型请求，不包装响应: {}", request.getURI().getPath());
            return body;
        }
        
        // 如果已经是CommonResult或ResponseEntity<CommonResult>类型，则不再包装
        if (body instanceof CommonResult || 
            (body instanceof ResponseEntity && ((ResponseEntity<?>)body).getBody() instanceof CommonResult)) {
            return body;
        }
        
        // 字符串类型特殊处理
        if (body instanceof String) {
            // 将字符串类型转换为CommonResult后序列化为JSON字符串
            CommonResult<String> result = CommonResult.success((String) body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(result);
        }
        
        // 如果body为null，返回空成功结果
        if (body == null) {
            return CommonResult.success();
        }
        
        // 其他情况，包装为统一响应格式
        return CommonResult.success(body);
    }
    
    /**
     * 判断是否是API文档相关路径
     */
    private boolean isApiDocPath(String path) {
        if (path == null) return false;
        
        // 以下是明确的API文档路径前缀
        String[] apiDocPrefixes = {
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger",
            "/api-docs",
            "/doc.html",
            "/docs",
            "/webjars/swagger",
            "/knife4j",
            "/openapi"
        };
        
        // 精确匹配路径前缀，避免误判
        for (String prefix : apiDocPrefixes) {
            if (path.startsWith(prefix)) {
                log.debug("检测到API文档路径: {} (匹配前缀: {})", path, prefix);
                return true;
            }
        }
        
        // 额外包含关系检查，用于一些特殊URL模式
        if ((path.contains("/api-docs") && !path.contains("/api-docs-examples")) ||
            (path.contains("/swagger") && !path.contains("/swagger-example")) ||
            (path.contains("/knife4j") && !path.contains("/knife4j-demo")) ||
            (path.contains("/webjars") && path.contains("swagger"))) {
            log.debug("检测到API文档相关路径: {} (包含关系)", path);
            return true;
        }
        
        return false;
    }
} 