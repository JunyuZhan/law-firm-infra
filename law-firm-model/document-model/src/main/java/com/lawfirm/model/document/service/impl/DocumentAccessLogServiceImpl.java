package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.entity.DocumentAccessLog;
import com.lawfirm.model.document.repository.DocumentAccessLogRepository;
import com.lawfirm.model.document.service.DocumentAccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentAccessLogServiceImpl implements DocumentAccessLogService {

    private final DocumentAccessLogRepository accessLogRepository;

    @Override
    @Transactional
    public void logAccess(Long documentId, String accessType, String accessUser,
            String clientInfo, String ipAddress, String sessionId, String requestUrl,
            String requestMethod, Long responseTime, Boolean isSuccess, String errorMessage,
            Long dataSize) {
        DocumentAccessLog accessLog = new DocumentAccessLog();
        accessLog.setDocumentId(documentId);
        accessLog.setAccessType(accessType);
        accessLog.setAccessUser(accessUser);
        accessLog.setAccessTime(LocalDateTime.now());
        accessLog.setClientInfo(clientInfo);
        accessLog.setIpAddress(ipAddress);
        accessLog.setSessionId(sessionId);
        accessLog.setRequestUrl(requestUrl);
        accessLog.setRequestMethod(requestMethod);
        accessLog.setResponseTime(responseTime);
        accessLog.setIsSuccess(isSuccess);
        accessLog.setErrorMessage(errorMessage);
        accessLog.setDataSize(dataSize);
        
        accessLogRepository.save(accessLog);
        log.info("Logged document access: documentId={}, user={}, type={}", 
                documentId, accessUser, accessType);
    }

    @Override
    public List<DocumentAccessLog> getAccessHistory(Long documentId) {
        return accessLogRepository.findByDocumentIdOrderByAccessTimeDesc(documentId);
    }

    @Override
    public Page<DocumentAccessLog> getAccessHistory(Long documentId, Pageable pageable) {
        return accessLogRepository.findByDocumentIdOrderByAccessTimeDesc(documentId, pageable);
    }

    @Override
    public List<DocumentAccessLog> getAccessByTimeRange(Long documentId,
            LocalDateTime startTime, LocalDateTime endTime) {
        return accessLogRepository.findByDocumentIdAndAccessTimeBetweenOrderByAccessTimeDesc(
                documentId, startTime, endTime);
    }

    @Override
    public List<DocumentAccessLog> getAccessByUser(String accessUser) {
        return accessLogRepository.findByAccessUserOrderByAccessTimeDesc(accessUser);
    }

    @Override
    public List<DocumentAccessLog> getFailedAccess() {
        return accessLogRepository.findByIsSuccessFalseOrderByAccessTimeDesc();
    }

    @Override
    public long getAccessCount(Long documentId) {
        return accessLogRepository.countByDocumentId(documentId);
    }

    @Override
    public long getUniqueVisitorCount(Long documentId) {
        return accessLogRepository.findByDocumentId(documentId).stream()
                .map(DocumentAccessLog::getAccessUser)
                .distinct()
                .count();
    }

    @Override
    public double getAverageResponseTime(Long documentId) {
        List<DocumentAccessLog> logs = accessLogRepository.findByDocumentId(documentId);
        if (logs.isEmpty()) {
            return 0.0;
        }
        return logs.stream()
                .mapToLong(DocumentAccessLog::getResponseTime)
                .average()
                .orElse(0.0);
    }

    @Override
    @Transactional
    public void cleanAccessLogs(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        int deleted = accessLogRepository.deleteByAccessTimeBefore(cutoffDate);
        log.info("Cleaned {} access logs older than {} days", deleted, days);
    }

    @Override
    public Map<String, Long> getHighFrequencyIps() {
        return accessLogRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        DocumentAccessLog::getIpAddress,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 100) // 设置阈值
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @Override
    public void analyzePerformance(Long documentId) {
        List<DocumentAccessLog> logs = accessLogRepository.findByDocumentId(documentId);
        
        // 计算平均响应时间
        double avgResponseTime = logs.stream()
                .mapToLong(DocumentAccessLog::getResponseTime)
                .average()
                .orElse(0.0);
                
        // 计算成功率
        long totalCount = logs.size();
        long successCount = logs.stream()
                .filter(DocumentAccessLog::getIsSuccess)
                .count();
        double successRate = totalCount == 0 ? 0 : (double) successCount / totalCount * 100;
        
        // 计算平均数据大小
        double avgDataSize = logs.stream()
                .mapToLong(DocumentAccessLog::getDataSize)
                .average()
                .orElse(0.0);
                
        log.info("Performance analysis for document {}: " +
                "Average response time: {}ms, Success rate: {}%, Average data size: {} bytes",
                documentId, avgResponseTime, successRate, avgDataSize);
    }
} 