package com.lawfirm.common.log.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * MDC日志追踪过滤器
 */
public class MdcTraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 优先从请求头获取traceId
            String traceId = request.getHeader(TRACE_ID_HEADER);
            if (traceId == null || traceId.isEmpty()) {
                traceId = generateTraceId();
            }
            
            // 将traceId放入MDC
            MDC.put(TRACE_ID, traceId);
            
            // 在响应头中返回traceId
            response.addHeader(TRACE_ID_HEADER, traceId);
            
            filterChain.doFilter(request, response);
        } finally {
            // 清理MDC
            MDC.remove(TRACE_ID);
        }
    }

    /**
     * 生成traceId
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
} 