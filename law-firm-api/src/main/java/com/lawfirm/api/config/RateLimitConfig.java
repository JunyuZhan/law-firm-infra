package com.lawfirm.api.config;

import com.lawfirm.common.cache.annotation.RateLimiter;
import lombok.Data;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API限流配置
 * 提供全局限流和IP限流功能
 */
@Configuration("apiRateLimitConfig")
public class RateLimitConfig implements WebMvcConfigurer {

    // 429 Too Many Requests (RFC 6585)
    private static final int SC_TOO_MANY_REQUESTS = 429;

    @Autowired
    private RedissonClient redissonClient;
    
    @Autowired
    private RateLimitProperties rateLimitProperties;
    
    @Autowired
    private Environment environment;
    
    /**
     * 基础排除路径
     */
    private static final List<String> BASE_EXCLUDE_PATHS = Arrays.asList(
        "/static/**", "/actuator/**", "/health/**", "/error"
    );
    
    /**
     * API文档相关路径，仅在开发和测试环境中排除
     */
    private static final List<String> API_DOC_PATHS = Arrays.asList(
        "/doc.html", "/webjars/**", "/v3/api-docs/**", 
        "/swagger-ui/**", "/swagger-resources/**", "/knife4j/**"
    );
    
    @Bean
    @ConfigurationProperties(prefix = "law-firm.api.rate-limit")
    public RateLimitProperties rateLimitProperties() {
        return new RateLimitProperties();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>(BASE_EXCLUDE_PATHS);
        
        // 检查当前环境
        boolean isDevelopmentOrTest = Arrays.asList(environment.getActiveProfiles()).stream()
                .anyMatch(profile -> profile.equals("dev") || profile.equals("test"));
        
        // 仅在开发和测试环境中排除API文档相关路径
        if (isDevelopmentOrTest) {
            excludePaths.addAll(API_DOC_PATHS);
        }
        
        registry.addInterceptor(ipRateLimitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
    
    @Bean
    public HandlerInterceptor ipRateLimitInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 如果未启用IP限流，直接放行
                if (!rateLimitProperties.isIpLimitEnabled()) {
                    return true;
                }
                
                // 获取客户端IP
                String ipAddress = getClientIp(request);
                
                // 获取请求路径
                String uri = request.getRequestURI();
                
                // 检查是否是需要限流的路径
                boolean needLimit = false;
                for (String pattern : rateLimitProperties.getIpLimitPaths()) {
                    if (uri.startsWith(pattern)) {
                        needLimit = true;
                        break;
                    }
                }
                
                if (needLimit) {
                    // 创建IP限流器
                    String key = "rate_limit:ip:" + ipAddress;
                    RRateLimiter limiter = redissonClient.getRateLimiter(key);
                    
                    // 初始化限流器
                    limiter.trySetRate(
                        org.redisson.api.RateType.OVERALL,
                        rateLimitProperties.getIpLimitRate(),
                        rateLimitProperties.getIpLimitInterval(),
                        RateIntervalUnit.SECONDS
                    );
                    
                    // 尝试获取令牌
                    if (!limiter.tryAcquire()) {
                        response.setStatus(SC_TOO_MANY_REQUESTS);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"code\":429,\"message\":\"请求过于频繁，请稍后重试\"}");
                        return false;
                    }
                }
                
                return true;
            }
        };
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // 多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
    
    /**
     * 限流配置属性
     */
    @Data
    public static class RateLimitProperties {
        /**
         * 是否启用全局限流
         */
        private boolean enabled = true;
        
        /**
         * 是否启用IP限流
         */
        private boolean ipLimitEnabled = true;
        
        /**
         * IP限流速率（每秒请求数）
         */
        private int ipLimitRate = 50;
        
        /**
         * IP限流间隔（秒）
         */
        private int ipLimitInterval = 1;
        
        /**
         * 需要进行IP限流的路径
         */
        private String[] ipLimitPaths = {"/api/auth/login", "/api/auth/register"};
        
        /**
         * 接口限流配置
         * key: 接口路径
         * value: 每秒请求数
         */
        private Map<String, Integer> apiRateLimit = new HashMap<>();
    }
} 