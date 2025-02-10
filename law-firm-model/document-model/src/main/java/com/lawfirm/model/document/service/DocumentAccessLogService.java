package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentAccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 文档访问日志服务接口
 */
public interface DocumentAccessLogService {
    
    /**
     * 记录访问日志
     */
    void logAccess(Long documentId, String accessType, String accessUser,
            String clientInfo, String ipAddress, String sessionId, String requestUrl,
            String requestMethod, Long responseTime, Boolean isSuccess, String errorMessage,
            Long dataSize);
    
    /**
     * 获取访问历史
     */
    List<DocumentAccessLog> getAccessHistory(Long documentId);
    
    /**
     * 分页获取访问历史
     */
    Page<DocumentAccessLog> getAccessHistory(Long documentId, Pageable pageable);
    
    /**
     * 获取指定时间范围内的访问记录
     */
    List<DocumentAccessLog> getAccessByTimeRange(Long documentId,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取指定用户的访问记录
     */
    List<DocumentAccessLog> getAccessByUser(String accessUser);
    
    /**
     * 获取失败的访问记录
     */
    List<DocumentAccessLog> getFailedAccess();
    
    /**
     * 获取访问次数
     */
    long getAccessCount(Long documentId);
    
    /**
     * 获取独立访问用户数
     */
    long getUniqueVisitorCount(Long documentId);
    
    /**
     * 获取平均响应时间
     */
    double getAverageResponseTime(Long documentId);
    
    /**
     * 清理访问日志
     */
    void cleanAccessLogs(int days);
    
    /**
     * 获取高频访问IP
     */
    Map<String, Long> getHighFrequencyIps();
    
    /**
     * 分析性能
     */
    void analyzePerformance(Long documentId);
} 