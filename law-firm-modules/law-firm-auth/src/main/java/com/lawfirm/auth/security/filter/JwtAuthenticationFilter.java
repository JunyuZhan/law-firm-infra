package com.lawfirm.auth.security.filter;

import com.lawfirm.common.security.constants.SecurityConstants;
import com.lawfirm.auth.security.provider.JwtTokenProvider;
import com.lawfirm.auth.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT认证过滤器
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider tokenProvider;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    @Value("${server.servlet.context-path:/}")
    private String contextPath;
    
    // API文档相关路径，这些路径不需要添加上下文路径前缀
    private static final List<String> API_DOC_PATHS = Arrays.asList(
        "/doc.html", 
        "/v3/api-docs/**", 
        "/swagger-ui/**", 
        "/swagger-ui.html", 
        "/webjars/**", 
        "/swagger-resources/**", 
        "/knife4j/**", 
        "/v3/api-docs.yaml"
    );

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestUri = request.getRequestURI();
        
        // 首先检查是否为API文档路径，如果是则不过滤
        for (String pattern : API_DOC_PATHS) {
            if (pathMatcher.match(pattern, requestUri)) {
                log.debug("API文档路径不过滤: {}", requestUri);
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestUri = request.getRequestURI();
        String pathPrefix = "";
        if (StringUtils.hasText(contextPath) && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // 检查公共资源路径，这些路径需要添加上下文路径前缀
        for (String pattern : SecurityConstants.PUBLIC_RESOURCE_PATHS) {
            // 跳过API文档路径，因为它们已经在shouldNotFilter中处理了
            if (API_DOC_PATHS.contains(pattern)) {
                continue;
            }
            
            String fullPattern = pathPrefix.isEmpty() ? pattern : pathPrefix + pattern;
            if (pathMatcher.match(fullPattern, requestUri) || pathMatcher.match(pattern, requestUri)) {
                log.debug("请求路径 {} 匹配公共资源路径，跳过JWT认证", requestUri);
                filterChain.doFilter(request, response);
                return;
            }
        }
        
        if (requestUri.equals(pathPrefix) || requestUri.equals(pathPrefix + "/")) {
             log.debug("请求路径为根路径 {}，跳过JWT认证", requestUri);
             filterChain.doFilter(request, response);
             return;
        }

        try {
            String jwt = SecurityUtils.getTokenFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("设置用户 {} 的认证信息到安全上下文", authentication.getName());
            }
        } catch (Exception e) {
            log.error("无法设置用户认证信息到安全上下文", e);
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
}

