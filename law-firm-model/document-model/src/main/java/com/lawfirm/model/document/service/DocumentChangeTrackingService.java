package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentChangeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 文档变更跟踪服务接口
 */
public interface DocumentChangeTrackingService {
    
    /**
     * 记录文档变更
     */
    void recordChange(Long documentId, String changeType, Map<String, Object> changeContent,
            String operator, String clientInfo, String ipAddress, String sessionId);
    
    /**
     * 获取文档变更历史
     */
    List<DocumentChangeLog> getChangeHistory(Long documentId);
    
    /**
     * 分页获取文档变更历史
     */
    Page<DocumentChangeLog> getChangeHistory(Long documentId, Pageable pageable);
    
    /**
     * 获取指定时间范围内的变更记录
     */
    List<DocumentChangeLog> getChangesByTimeRange(Long documentId, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取指定操作人的变更记录
     */
    List<DocumentChangeLog> getChangesByOperator(String operator);
    
    /**
     * 获取指定版本的变更记录
     */
    DocumentChangeLog getVersionChange(Long documentId, Integer version);
    
    /**
     * 比较两个版本的差异
     */
    String compareVersions(Long documentId, Integer version1, Integer version2);
    
    /**
     * 获取最近的变更记录
     */
    DocumentChangeLog getLatestChange(Long documentId);
    
    /**
     * 获取变更次数
     */
    long getChangeCount(Long documentId);
    
    /**
     * 清理变更历史
     */
    void cleanChangeHistory(Long documentId);
} 