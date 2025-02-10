package com.lawfirm.document.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.model.document.repository.DocumentChangeLogRepository;
import com.lawfirm.model.document.repository.DocumentVersionRepository;
import com.lawfirm.model.document.entity.DocumentChangeLog;
import com.lawfirm.model.document.entity.DocumentVersion;
import com.lawfirm.model.document.service.DocumentChangeTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 文档变更跟踪服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentChangeTrackingServiceImpl implements DocumentChangeTrackingService {

    private final DocumentChangeLogRepository changeLogRepository;
    private final DocumentVersionRepository versionRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void recordChange(Long documentId, String changeType, Map<String, Object> changeContent,
            String operator, String clientInfo, String ipAddress, String sessionId) {
        try {
            // 获取当前版本
            DocumentVersion latestVersion = versionRepository.findFirstByDocumentIdOrderByVersionDesc(documentId);
            Integer currentVersion = latestVersion != null ? latestVersion.getVersion() : 0;

            // 创建变更记录
            DocumentChangeLog changeLog = new DocumentChangeLog()
                    .setDocumentId(documentId)
                    .setChangeType(changeType)
                    .setChangeContent(objectMapper.writeValueAsString(changeContent))
                    .setOperator(operator)
                    .setChangeTime(LocalDateTime.now())
                    .setVersion(currentVersion)
                    .setClientInfo(clientInfo)
                    .setIpAddress(ipAddress)
                    .setSessionId(sessionId)
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now())
                    .setIsDeleted(false);

            changeLogRepository.save(changeLog);
            log.info("记录文档变更：documentId={}, changeType={}, operator={}", 
                    documentId, changeType, operator);
        } catch (Exception e) {
            log.error("记录文档变更失败：documentId=" + documentId, e);
            throw new RuntimeException("记录文档变更失败", e);
        }
    }

    @Override
    public List<DocumentChangeLog> getChangeHistory(Long documentId) {
        return changeLogRepository.findByDocumentIdOrderByChangeTimeDesc(documentId);
    }

    @Override
    public Page<DocumentChangeLog> getChangeHistory(Long documentId, Pageable pageable) {
        return changeLogRepository.findByDocumentIdOrderByChangeTimeDesc(documentId, pageable);
    }

    @Override
    public List<DocumentChangeLog> getChangesByTimeRange(Long documentId, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return changeLogRepository.findByDocumentIdAndChangeTimeBetweenOrderByChangeTimeDesc(
                documentId, startTime, endTime);
    }

    @Override
    public List<DocumentChangeLog> getChangesByOperator(String operator) {
        return changeLogRepository.findByOperatorOrderByChangeTimeDesc(operator);
    }

    @Override
    public DocumentChangeLog getVersionChange(Long documentId, Integer version) {
        return changeLogRepository.findByDocumentIdAndVersion(documentId, version);
    }

    @Override
    public String compareVersions(Long documentId, Integer version1, Integer version2) {
        DocumentChangeLog change1 = changeLogRepository.findByDocumentIdAndVersion(documentId, version1);
        DocumentChangeLog change2 = changeLogRepository.findByDocumentIdAndVersion(documentId, version2);
        
        if (change1 == null || change2 == null) {
            throw new IllegalArgumentException("指定版本的变更记录不存在");
        }

        try {
            Map<String, Object> content1 = objectMapper.readValue(change1.getChangeContent(), Map.class);
            Map<String, Object> content2 = objectMapper.readValue(change2.getChangeContent(), Map.class);
            
            // TODO: 实现详细的差异比较逻辑
            return "版本比较功能待完善";
        } catch (Exception e) {
            log.error("比较版本失败：documentId=" + documentId, e);
            throw new RuntimeException("比较版本失败", e);
        }
    }

    @Override
    public DocumentChangeLog getLatestChange(Long documentId) {
        return changeLogRepository.findLatestChange(documentId);
    }

    @Override
    public long getChangeCount(Long documentId) {
        return changeLogRepository.countByDocumentId(documentId);
    }

    @Override
    @Transactional
    public void cleanChangeHistory(Long documentId) {
        changeLogRepository.deleteByDocumentId(documentId);
    }
} 