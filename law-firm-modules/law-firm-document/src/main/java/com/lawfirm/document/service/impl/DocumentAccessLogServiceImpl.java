package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.repository.DocumentAccessLogRepository;
import com.lawfirm.model.document.entity.DocumentAccessLog;
import com.lawfirm.model.document.service.DocumentAccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档访问日志服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentAccessLogServiceImpl implements DocumentAccessLogService {

    private final DocumentAccessLogRepository accessLogRepository;

    @Override
    @Async
    @Transactional
    public void logAccess(Long documentId, String accessType, String accessUser,
            String clientInfo, String ipAddress, String sessionId, String requestUrl,
            String requestMethod, Long responseTime, Boolean isSuccess, String errorMessage,
            Long dataSize) {
        try {
            DocumentAccessLog accessLog = new DocumentAccessLog()
                    .setDocumentId(documentId)
                    .setAccessType(accessType)
                    .setAccessUser(accessUser)
                    .setAccessTime(LocalDateTime.now())
                    .setClientInfo(clientInfo)
                    .setIpAddress(ipAddress)
                    .setSessionId(sessionId)
                    .setRequestUrl(requestUrl)
                    .setRequestMethod(requestMethod)
                    .setResponseTime(responseTime)
                    .setIsSuccess(isSuccess)
                    .setErrorMessage(errorMessage)
                    .setDataSize(dataSize)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now())
                    .setIsDeleted(false);

            accessLogRepository.save(accessLog);
        } catch (Exception e) {
            log.error("记录访问日志失败：documentId=" + documentId, e);
        }
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
        return accessLogRepository.countDistinctAccessUserByDocumentId(documentId);
    }

    @Override
    public double getAverageResponseTime(Long documentId) {
        Double avgTime = accessLogRepository.avgResponseTimeByDocumentId(documentId);
        return avgTime != null ? avgTime : 0.0;
    }

    @Override
    @Transactional
    public void cleanAccessLogs(int days) {
        LocalDateTime beforeTime = LocalDateTime.now().minusDays(days);
        accessLogRepository.deleteByAccessTimeBefore(beforeTime);
    }

    @Override
    public Map<String, Long> getHighFrequencyIps() {
        List<Object[]> results = accessLogRepository.findHighFrequencyIps();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public void analyzePerformance(Long documentId) {
        double avgResponseTime = getAverageResponseTime(documentId);
        long totalAccess = getAccessCount(documentId);
        long uniqueVisitors = getUniqueVisitorCount(documentId);
        
        log.info("文档访问性能分析：documentId={}, 平均响应时间={}ms, 总访问次数={}, 独立访问用户数={}",
                documentId, avgResponseTime, totalAccess, uniqueVisitors);
    }
} 