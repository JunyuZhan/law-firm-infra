package com.lawfirm.common.log.filter;

import com.lawfirm.common.web.utils.IpUtils;
import com.lawfirm.common.util.ServletUtils;
import com.lawfirm.common.log.domain.TrackData;
import com.lawfirm.common.log.service.BehaviorTrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 行为验证过滤器
 */
@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class BehaviorVerifyFilter extends OncePerRequestFilter {

    private final BehaviorTrackService behaviorTrackService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            // 执行请求
            filterChain.doFilter(request, response);
        } finally {
            // 记录行为
            recordBehavior(request, response, System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 记录行为
     */
    private void recordBehavior(HttpServletRequest request, HttpServletResponse response, long responseTime) {
        try {
            TrackData trackData = new TrackData();
            // TODO: 设置用户信息
            trackData.setUserId(0L);
            trackData.setUsername("unknown");

            // 设置请求信息
            String ip = IpUtils.getIpAddr(request);
            trackData.setIp(ip);
            trackData.setLocation(ip); // 暂时使用 IP 作为位置
            trackData.setRequestUrl(request.getRequestURI());
            trackData.setRequestMethod(request.getMethod());
            trackData.setRequestParams("{}"); // 暂时使用空 JSON 对象
            trackData.setResponseStatus(response.getStatus());
            trackData.setResponseTime(responseTime);
            trackData.setOperationTime(LocalDateTime.now());

            // 设置设备信息
            String userAgent = request.getHeader("User-Agent");
            trackData.setDeviceInfo(userAgent);
            trackData.setBrowserInfo(userAgent);

            // 记录行为
            behaviorTrackService.trackBehavior(trackData);
        } catch (Exception e) {
            log.error("记录行为异常", e);
        }
    }
} 